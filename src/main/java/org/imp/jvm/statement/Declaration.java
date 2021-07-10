package org.imp.jvm.statement;

import org.imp.jvm.domain.scope.LocalVariable;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.Mutability;
import org.imp.jvm.types.Type;
import org.imp.jvm.expression.Expression;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Declaration extends Statement {
    public final Mutability mutability;
    public final String name;
    public final Expression expression;

    public LocalVariable localVariable;

    public Declaration(Mutability mutability, String name, Expression expression) {
        this.mutability = mutability;
        this.name = name;
        this.expression = expression;
    }


    @Override
    public void generate(MethodVisitor mv, Scope scope) {

        if (localVariable.closure) {
            // Generate the box
            String ownerDescriptor = "org/imp/jvm/runtime/Box";
            mv.visitTypeInsn(Opcodes.NEW, ownerDescriptor);
            mv.visitInsn(Opcodes.DUP);
            expression.generate(mv, scope);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);

            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, ownerDescriptor, "<init>", "(Ljava/lang/Object;)V", false);

            int index = scope.getLocalVariableIndex(name);
            mv.visitVarInsn(Opcodes.ASTORE, index);
        } else {
            Type type = expression.type;
            int index = scope.getLocalVariableIndex(name);
            localVariable.type = expression.type;
            //Type localVariableType = localVariable.getType();
            // Todo: for now no casting is supported
            Type localVariableType = expression.type;
            castIfNecessary(type, localVariableType, mv);
            mv.visitVarInsn(type.getStoreVariableOpcode(), index);
//          return;
        }
    }

    @Override
    public void validate(Scope scope) {
        expression.validate(scope);
        localVariable = scope.getLocalVariable(name);
        localVariable.type = expression.type;

    }

    private void castIfNecessary(Type expressionType, Type variableType, MethodVisitor mv) {
        if (!expressionType.equals(variableType)) {
            mv.visitTypeInsn(Opcodes.CHECKCAST, variableType.getInternalName());
        }
    }


}
