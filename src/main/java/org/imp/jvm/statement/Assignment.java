package org.imp.jvm.statement;

import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.types.Type;
import org.imp.jvm.expression.Expression;
import org.imp.jvm.expression.IdentifierReference;
import org.imp.jvm.expression.StructPropertyAccess;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Assignment extends Statement {
    public Expression recipient;
    public Expression provider;

    public Assignment(Expression recipient, Expression provider) {
        this.recipient = recipient;
        this.provider = provider;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {

        Type providerType = provider.type;
        Type recipientType = recipient.type;


        if (recipient instanceof IdentifierReference) {
            provider.generate(mv, scope);
            IdentifierReference idRef = (IdentifierReference) recipient;
            String varName = idRef.localVariable.getName();
            int index = scope.getLocalVariableIndex(varName);
            castIfNecessary(providerType, recipientType, mv);
            mv.visitVarInsn(recipientType.getStoreVariableOpcode(), index);

        } else if (recipient instanceof StructPropertyAccess) {
            StructPropertyAccess access = (StructPropertyAccess) recipient;
            String ownerInternalName = "scratch/Person";
            Identifier field = access.getLast();

            int index = scope.getLocalVariableIndex(access.parent.localVariable.name);
            if (access.parent.localVariable.name.equals("p")) {
                index = 0;
            }
            mv.visitVarInsn(Opcodes.ALOAD, index);
            provider.generate(mv, scope);
            castIfNecessary(providerType, recipientType, mv);
            mv.visitFieldInsn(Opcodes.PUTFIELD, ownerInternalName, field.name, field.type.getDescriptor());
        }


    }

    @Override
    public void validate() {
        recipient.validate();
        provider.validate();
    }

    private void castIfNecessary(Type expressionType, Type variableType, MethodVisitor mv) {
        // Todo: this does not work
        if (!expressionType.equals(variableType)) {
            mv.visitTypeInsn(Opcodes.CHECKCAST, variableType.getInternalName());
        }
    }


}
