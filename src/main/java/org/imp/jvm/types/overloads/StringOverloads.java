package org.imp.jvm.types.overloads;

import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.types.BuiltInType;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class StringOverloads extends OperatorOverload {
    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "charAt", "(I)C", false);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "toString", "(C)Ljava/lang/String;", false);

    }

    @Override
    public void validate(Scope scope) {
        this.type = BuiltInType.STRING;
    }
}
