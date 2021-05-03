package org.imp.jvm.domain.statement.variable;

import org.imp.jvm.domain.expression.Expression;

public class VariableInitialization extends Declaration {
    public final Expression expression;

    public VariableInitialization(Expression expression) {
        this.expression = expression;
    }
}
