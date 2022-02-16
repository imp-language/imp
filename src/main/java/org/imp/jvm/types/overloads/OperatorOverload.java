package org.imp.jvm.types.overloads;

import org.imp.jvm.expression.Expression;
import org.imp.jvm.types.ImpType;

public abstract class OperatorOverload extends Expression {

    public void setType(ImpType t) {
        this.type = t;
    }

}
