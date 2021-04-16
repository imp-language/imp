package org.imp.jvm.expression;

import org.imp.jvm.domain.types.Type;
import org.objectweb.asm.MethodVisitor;

public class Literal extends Expression {

    public final Object value;

    public Literal(Type type, Object value) {
        super();
        this.type = type;
        this.value = value;
    }

    public void generate(MethodVisitor mv) {
        // ToDo: support types other than integers
        int transformed = Integer.parseInt((String) value);
        mv.visitLdcInsn(transformed);
    }

}
