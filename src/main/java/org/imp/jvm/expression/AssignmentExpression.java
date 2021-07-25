package org.imp.jvm.expression;

import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.expression.reference.VariableReference;
import org.imp.jvm.types.Type;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

// todo: combine logic with AssignmentStatement
public class AssignmentExpression extends Expression {
    public final Expression recipient;
    public final Expression provider;

    public AssignmentExpression(Expression recipient, Expression provider) {
        this.recipient = recipient;
        this.provider = provider;
    }

    public void generate(MethodVisitor mv, Scope scope) {
        Type providerType = provider.type;
        Type recipientType = recipient.type;

        if (recipient instanceof VariableReference idRef) {
            provider.generate(mv, scope);
            String varName = idRef.reference.getName();
            int index = scope.getLocalVariableIndex(varName);
            castIfNecessary(providerType, recipientType, mv);
            mv.visitVarInsn(recipientType.getStoreVariableOpcode(), index);

        } else if (recipient instanceof StructPropertyAccess access) {
            String ownerInternalName = access.parent.type.getName();
            Identifier field = access.getLast();

            int index = scope.getLocalVariableIndex(access.parent.reference.getName());

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

