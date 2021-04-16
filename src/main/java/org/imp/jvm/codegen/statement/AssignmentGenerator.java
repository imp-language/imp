package org.imp.jvm.codegen.statement;

import org.imp.jvm.domain.expression.Expression;
import org.imp.jvm.domain.scope.LocalVariable;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.statement.Assignment;
import org.imp.jvm.domain.types.Type;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AssignmentGenerator {
    private final MethodVisitor methodVisitor;
    private final Scope scope;

    public AssignmentGenerator(MethodVisitor methodVisitor, Scope scope) {
        this.methodVisitor = methodVisitor;
        this.scope = scope;
    }

    public void generate(Assignment assignment) {
        String varName = assignment.name;
        Expression expression = assignment.expression;
        Type type = expression.getType();
        if (scope.variableExists(varName)) {
            int index = scope.getLocalVariableIndex(varName);
            LocalVariable localVariable = scope.getLocalVariable(varName);
            Type localVariableType = localVariable.getType();
            castIfNecessary(type, localVariableType);
            methodVisitor.visitVarInsn(type.getStoreVariableOpcode(), index);
//          return;
        }
//        Field field = scope.getField(varName);
//        String descriptor = field.getType().getDescriptor();
//        methodVisitor.visitVarInsn(Opcodes.ALOAD, 0);
//        expression.accept(expressionGenerator);
//        castIfNecessary(type, field.getType());
//        methodVisitor.visitFieldInsn(Opcodes.PUTFIELD, field.getOwnerInternalName(), field.getName(), descriptor);
    }

    private void castIfNecessary(Type expressionType, Type variableType) {
        if (!expressionType.equals(variableType)) {
            methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, variableType.getInternalName());
        }
    }
}