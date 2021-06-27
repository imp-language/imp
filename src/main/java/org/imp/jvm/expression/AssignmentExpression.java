package org.imp.jvm.expression;

import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.types.Type;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AssignmentExpression extends Expression {
    public Expression recipient;
    public Expression provider;

    public AssignmentExpression(Expression recipient, Expression provider) {
        this.recipient = recipient;
        this.provider = provider;
    }

    public void generate(MethodVisitor mv, Scope scope) {
        Type providerType = provider.type;
        Type recipientType = recipient.type;

        if (recipient instanceof LocalVariableReference) {
            provider.generate(mv, scope);
            LocalVariableReference idRef = (LocalVariableReference) recipient;
            String varName = idRef.localVariable.getName();
            int index = scope.getLocalVariableIndex(varName);
            castIfNecessary(providerType, recipientType, mv);
            mv.visitVarInsn(recipientType.getStoreVariableOpcode(), index);

        } else if (recipient instanceof StructPropertyAccess) {
            StructPropertyAccess access = (StructPropertyAccess) recipient;
            String ownerInternalName = access.parent.type.getName();
            Identifier field = access.getLast();

            int index = scope.getLocalVariableIndex(access.parent.localVariable.name);
            if (access.parent.localVariable.name.equals("p")) {
//                index = 0;
                System.out.println("reee");
            }
            mv.visitVarInsn(Opcodes.ALOAD, index);
            provider.generate(mv, scope);
            castIfNecessary(providerType, recipientType, mv);
            mv.visitFieldInsn(Opcodes.PUTFIELD, ownerInternalName, field.name, field.type.getDescriptor());
        }
    }

    @Override
    public void validate(Scope scope) {
        recipient.validate(scope);
        provider.validate(scope);
    }

    private void castIfNecessary(Type expressionType, Type variableType, MethodVisitor mv) {
        // Todo: this does not work
        if (!expressionType.equals(variableType)) {
            mv.visitTypeInsn(Opcodes.CHECKCAST, variableType.getInternalName());
        }
    }

}

