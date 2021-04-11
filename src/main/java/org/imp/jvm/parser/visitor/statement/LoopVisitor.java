package org.imp.jvm.parser.visitor.statement;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.domain.expression.Expression;
import org.imp.jvm.domain.statement.Block;
import org.imp.jvm.domain.statement.Function;
import org.imp.jvm.domain.statement.Loop;
import org.imp.jvm.parser.visitor.expression.ExpressionVisitor;

public class LoopVisitor extends ImpParserBaseVisitor<Loop> {
    private final ExpressionVisitor expressionVisitor;
    private final BlockVisitor blockVisitor;

    public LoopVisitor(ExpressionVisitor expressionVisitor, BlockVisitor blockVisitor) {
        this.expressionVisitor = expressionVisitor;
        this.blockVisitor = blockVisitor;
    }

    @Override
    public Loop visitLoopStatement(ImpParser.LoopStatementContext ctx) {
        ImpParser.LoopConditionContext conditionContext = ctx.loopCondition();
        ImpParser.BlockContext blockContext = ctx.block();

        Expression condition = null;
        Block block = null;

        if (conditionContext != null) {
            condition = conditionContext.accept(expressionVisitor);
            block = blockContext.accept(blockVisitor);
        }

        return new Loop(condition, block);

    }
}
