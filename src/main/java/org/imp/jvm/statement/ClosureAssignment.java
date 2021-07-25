package org.imp.jvm.statement;

import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ClosureAssignment extends Statement {
    public final String qualifiedFunctionName;
    public final Identifier closureParam;
    public final int index;

    public ClosureAssignment(String qualifiedFunctionName, Identifier closureParam, int index) {
        this.qualifiedFunctionName = qualifiedFunctionName;
        this.closureParam = closureParam;
        this.index = index;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        // Load 'this'
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        // Load the parameter to this.closure(var1, var2)
        mv.visitVarInsn(Opcodes.ALOAD, index);
        // Store the box
        mv.visitFieldInsn(Opcodes.PUTFIELD, qualifiedFunctionName, closureParam.name, "Lorg/imp/jvm/runtime/Box;");
    }

    @Override
    public void validate(Scope scope) {

    }


}
