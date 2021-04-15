package org.imp.jvm.domain.statement;

import org.imp.jvm.codegen.statement.StatementGenerator;
import org.imp.jvm.domain.expression.Expression;

import java.util.Optional;


// Only regular assignment, e.g. = is implemented so far
public class Assignment implements Statement {
    public final Expression left;
    public final Expression right;

    public Assignment(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void accept(StatementGenerator generator) {
        generator.generate(this);
    }
}
