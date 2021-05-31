package org.imp.jvm.parsing.visitor.expression;

import org.antlr.v4.runtime.RuleContext;
import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.domain.CompareSign;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.types.ClassType;
import org.imp.jvm.domain.types.Type;
import org.imp.jvm.domain.types.UnknownType;
import org.imp.jvm.expression.*;
import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.LocalVariable;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.types.BuiltInType;
import org.imp.jvm.statement.Struct;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ExpressionVisitor extends ImpParserBaseVisitor<Expression> {
    //    private final IdentifierReferenceVisitor identifierReferenceVisitor;
    private final LiteralVisitor literalVisitor;
    //    private final UnaryNotVisitor unaryNotVisitor;
//
//    private final UnaryAdditiveVisitor unaryAdditiveVisitor;
//    private final PowerVisitor powerVisitor;
//    private final MultiplicativeVisitor multiplicativeVisitor;
    private final ArithmeticVisitor arithmeticVisitor;
//    private final RelationalVisitor relationalVisitor;
//    private final LogicalVisitor logicalVisitor;
//    private final PropertyAccessVisitor propertyAccessVisitor;
//    private final MethodCallVisitor methodCallVisitor;
//    private final PostIncrementVisitor postIncrementVisitor;
//    private final CallVisitor callStatementVisitor;
//    private final NewObjectVisitor newObjectVisitor;
//    private final MemberIndexVisitor memberIndexVisitor;

    private final Scope scope;


    public ExpressionVisitor(Scope scope) {
        this.scope = scope;
//        identifierReferenceVisitor = new IdentifierReferenceVisitor(scope);
        literalVisitor = new LiteralVisitor(this);
//        unaryNotVisitor = new UnaryNotVisitor();
//        unaryAdditiveVisitor = new UnaryAdditiveVisitor();
//        powerVisitor = new PowerVisitor();
//        multiplicativeVisitor = new MultiplicativeVisitor();
        arithmeticVisitor = new ArithmeticVisitor(this);
//        relationalVisitor = new RelationalVisitor(this);
//        logicalVisitor = new LogicalVisitor();
//        propertyAccessVisitor = new PropertyAccessVisitor();
//        methodCallVisitor = new MethodCallVisitor();
//        postIncrementVisitor = new PostIncrementVisitor();
//        callStatementVisitor = new CallVisitor(scope, this);
//        newObjectVisitor = new NewObjectVisitor();
//        memberIndexVisitor = new MemberIndexVisitor();
    }

    @Override
    public IdentifierReference visitIdentifierReferenceExpression(ImpParser.IdentifierReferenceExpressionContext ctx) {
        String name = ctx.getText();

        // Todo: Struct vs StructType
        LocalVariable local = scope.getLocalVariable(name);
        if (scope.variableExists(name)) {

        }
        // ToDo: reference type parsing
        LocalVariable localVariable = new LocalVariable(name, BuiltInType.INT);

        return new IdentifierReference(local);
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
        // ToDo: operator overloading for comparison
        CompareSign compareSign = CompareSign.fromString(ctx.cmp.getText());
        return new Relational(left, right, compareSign);
    }


    @Override
    public FunctionCall visitCallStatementExpression(ImpParser.CallStatementExpressionContext ctx) {
        ImpParser.CallStatementContext callCtx = ctx.callStatement();

        // Identifier
        String functionName = callCtx.identifier().getText();

        // Todo: error handling for nonexistent function signatures
        // Function Signature
        var arguments = callCtx.expressionList().expression();
        var argTypes = getArgumentsForCall(arguments);
        FunctionSignature signature;
        if (functionName.equals("log")) {
            signature = new FunctionSignature("log", null, null);
        } else {
            signature = scope.getSignature(functionName, argTypes);
        }

        // Visit Arguments
        List<Expression> visited = new ArrayList<>();
        for (var arg : arguments) {
            visited.add(arg.accept(this));
        }


        // Function calls withing a single module never are accessed like module.func()
        // So the owner of each is the static class.
        var owner = new EmptyExpression(BuiltInType.VOID);


        return new FunctionCall(signature, visited, new ClassType("scratch"));
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

        Struct struct = scope.getStruct(structName);

        return new StructInit(struct, expressions, null);
    }

    @Override
    public Expression visitPropertyAccessExpression(ImpParser.PropertyAccessExpressionContext ctx) {
        Expression structExpr = ctx.expression().accept(this);
        IdentifierReference structRef = (IdentifierReference) structExpr;


        List<ImpParser.IdentifierContext> fieldPathCtx = ctx.identifier(); // list of identifiers in the chain


        // Types on struct fields have not settled yet, so all we know is a list of field names.
        List<Identifier> fieldPath = new ArrayList<>();
        for (var e : fieldPathCtx) {
            var ident = new Identifier(e.getText(), new UnknownType(e.getText()));
            ident.setLine(e.start);
            fieldPath.add(ident);
        }


        // This could be an attempt to reference fields that do not exist.
        // Validation of the field path is performed later.
        StructPropertyAccess access = new StructPropertyAccess(structRef, fieldPath);
        access.setLine(ctx.start);
        return access;
    }
}
