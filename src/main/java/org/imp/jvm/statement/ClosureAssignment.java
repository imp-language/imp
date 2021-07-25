package org.imp.jvm.statement;

import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.expression.Expression;
import org.imp.jvm.expression.StructPropertyAccess;
import org.imp.jvm.expression.reference.ClosureReference;
import org.imp.jvm.expression.reference.LocalReference;
import org.imp.jvm.expression.reference.VariableReference;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.Type;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ClosureAssignment extends Statement {
//    public Expression recipient;
//    public Expression provider;

    public ClosureAssignment() {
//        this.recipient = recipient;
//        this.provider = provider;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        int index = 0;
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitVarInsn(Opcodes.ALOAD, 1);
        // Todo: change owner based on function name
        mv.visitFieldInsn(Opcodes.PUTFIELD, "scratch/Function_modifyG", "g", "Lorg/imp/jvm/runtime/Box;");
    }

    @Override
    public void validate(Scope scope) {

    }


}
