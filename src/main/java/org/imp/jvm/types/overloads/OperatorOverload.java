package org.imp.jvm.types.overloads;

import org.imp.jvm.expression.Expression;
import org.imp.jvm.types.Type;

public abstract class OperatorOverload extends Expression {

    public void setType(Type t) {
        this.type = t;
    }

}
