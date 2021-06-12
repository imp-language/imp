package org.imp.jvm.expression;

import org.imp.jvm.domain.scope.LocalVariable;
import org.imp.jvm.domain.scope.Scope;
import org.objectweb.asm.MethodVisitor;

public class LocalVariableReference extends Expression {

    public final LocalVariable localVariable;

    public LocalVariableReference(LocalVariable localVariable) {
        this.type = localVariable.getType();
        this.localVariable = localVariable;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        // ToDo: refactor IdentifierReference to inherit from Identifier
        String varName = localVariable.getName();
        int index = scope.getLocalVariableIndex(varName);
        if (localVariable.name.equals("p")) {
            index = 0;
        }
        this.type = localVariable.type; // Todo: this should be designed as to make this redundant. Both LocalVariable and LocalVariableReference shouldn't need type
        mv.visitVarInsn(localVariable.type.getLoadVariableOpcode(), index);
    }

    @Override
    public void validate(Scope scope) {

    }

}
