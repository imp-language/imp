package org.imp.jvm.parsing.visitor.expression;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.CompareSign;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.Operator;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.LocalVariable;
import org.imp.jvm.exception.SemanticErrors;
import org.imp.jvm.expression.reference.VariableReference;
import org.imp.jvm.parsing.visitor.ArgumentsVisitor;
import org.imp.jvm.parsing.visitor.statement.StatementVisitor;
import org.imp.jvm.statement.Block;
import org.imp.jvm.expression.Function;
import org.imp.jvm.statement.Statement;
import org.imp.jvm.types.*;
import org.imp.jvm.expression.*;
import org.imp.jvm.domain.scope.Scope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ExpressionVisitor extends ImpParserBaseVisitor<Expression> {
    private final LiteralVisitor literalVisitor;
    private final ArithmeticVisitor arithmeticVisitor;

    private final Scope scope;
    private final ImpFile parent;


    public ExpressionVisitor(Scope scope, ImpFile parent) {
        this.scope = scope;
        this.parent = parent;
        literalVisitor = new LiteralVisitor(this);
        arithmeticVisitor = new ArithmeticVisitor(this);
    }

    @Override
    public Expression visitWrappedExpression(ImpParser.WrappedExpressionContext ctx) {
        return ctx.expression().accept(this);
    }

    @Override
    public VariableReference visitIdentifierReferenceExpression(ImpParser.IdentifierReferenceExpressionContext ctx) {
        String name = ctx.getText();
        var vr = new VariableReference(name);
        vr.setCtx(ctx);

        return vr;
    }


    @Override
    public Literal visitLiteral(ImpParser.LiteralContext ctx) {
        return literalVisitor.visitLiteral(ctx);
    }

    @Override
    public Expression visitUnaryNotExpression(ImpParser.UnaryNotExpressionContext ctx) {
        return super.visitUnaryNotExpression(ctx);
    }


    @Override
    public Relational visitRelationalExpression(ImpParser.RelationalExpressionContext ctx) {
        Expression left = ctx.expression(0).accept(this);
        Expression right = ctx.expression(1).accept(this);

        CompareSign compareSign = CompareSign.fromString(ctx.cmp.getText());
        return new Relational(left, right, compareSign);
    }

    @Override
    public Expression visitLogicalExpression(ImpParser.LogicalExpressionContext ctx) {
        Expression left = ctx.expression(0).accept(this);
        left.setCtx(ctx.expression(0));
        Expression right = ctx.expression(1).accept(this);
        right.setCtx(ctx.expression(1));

        Logical.LogicalOperator logicalOperator = Logical.LogicalOperator.AND;
        if (ctx.OR() != null) {
            logicalOperator = Logical.LogicalOperator.OR;
        }

        return new Logical(left, right, logicalOperator);
    }

    @Override
    public FunctionCall visitCallStatement(ImpParser.CallStatementContext ctx) {

        // Function name
        String functionName = ctx.identifier().getText();

        // Function argument expressions
        List<Expression> argExpressions = new ArrayList<>();
        if (ctx.expressionList() != null) {
            var arguments = ctx.expressionList().expression();
            argExpressions = arguments.stream().map(a -> a.accept(this)).collect(Collectors.toList());
        }

        // Function Call
        FunctionCall call = new FunctionCall(functionName, argExpressions, parent);
        call.setCtx(ctx);

        return call;

    }

    @Override
    public FunctionCall visitCallStatementExpression(ImpParser.CallStatementExpressionContext ctx) {

        ImpParser.CallStatementContext callCtx = ctx.callStatement();

        return visitCallStatement(callCtx);

    }

    private List<Identifier> getArgumentsForCall(List<ImpParser.ExpressionContext> argumentsListCtx) {
        if (argumentsListCtx != null) {
            return argumentsListCtx.stream()
                    .map(arg -> arg.accept(this).type)
                    .map(argType -> {
                        var ident = new Identifier();
                        ident.type = argType;
                        return ident;
                    })
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }


    @Override
    public Arithmetic visitAdditiveExpression(ImpParser.AdditiveExpressionContext ctx) {
        return arithmeticVisitor.visitAdditiveExpression(ctx);
    }

    @Override
    public Expression visitPostIncrementExpression(ImpParser.PostIncrementExpressionContext ctx) {
        Operator op = Operator.ADD;
        if (ctx.DEC() != null) {
            op = Operator.SUBTRACT;
        }
        var expression = ctx.expression().accept(this);

        var postIncrementExpression = new PostIncrement(expression, op);
        postIncrementExpression.setCtx(ctx.expression());
        return postIncrementExpression;

    }

    @Override
    public Expression visitAssignmentExpression(ImpParser.AssignmentExpressionContext ctx) {
        Expression recipient = ctx.expression(0).accept(this);
        Expression provider = ctx.expression(1).accept(this);
        return new AssignmentExpression(recipient, provider);
    }

    @Override
    public StructInit visitNewObjectExpression(ImpParser.NewObjectExpressionContext ctx) {
        String structName = ctx.identifier().getText();
        List<Expression> expressions = new ArrayList<>();

        var expressionList = ctx.expressionList();
        if (expressionList != null) {
            var expressionContexts = ctx.expressionList().expression();

            for (var expCtx : expressionContexts) {
                Expression exp = expCtx.accept(this);
                expressions.add(exp);
            }

        }

        var si = new StructInit(structName, expressions, null);
        si.setCtx(ctx.identifier());
        return si;
    }

    @Override
    public Expression visitPropertyAccessExpression(ImpParser.PropertyAccessExpressionContext ctx) {
        Expression structExpr = ctx.expression().accept(this);
        VariableReference structRef = (VariableReference) structExpr;


        List<ImpParser.IdentifierContext> fieldPathCtx = ctx.identifier(); // list of identifiers in the chain


        // Types on struct fields have not settled yet, so all we know is a list of field names.
        List<Identifier> fieldPath = new ArrayList<>();
        for (var e : fieldPathCtx) {
            var ident = new Identifier(e.getText(), new UnknownType(e.getText()));
            ident.setCtx(e);
            fieldPath.add(ident);
        }


        // This could be an attempt to reference fields that do not exist.
        // Validation of the field path is performed later.
        StructPropertyAccess access = new StructPropertyAccess(structRef, fieldPath);
        access.setCtx(ctx);
        return access;
    }

    @Override
    public Function visitFunction(ImpParser.FunctionContext ctx) {
        String name = ctx.identifier().getText();

        FunctionType functionType = scope.findFunctionType(name);

        // If no FunctionTypes of name exist on the current scope,
        if (functionType == null) {
            // Create a new FunctionType and add it to the scope
            functionType = new FunctionType(name, parent);
            scope.functionTypes.add(functionType);
        }


        // Arguments
        List<Identifier> arguments = new ArrayList<>();
        ImpParser.ArgumentsContext argumentsContext = ctx.arguments();
        if (argumentsContext != null) {
            arguments = argumentsContext.accept(new ArgumentsVisitor(scope));
        }

        // Block
        ImpParser.BlockContext blockContext = ctx.block();
        Block block;
        if (blockContext.statementList() != null) {
            List<ImpParser.StatementContext> blockStatementsCtx = blockContext.statementList().statement();

            // Add parameters as local variables to the scope of the function block
            Scope newScope = new Scope(scope);
            newScope.functionType = functionType;
            arguments.forEach(param -> newScope.addLocalVariable(new LocalVariable(param.name, param.type)));

            StatementVisitor statementVisitor = new StatementVisitor(newScope, parent);
            List<Statement> statements = blockStatementsCtx.stream().map(stmt -> stmt.accept(statementVisitor)).collect(Collectors.toList());
            block = new Block(statements, newScope);
        } else {
            block = new Block();
        }


        // Return type
        ImpParser.TypeContext typeContext = ctx.type();
        Type returnType = BuiltInType.VOID;
        if (typeContext != null) {
            // ToDo: parse multiple returns
            returnType = TypeResolver.getFromTypeContext(typeContext, scope);
        }

        Modifier modifier = Modifier.NONE;
        if (ctx.modifiers() != null) {
            modifier = Modifier.fromString(ctx.modifiers().getText());

        }

        // Don't allow multiple definitions with same signature
        Function function = new Function(functionType, arguments, returnType, block, modifier);
        function.setCtx(ctx);
        if (functionType.signatures.containsKey(Function.getDescriptor(function.parameters))) {
            Logger.syntaxError(SemanticErrors.DuplicateFunctionOverloads, ctx.identifier());
        } else {
            functionType.signatures.put(Function.getDescriptor(function.parameters), function);

        }

        return function;
    }


    @Override
    public Expression visitMethodCallExpression(ImpParser.MethodCallExpressionContext ctx) {
        var owner = ctx.expression().accept(this);
        var callStatement = visitCallStatement(ctx.callStatement());
        callStatement.setOwner(owner);
        return callStatement;
    }
}
