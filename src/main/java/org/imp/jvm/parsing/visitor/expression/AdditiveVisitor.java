package org.imp.jvm.parsing.visitor.expression;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.domain.Operator;
import org.imp.jvm.domain.expression.AdditiveExpression;

public class AdditiveVisitor extends ImpParserBaseVisitor<AdditiveExpression> {
    private final ExpressionVisitor expressionVisitor;

    public AdditiveVisitor(ExpressionVisitor expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public AdditiveExpression visitAdditiveExpression(ImpParser.AdditiveExpressionContext ctx) {
        var left = ctx.expression(0).accept(expressionVisitor);
        var right = ctx.expression(1).accept(expressionVisitor);
        if (ctx.ADD() != null) {
            return new AdditiveExpression(left, right, Operator.ADD);
        } else if (ctx.SUB() != null) {
            return new AdditiveExpression(left, right, Operator.SUBTRACT);
        } else {
            System.out.println("not implemented");
            return null;
        }
    }
}
