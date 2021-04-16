package org.imp.jvm.statement;

import org.imp.jvm.domain.scope.LocalVariable;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.types.Type;
import org.imp.jvm.expression.Expression;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Assignment extends Statement {
    public final String name;
    public final Expression expression;

    public Assignment(String name, Expression expression) {
        this.name = name;
        this.expression = expression;
    }

    public Assignment(Declaration declaration) {
        this.name = declaration.name;
        this.expression = declaration.expression;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        Type type = expression.type;
        if (scope.variableExists(name)) {
            int index = scope.getLocalVariableIndex(name);
            LocalVariable localVariable = scope.getLocalVariable(name);
            Type localVariableType = localVariable.getType();
            castIfNecessary(type, localVariableType, mv);
            mv.visitVarInsn(type.getStoreVariableOpcode(), index);
//          return;
        }
//        Field field = scope.getField(varName);
//        String descriptor = field.getType().getDescriptor();
//        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
//        expression.accept(expressionGenerator);
//        castIfNecessary(type, field.getType());
//        methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, field.getOwnerInternalName(), field.getName(), descriptor);
    }

    private void castIfNecessary(Type expressionType, Type variableType, MethodVisitor mv) {
        if (!expressionType.equals(variableType)) {
            mv.visitTypeInsn(Opcodes.CHECKCAST, variableType.getInternalName());
        }
    }


}
