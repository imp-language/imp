package org.imp.jvm.domain.expression;

import org.imp.jvm.codegen.expression.ExpressionGenerator;
import org.imp.jvm.codegen.statement.StatementGenerator;
import org.imp.jvm.domain.CompareSign;
import org.imp.jvm.domain.types.Type;


public class RelationalExpression implements Expression {

    public final Expression left;
    public final Expression right;
    public final CompareSign compareSign;

    public RelationalExpression(Expression left, Expression right, CompareSign compareSign) {
        this.left = left;
        this.right = right;
        this.compareSign = compareSign;
    }


    @Override
    public Type getType() {
        return left.getType();
    }

    @Override
    public void accept(ExpressionGenerator generator) {
        generator.generate(this);
    }

    @Override
    public void accept(StatementGenerator generator) {
        System.out.println("huh");
    }
}
