package org.imp.jvm.parser.visitor.statement;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.statement.Statement;
import org.imp.jvm.parser.visitor.expression.ExpressionVisitor;

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
        blockVisitor = new BlockVisitor();
        functionVisitor = new FunctionVisitor();
        classVisitor = new ClassVisitor();
        returnVisitor = new ReturnVisitor();
        ifVisitor = new IfVisitor();
        loopVisitor = new LoopVisitor();
        expressionVisitor = new ExpressionVisitor(scope);
        variableVisitor = new VariableVisitor();
        assignmentVisitor = new AssignmentVisitor();
        importVisitor = new ImportVisitor();
        exportVisitor = new ExportVisitor();
    }

    @Override
    public Statement visitBlock(ImpParser.BlockContext ctx) {
        return super.visitBlock(ctx);
    }

    @Override
    public Statement visitAssignment(ImpParser.AssignmentContext ctx) {
        return super.visitAssignment(ctx);
    }

    @Override
    public Statement visitLoopStatement(ImpParser.LoopStatementContext ctx) {
        return super.visitLoopStatement(ctx);
    }

    @Override
    public Statement visitReturnStatement(ImpParser.ReturnStatementContext ctx) {
        return super.visitReturnStatement(ctx);
    }

    @Override
    public Statement visitIfStatement(ImpParser.IfStatementContext ctx) {
        return super.visitIfStatement(ctx);
    }

    @Override
    public Statement visitFunctionStatement(ImpParser.FunctionStatementContext ctx) {
        return super.visitFunctionStatement(ctx);
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
