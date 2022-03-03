package org.imp.jvm.legacy.expression;

import org.imp.jvm.legacy.domain.scope.Scope;
import org.imp.jvm.types.ImpType;
import org.objectweb.asm.MethodVisitor;

public class EmptyExpression extends Expression {

    public EmptyExpression(ImpType type) {
        this.type = type;
    }

    public void generate(MethodVisitor mv, Scope scope) {
        // Do nothing
    }

    @Override
    public void validate(Scope scope) {
        // Do nothing
    }

}
