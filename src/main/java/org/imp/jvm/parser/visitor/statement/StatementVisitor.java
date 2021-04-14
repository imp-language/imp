package org.imp.jvm.parser.visitor.statement;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.domain.expression.Expression;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.statement.Statement;
import org.imp.jvm.parser.visitor.expression.ExpressionVisitor;

// http://www.ist.tugraz.at/_attach/Publish/Cb/typechecker_2017.pdf
// https://web.stanford.edu/class/archive/cs/cs143/cs143.1128/lectures/09/Slides09.pdf
public class StatementVisitor extends ImpParserBaseVisitor<Statement> {
    private final BlockVisitor blockVisitor;
    private final FunctionVisitor functionVisitor;
    private final ClassVisitor classVisitor;
    private final ReturnVisitor returnVisitor;
    private final IfVisitor ifVisitor;
    private final LoopVisitor loopVisitor;
    private final ExpressionVisitor expressionVisitor;
    private final VariableVisitor variableVisitor;
    private final AssignmentVisitor assignmentVisitor;
    private final ImportVisitor importVisitor;
    private final ExportVisitor exportVisitor;

    public StatementVisitor(Scope scope) {
        expressionVisitor = new ExpressionVisitor(scope);
        blockVisitor = new BlockVisitor(scope);
        functionVisitor = new FunctionVisitor(scope);
        classVisitor = new ClassVisitor();
        returnVisitor = new ReturnVisitor(expressionVisitor);
        ifVisitor = new IfVisitor(expressionVisitor, blockVisitor);
        loopVisitor = new LoopVisitor(expressionVisitor, blockVisitor);
        variableVisitor = new VariableVisitor(expressionVisitor, scope);
        assignmentVisitor = new AssignmentVisitor();
        importVisitor = new ImportVisitor();
        exportVisitor = new ExportVisitor();
    }

    @Override
    public Expression visitCallStatementExpression(ImpParser.CallStatementExpressionContext ctx) {
        return expressionVisitor.visitCallStatementExpression(ctx);
    }


    @Override
    public Statement visitBlock(ImpParser.BlockContext ctx) {
        return blockVisitor.visitBlock(ctx);
    }

    @Override
    public Statement visitAssignment(ImpParser.AssignmentContext ctx) {
        return super.visitAssignment(ctx);
    }

    @Override
    public Statement visitLoopStatement(ImpParser.LoopStatementContext ctx) {
        return loopVisitor.visitLoopStatement(ctx);
    }

    @Override
    public Statement visitReturnStatement(ImpParser.ReturnStatementContext ctx) {
        return returnVisitor.visitReturnStatement(ctx);
    }

    @Override
    public Statement visitIfStatement(ImpParser.IfStatementContext ctx) {
        return ifVisitor.visitIfStatement(ctx);
    }

    @Override
    public Statement visitFunctionStatement(ImpParser.FunctionStatementContext ctx) {
        return functionVisitor.visitFunctionStatement(ctx);
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

    @Override
    public Statement visitVariableStatement(ImpParser.VariableStatementContext ctx) {
        return variableVisitor.visitVariableStatement(ctx);
    }
}
