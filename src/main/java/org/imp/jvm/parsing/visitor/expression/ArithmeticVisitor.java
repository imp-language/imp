package org.imp.jvm.parsing.visitor.expression;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.domain.Operator;
import org.imp.jvm.expression.Arithmetic;

public class ArithmeticVisitor extends ImpParserBaseVisitor<Arithmetic> {
    private final ExpressionVisitor expressionVisitor;

    public ArithmeticVisitor(ExpressionVisitor expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public Arithmetic visitAdditiveExpression(ImpParser.AdditiveExpressionContext ctx) {
        var left = ctx.expression(0).accept(expressionVisitor);
        var right = ctx.expression(1).accept(expressionVisitor);
        if (ctx.ADD() != null) {
            return new Arithmetic(left, right, Operator.ADD);
        } else if (ctx.SUB() != null) {
            return new Arithmetic(left, right, Operator.SUBTRACT);
        } else if (ctx.MUL() != null) {
            return new Arithmetic(left, right, Operator.MULTIPLY);
        } else if (ctx.DIV() != null) {
            return new Arithmetic(left, right, Operator.DIVIDE);
        } else if (ctx.MOD() != null) {
            return new Arithmetic(left, right, Operator.MODULUS);
        } else {
            return null;
        }
    }
}
