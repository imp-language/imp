package org.imp.jvm.types.overloads;

import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.ListType;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ListOverloads extends OperatorOverload {


    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        String returnTypeDescriptor = "Ljava/lang/Object;";

        String descriptor = "(I)" + returnTypeDescriptor;


        mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/List", "get", descriptor, true);

        if (type instanceof BuiltInType bt) {
            bt.doUnboxing(mv);
        } else {

            mv.visitTypeInsn(Opcodes.CHECKCAST, type.getInternalName());
        }

    }

    @Override
    public void validate(Scope scope) {

    }
}
