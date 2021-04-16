package org.imp.jvm.parsing.visitor.expression;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.domain.CompareSign;
import org.imp.jvm.domain.expression.Expression;
import org.imp.jvm.domain.expression.RelationalExpression;

public class RelationalVisitor extends ImpParserBaseVisitor<RelationalExpression> {
    private final ExpressionVisitor expressionVisitor;

    public RelationalVisitor(ExpressionVisitor expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public RelationalExpression visitRelationalExpression(ImpParser.RelationalExpressionContext ctx) {
        Expression left = ctx.expression(0).accept(expressionVisitor);
        Expression right = ctx.expression(1).accept(expressionVisitor);
        System.out.println(ctx.cmp.getText());

        // ToDo: operator overloading for comparison
        CompareSign compareSign = CompareSign.fromString(ctx.cmp.getText());

        return new RelationalExpression(left, right, compareSign);
    }
}
