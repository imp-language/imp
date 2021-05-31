package org.imp.jvm.expression;

import org.imp.jvm.domain.scope.LocalVariable;
import org.imp.jvm.domain.scope.Scope;
import org.objectweb.asm.MethodVisitor;

// Todo: combine with Identifier
public class IdentifierReference extends Expression {

    public final LocalVariable localVariable;

    public IdentifierReference(LocalVariable localVariable) {
        this.type = localVariable.getType();
        this.localVariable = localVariable;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        // ToDo: refactor IdentifierReference to inherit from Identifier
        String varName = localVariable.getName();
        int index = scope.getLocalVariableIndex(varName);
        mv.visitVarInsn(type.getLoadVariableOpcode(), index);
    }

    @Override
    public void validate() {
        
    }

}
