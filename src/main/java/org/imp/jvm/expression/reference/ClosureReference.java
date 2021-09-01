package org.imp.jvm.expression.reference;

import org.imp.jvm.domain.scope.LocalVariable;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.types.BuiltInType;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ClosureReference extends Reference {
    public final LocalVariable localVariable;

    public ClosureReference(LocalVariable lv) {
        this.localVariable = lv;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        String varName = localVariable.getName();
        int index = 0;
//        int index = scope.getLocalVariableIndex(this.getName());
//        int index = scope.closures.indexOf(varName) + 1;
        mv.visitVarInsn(Opcodes.ALOAD, index);

        String currentFunctionName = scope.functionType.getName();
        mv.visitFieldInsn(Opcodes.GETFIELD, currentFunctionName, varName, "Lorg/imp/jvm/runtime/Box;");

        mv.visitFieldInsn(Opcodes.GETFIELD, "org/imp/jvm/runtime/Box", "t", Object.class.descriptorString());

        if (localVariable.type instanceof BuiltInType builtInType) {
            builtInType.doUnboxing(mv);
        } else {
            System.err.println("Unboxing isn't supported for custom types.");
            System.exit(27);
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
