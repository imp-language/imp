package org.imp.jvm.statement;

import org.imp.jvm.domain.scope.LocalVariable;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.types.Mutability;
import org.imp.jvm.domain.types.Type;
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
        expression.generate(mv, scope);

        Type type = expression.type;
        if (scope.variableExists(name)) {
            int index = scope.getLocalVariableIndex(name);
            LocalVariable localVariable = scope.getLocalVariable(name);
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

    }

    private void castIfNecessary(Type expressionType, Type variableType, MethodVisitor mv) {
        if (!expressionType.equals(variableType)) {
            mv.visitTypeInsn(Opcodes.CHECKCAST, variableType.getInternalName());
        }
    }


}
