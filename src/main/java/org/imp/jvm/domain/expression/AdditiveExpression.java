package org.imp.jvm.domain.expression;

import org.imp.jvm.codegen.expression.ExpressionGenerator;
import org.imp.jvm.codegen.statement.StatementGenerator;
import org.imp.jvm.domain.Operator;
import org.imp.jvm.domain.types.BuiltInType;
import org.imp.jvm.domain.types.Type;

public class AdditiveExpression implements Expression {

    public final Operator op;

    public final Expression leftExpression;
    public final Expression rightExpression;
    private final Type type;

    public AdditiveExpression(Expression leftExpression, Expression rightExpression, Operator op) {
        this.op = op;
        this.type = getCommonType(leftExpression, rightExpression);
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    private static Type getCommonType(Expression leftExpression, Expression rightExpression) {
        if (rightExpression.getType() == BuiltInType.STRING) return BuiltInType.STRING;
        return leftExpression.getType();
    }


    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void accept(ExpressionGenerator generator) {
        generator.generate(this);
    }

    @Override
    public void accept(StatementGenerator generator) {
//        generator.generate(this);
    }
}