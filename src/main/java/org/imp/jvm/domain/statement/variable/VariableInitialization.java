package org.imp.jvm.domain.statement.variable;

import org.imp.jvm.domain.expression.Expression;
import org.imp.jvm.parser.visitor.expression.ExpressionVisitor;
import org.imp.jvm.parser.visitor.statement.variable.VariableInitializationVisitor;

public class VariableInitialization extends Declaration {
    public final Expression expression;

    public VariableInitialization(Expression expression) {
        this.expression = expression;
    }
}
