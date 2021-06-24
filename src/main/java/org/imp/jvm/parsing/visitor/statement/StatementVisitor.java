package org.imp.jvm.parsing.visitor.statement;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.LocalVariable;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.types.*;
import org.imp.jvm.exception.SemanticErrors;
import org.imp.jvm.expression.Expression;
import org.imp.jvm.parsing.visitor.expression.ExpressionVisitor;
import org.imp.jvm.statement.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// http://www.ist.tugraz.at/_attach/Publish/Cb/typechecker_2017.pdf
// https://web.stanford.edu/class/archive/cs/cs143/cs143.1128/lectures/09/Slides09.pdf
public class StatementVisitor extends ImpParserBaseVisitor<Statement> {
    private final ExpressionVisitor expressionVisitor;

    private final Scope scope;
    private final ImpFile parent;

    public StatementVisitor(Scope scope, ImpFile parent) {
        this.scope = scope;
        expressionVisitor = new ExpressionVisitor(scope, parent);
        this.parent = parent;
    }

    @Override
    public Statement visitCallStatementExpression(ImpParser.CallStatementExpressionContext ctx) {
        return expressionVisitor.visitCallStatementExpression(ctx);
    }

    @Override
    public Statement visitPostIncrementExpression(ImpParser.PostIncrementExpressionContext ctx) {
        return expressionVisitor.visitPostIncrementExpression(ctx);
    }

    @Override
    public Statement visitStructStatement(ImpParser.StructStatementContext ctx) {
        var fieldCtx = ctx.structBlock().fieldDef();


        List<Identifier> fields = new ArrayList<>();

        // At this point we do not know of any custom types that exist.
        for (var fCtx : fieldCtx) {
//            Type t = TypeResolver.getFromTypeContext(types.get(i), scope);
            ImpParser.TypeContext typeContext = fCtx.type();
            ImpParser.IdentifierContext identifierContext = fCtx.identifier();

            if (typeContext == null || typeContext.getText().length() < 1) {
                Logger.syntaxError(SemanticErrors.MissingFieldType, identifierContext);
            }
            Type t = TypeResolver.getTemporaryType(typeContext);


            String n = identifierContext.getText();
            var field = new Identifier(n, t);
            fields.add(field);
        }

        var id = new Identifier(ctx.identifier().getText(), BuiltInType.STRUCT);

        // Create struct type object
        StructType structType = new StructType(id, fields, parent, scope);

        // Create struct object
        Struct struct = new Struct(structType);
        struct.setCtx(ctx);

        // Add struct to scope
        scope.addStruct(structType);

        return struct;
    }

    @Override
    public Block visitBlock(ImpParser.BlockContext ctx) {
        if (ctx.statementList() != null) {
            List<ImpParser.StatementContext> blockStatementsCtx = ctx.statementList().statement();

            // Child blocks inherit the parent block's scope
            Scope newScope = new Scope(scope);

            StatementVisitor statementVisitor = new StatementVisitor(newScope, parent);
            List<Statement> statements = blockStatementsCtx.stream().map(stmt -> stmt.accept(statementVisitor)).collect(Collectors.toList());
            return new Block(statements, newScope);
        }
        return new Block();
    }

    @Override
    public Function visitFunctionStatement(ImpParser.FunctionStatementContext ctx) {
        String name = ctx.identifier().getText();

        FunctionType functionType = scope.findFunctionType(name);

        // If no FunctionTypes of name exist on the current scope,
        if (functionType == null) {
            // Create a new FunctionType and add it to the scope
            functionType = new FunctionType(name);
            scope.functionTypes.add(functionType);
        }


        // Arguments
        List<Identifier> arguments = new ArrayList<>();
        ImpParser.ArgumentsContext argumentsContext = ctx.arguments();
        if (argumentsContext != null) {
            arguments = argumentsContext.accept(new ArgumentsVisitor());
        }

        // Block
        ImpParser.BlockContext blockContext = ctx.block();
        List<ImpParser.StatementContext> blockStatementsCtx = blockContext.statementList().statement();

        // Child blocks inherit the parent block's scope
        // Add parameters as local variables to the scope of the function block
        Scope newScope = new Scope(scope);
        arguments.forEach(param -> newScope.addLocalVariable(new LocalVariable(param.name, param.type)));

        StatementVisitor statementVisitor = new StatementVisitor(newScope, parent);
        List<Statement> statements = blockStatementsCtx.stream().map(stmt -> stmt.accept(statementVisitor)).collect(Collectors.toList());
        Block block = new Block(statements, newScope);


        // Return type
        ImpParser.TypeContext typeContext = ctx.type();
        Type returnType = BuiltInType.VOID;
        if (typeContext != null) {
            // ToDo: parse multiple returns
            returnType = TypeResolver.getFromTypeContext(typeContext, scope);
        }


        // Todo: addParametersAsLocalVariables(signature);
        FunctionSignature signature = new FunctionSignature(functionType, arguments, returnType);
        scope.addSignature(signature);
        functionType.signatures.add(signature);

        return new Function(signature, block);
    }

    @Override
    public Return visitReturnStatement(ImpParser.ReturnStatementContext ctx) {
        Expression expression = ctx.expression().accept(expressionVisitor);
        return new Return(expression);
    }

    @Override
    public If visitIfStatement(ImpParser.IfStatementContext ctx) {
        Expression condition = ctx.expression().accept(expressionVisitor);

        Statement trueStatement = ctx.trueStatement.accept(this);

        Statement falseStatement = null;
        if (ctx.falseStatement != null) {
            falseStatement = ctx.falseStatement.accept(this);
        }

        return new If(condition, trueStatement, falseStatement);
    }

    @Override
    public Loop visitLoopStatement(ImpParser.LoopStatementContext ctx) {
        ImpParser.LoopConditionContext conditionContext = ctx.loopCondition();


        if (conditionContext instanceof ImpParser.ForLoopConditionContext) {
            // loop val i = 0; i < 10; i++ { }
            ImpParser.ForLoopConditionContext cond = (ImpParser.ForLoopConditionContext) conditionContext;
            Declaration declaration = (Declaration) cond.variableStatement().accept(this);
            Expression condition = cond.expression(0).accept(expressionVisitor);
            var incrementerCtx = cond.expression(1);
            Expression incrementer = incrementerCtx.accept(expressionVisitor);

            Block block = (Block) ctx.block().accept(this);
            block.scope = new Scope(scope);

            return new ForLoop(declaration, condition, incrementer, block);
        } else if (conditionContext instanceof ImpParser.ForInLoopConditionContext) {
            // loop val item, idx in list { }

        } else if (conditionContext instanceof ImpParser.WhileLoopConditionContext) {
            // loop someExpression() == true { }
        }

        System.out.println("ahhh oh no error bad loop syntax");
        return null;

    }

    @Override
    public Declaration visitVariableStatement(ImpParser.VariableStatementContext ctx) {
        Mutability mutability = Mutability.Val;

        // Is the variable described as `mut` or `val`?
        if (ctx.VAL() != null && ctx.MUT() == null) {
            mutability = Mutability.Val;
        } else if (ctx.VAL() == null && ctx.MUT() != null) {
            mutability = Mutability.Mut;
        }

        // Variable initialization or iterator descructuring?
        ImpParser.IteratorDestructuringContext iteratorDestructuringContext = ctx.iteratorDestructuring();
        ImpParser.VariableInitializeContext variableInitializeContext = ctx.variableInitialize();
        Declaration declaration = null;

        if (iteratorDestructuringContext != null) {
//            declaration = iteratorDestructuringContext.accept(iteratorDestructuringVisitor);
            System.err.println("Parser error.");
            System.exit(1);
        } else if (variableInitializeContext != null) {
            Expression expression = variableInitializeContext.expression().accept(expressionVisitor);
            String name = variableInitializeContext.identifier().getText();
            declaration = new Declaration(mutability, name, expression);
            scope.addLocalVariable(new LocalVariable(name, expression.type));

        } else {
            System.err.println("Parser error.");
            System.err.println(Arrays.toString(Thread.currentThread().getStackTrace()));
            System.exit(1);
        }
        return declaration;
    }


    class ArgumentsVisitor extends ImpParserBaseVisitor<List<Identifier>> {
        @Override
        public List<Identifier> visitArguments(ImpParser.ArgumentsContext ctx) {
            var argumentsCtx = ctx.argument();

            var arguments = new ArrayList<Identifier>();
            for (var argCtx : argumentsCtx) {
                var identifier = new Identifier();
                identifier.name = argCtx.identifier().getText();
                // todo: must respect scope to find struct types
                identifier.type = TypeResolver.getFromTypeContext(argCtx.type(), scope);
                arguments.add(identifier);
            }

            return arguments;
        }
    }


    @Override
    public AssignmentStatement visitAssignment(ImpParser.AssignmentContext ctx) {
        Expression recipient = ctx.expression(0).accept(expressionVisitor);
        Expression provider = ctx.expression(1).accept(expressionVisitor);

        // recipient must be an identifier or a property access expression


//        String name = ctx.identifier().getText();
//        Expression expression = ctx.expression().accept(expressionVisitor);
//        return new Assignment(name, expression);
        return new AssignmentStatement(recipient, provider);
    }


    @Override
    public Statement visitClassStatement(ImpParser.ClassStatementContext ctx) {
        return super.visitClassStatement(ctx);
    }

    @Override
    public Statement visitImportStatement(ImpParser.ImportStatementContext ctx) {
        return super.visitImportStatement(ctx);
    }

    @Override
    public Statement visitExportStatement(ImpParser.ExportStatementContext ctx) {
        return super.visitExportStatement(ctx);
    }


}
