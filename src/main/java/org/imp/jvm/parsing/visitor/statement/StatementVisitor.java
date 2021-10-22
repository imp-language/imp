package org.imp.jvm.parsing.visitor.statement;

import org.apache.commons.lang3.StringUtils;
import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.exception.Errors;
import org.imp.jvm.expression.Assignment;
import org.imp.jvm.expression.Expression;
import org.imp.jvm.expression.Function;
import org.imp.jvm.expression.Literal;
import org.imp.jvm.parsing.visitor.expression.ExpressionVisitor;
import org.imp.jvm.parsing.visitor.expression.LiteralVisitor;
import org.imp.jvm.statement.*;
import org.imp.jvm.statement.Enum;
import org.imp.jvm.types.*;

import java.util.*;
import java.util.stream.Collectors;

// http://www.ist.tugraz.at/_attach/Publish/Cb/typechecker_2017.pdf
// https://web.stanford.edu/class/archive/cs/cs143/cs143.1128/lectures/09/Slides09.pdf
public class StatementVisitor extends ImpParserBaseVisitor<Statement> {
    private final ExpressionVisitor expressionVisitor;
    private final LiteralVisitor literalVisitor;

    private final Scope scope;
    private final ImpFile parent;

    public StatementVisitor(Scope scope, ImpFile parent) {
        this.scope = scope;
        expressionVisitor = new ExpressionVisitor(scope, parent);
        literalVisitor = new LiteralVisitor(expressionVisitor);
        this.parent = parent;
    }

    @Override
    public Literal visitLiteralExpression(ImpParser.LiteralExpressionContext ctx) {
        return ctx.accept(literalVisitor);
    }

    @Override
    public Statement visitExportStatement(ImpParser.ExportStatementContext ctx) {
        if (ctx.function() != null) {
            Function function = (Function) ctx.function().accept(expressionVisitor);
            return new Export(function, scope);
        } else {
            System.err.println("Exports not yet implemented.");
            return null;
        }
    }

    @Override
    public Statement visitFunctionDefinition(ImpParser.FunctionDefinitionContext ctx) {
        return ctx.function().accept(expressionVisitor);
    }

    @Override
    public Statement visitImportFile(ImpParser.ImportFileContext ctx) {
        var modulePath = ctx.stringLiteral().accept(literalVisitor);

        var importStatement = new Import(modulePath, scope);
        importStatement.setCtx(ctx, parent.name);
        return importStatement;
    }

    @Override
    public Statement visitImportFileAsIdentifier(ImpParser.ImportFileAsIdentifierContext ctx) {
        var modulePath = ctx.stringLiteral().accept(literalVisitor);

        var identifier = ctx.identifier().getText();

        var importStatement = new Import(modulePath, scope, identifier);
        importStatement.setCtx(ctx, parent.name);
        return importStatement;
    }

    @Override
    public Statement visitCallStatementExpression(ImpParser.CallStatementExpressionContext ctx) {
        return expressionVisitor.visitCallStatementExpression(ctx);
    }

    @Override
    public Statement visitMethodCallExpression(ImpParser.MethodCallExpressionContext ctx) {
        return expressionVisitor.visitMethodCallExpression(ctx);
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
                Logger.syntaxError(Errors.MissingFieldType, new Empty(identifierContext, parent.name), ctx.getText(), Errors.getLocation(ctx.getStart()));
            }
            Type t = TypeResolver.getTemporaryType(typeContext);


            String n = identifierContext.getText();
            var field = new Identifier(n, t);
            field.setCtx(fCtx, parent.name);
            fields.add(field);
        }

        var id = new Identifier(ctx.identifier().getText(), BuiltInType.STRUCT);

        // Create struct type object
        StructType structType = new StructType(id, fields, parent, scope);

        // Create struct object
        Struct struct = new Struct(structType);
        struct.setCtx(ctx, parent.name);

        // Add struct to scope
        // Todo: do this for enums?
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


        if (conditionContext instanceof ImpParser.ForLoopConditionContext cond) {
            // loop val i = 0; i < 10; i++ { }
            Declaration declaration = (Declaration) cond.variableStatement().accept(this);
            Expression condition = cond.expression(0).accept(expressionVisitor);
            var incrementerCtx = cond.expression(1);
            Expression incrementer = incrementerCtx.accept(expressionVisitor);

            Block block = (Block) ctx.block().accept(this);
            block.scope = new Scope(scope);

            return new ForLoop(declaration, condition, incrementer, block);
        } else if (conditionContext instanceof ImpParser.ForInLoopConditionContext forInCtx) {
            // loop val item, idx in list { }
            String iterator = forInCtx.identifier().getText();
            Expression expression = forInCtx.expression().accept(expressionVisitor);
            expression.setCtx(forInCtx.expression(), parent.name);
            Block block = (Block) ctx.block().accept(this);
            var forInLoop = new ForInLoop(iterator, expression, block);
            forInLoop.setCtx(forInCtx, parent.name);

            return forInLoop;

        } else if (conditionContext instanceof ImpParser.WhileLoopConditionContext) {
            // loop someExpression() == true { }
            System.err.println("loop someExpression() == true { }");
            System.exit(123);
        }

        System.out.println("ahhh oh no error bad loop syntax");
        return null;

    }

    @Override
    public Declaration visitVariableStatement(ImpParser.VariableStatementContext ctx) {
        Mutability mutability = Mutability.Val;

        // Is the variable described as `mut` or `val`?
        if (ctx.VAL() != null && ctx.MUT() == null) {
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
            declaration.setCtx(ctx, parent.name);
//            scope.addLocalVariable(new LocalVariable(name, expression.type));

        } else {
            System.err.println("Parser error.");
            System.err.println(Arrays.toString(Thread.currentThread().getStackTrace()));
            System.exit(1);
        }
        return declaration;
    }


    @Override
    public Assignment visitAssignment(ImpParser.AssignmentContext ctx) {
        Expression recipient = ctx.expression(0).accept(expressionVisitor);
        recipient.setCtx(ctx.expression(0), parent.name);
        Expression provider = ctx.expression(1).accept(expressionVisitor);
        provider.setCtx(ctx.expression(1), parent.name);

        return new Assignment(recipient, provider);
    }


    @Override
    public Statement visitEnumStatement(ImpParser.EnumStatementContext ctx) {
        var name = ctx.identifier().getText();
        var blockCtx = ctx.enumDef();

        Map<String, Optional<Expression>> elements = new HashMap<>();
        for (var enumDef : blockCtx) {
            String key = enumDef.identifier().getText();
            Expression e = null;
            if (enumDef.expression() != null) {
                e = enumDef.expression().accept(expressionVisitor);
            }
            Optional<Expression> expression = Optional.ofNullable(e);

            elements.put(key, expression);
        }
        EnumType enumType = new EnumType(name, elements);
        Enum enumStatement = new Enum(enumType);
        enumStatement.setCtx(ctx, parent.name);
        return enumStatement;
    }

    @Override
    public Statement visitTypeAliasStatement(ImpParser.TypeAliasStatementContext ctx) {

        String name = ctx.identifier().getText();

        String extern = ctx.stringLiteral().getText();
        extern = StringUtils.removeStart(extern, "\"");
        extern = StringUtils.removeEnd(extern, "\"");

        var typeAlias = new TypeAlias(name, extern);
        typeAlias.setCtx(ctx, parent.name);

        return typeAlias;
    }
}
