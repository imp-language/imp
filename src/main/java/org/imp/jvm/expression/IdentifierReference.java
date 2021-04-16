package org.imp.jvm.expression;

import org.imp.jvm.domain.scope.LocalVariable;
import org.objectweb.asm.MethodVisitor;

public class IdentifierReference extends Expression {

    public final LocalVariable localVariable;

    public IdentifierReference(LocalVariable localVariable) {
        this.localVariable = localVariable;
    }

    public void generate(MethodVisitor mv) {
        // ToDo: refactor IdentifierReference to inherit from Identifier
        String varName = localVariable.getName();
        int index = scope.getLocalVariableIndex(varName);
        mv.visitVarInsn(type.getLoadVariableOpcode(), index);
    }

}
