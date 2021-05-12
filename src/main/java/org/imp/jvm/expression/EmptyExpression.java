package org.imp.jvm.expression;

import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.types.Type;
import org.objectweb.asm.MethodVisitor;

public class EmptyExpression extends Expression {

    public EmptyExpression(Type type) {
        this.type = type;
    }

    public void generate(MethodVisitor mv, Scope scope) {
        // Do nothing
    }

}
