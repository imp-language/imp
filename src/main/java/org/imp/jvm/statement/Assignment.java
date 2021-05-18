package org.imp.jvm.statement;

import org.imp.jvm.domain.scope.LocalVariable;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.types.Type;
import org.imp.jvm.expression.Expression;
import org.imp.jvm.expression.IdentifierReference;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Assignment extends Statement {
    public Expression recepient;
    public Expression provider;
//    public String name;
//    public Expression expression;

    public Assignment(Expression recepient, Expression provider) {
        this.recepient = recepient;
        this.provider = provider;
    }
//
//    public Assignment(String name, Expression expression) {
//        this.name = name;
//        this.expression = expression;
//    }
//
//    public Assignment(Declaration declaration) {
//        this.name = declaration.name;
//        this.expression = declaration.expression;
//    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {

        Type providerType = provider.type;
        Type recipientType = recepient.type;
        provider.generate(mv, scope);

        // Todo: Only tested on LocalVariable expressions so far. Need to figure out how to add casts back in.
//        recepient.generate(mv, scope);

        IdentifierReference idRef = (IdentifierReference) recepient;
        String varName = idRef.localVariable.getName();
        int index = scope.getLocalVariableIndex(varName);
        castIfNecessary(providerType, recipientType, mv);
        mv.visitVarInsn(recipientType.getStoreVariableOpcode(), index);


//        expression.generate(mv, scope);
//
//        if (scope.variableExists(name)) {
//            int index = scope.getLocalVariableIndex(name);
//            LocalVariable localVariable = scope.getLocalVariable(name);
//            Type localVariableType = localVariable.getType();
//            castIfNecessary(type, localVariableType, mv);
//            mv.visitVarInsn(type.getStoreVariableOpcode(), index);
////          return;
//        }

    }

    private void castIfNecessary(Type expressionType, Type variableType, MethodVisitor mv) {
        if (!expressionType.equals(variableType)) {
            mv.visitTypeInsn(Opcodes.CHECKCAST, variableType.getInternalName());
        }
    }


}
