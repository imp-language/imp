package org.imp.jvm.parser.visitor.statement;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.domain.expression.Expression;
import org.imp.jvm.domain.statement.Block;
import org.imp.jvm.domain.statement.Function;
import org.imp.jvm.domain.statement.IfStatement;
import org.imp.jvm.parser.visitor.expression.ExpressionVisitor;

public class IfVisitor extends ImpParserBaseVisitor<IfStatement> {
    private final ExpressionVisitor expressionVisitor;
    private final BlockVisitor blockVisitor;

    public IfVisitor(ExpressionVisitor expressionVisitor, BlockVisitor blockVisitor) {
        this.expressionVisitor = expressionVisitor;
        this.blockVisitor = blockVisitor;
    }

    @Override
    public IfStatement visitIfStatement(ImpParser.IfStatementContext ctx) {
        Expression condition = null;
        if (ctx.expression() != null) {
            condition = ctx.expression().accept(expressionVisitor);
        }
        Block block = ctx.block(0).accept(blockVisitor);
        IfStatement elseIf = null;

//        if (ctx.block(0) != null) {
//            IfVisitor ifElseVisitor = new IfVisitor(expressionVisitor, blockVisitor);
//            elseIf = ctx.ifStatement().accept(ifElseVisitor);
//        }

        return new IfStatement(condition, block, elseIf);
    }
}
