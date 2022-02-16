package org.imp.jvm.expression;

import org.imp.jvm.domain.scope.Scope;
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
