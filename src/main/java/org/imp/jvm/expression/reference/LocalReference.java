package org.imp.jvm.expression.reference;

import org.imp.jvm.domain.scope.LocalVariable;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.types.BuiltInType;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class LocalReference extends Reference {
    public final LocalVariable localVariable;

    public LocalReference(LocalVariable lv) {
        this.localVariable = lv;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        String varName = localVariable.getName();
        int index = scope.getLocalVariableIndex(varName);
        if (localVariable.closure) {
            mv.visitVarInsn(Opcodes.ALOAD, index);

            mv.visitFieldInsn(Opcodes.GETFIELD, "org/imp/jvm/runtime/Box", "t", Object.class.descriptorString());

            if (localVariable.type instanceof BuiltInType builtInType) {
                builtInType.doUnboxing(mv);
            } else {
                System.err.println("Unboxing isn't supported for custom types.");
                System.exit(27);
            }
        } else {

            // Todo: this should be designed as to make this redundant. Both LocalVariable and LocalVariableReference shouldn't need type
            this.type = localVariable.type;
            mv.visitVarInsn(type.getLoadVariableOpcode(), index);
        }


    }

    @Override
    public void validate(Scope scope) {
        this.type = localVariable.type;
    }

    @Override
    public String getName() {
        return localVariable.name;
    }
}
