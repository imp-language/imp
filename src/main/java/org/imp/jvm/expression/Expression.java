package org.imp.jvm.expression;

import org.imp.jvm.domain.types.Type;
import org.objectweb.asm.MethodVisitor;

public abstract class Expression {
    public Type type;

    public abstract void generate(MethodVisitor mv);

}

