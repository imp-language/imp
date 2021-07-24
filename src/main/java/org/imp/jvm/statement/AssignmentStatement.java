package org.imp.jvm.statement;

import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.expression.reference.ClosureReference;
import org.imp.jvm.expression.reference.LocalReference;
import org.imp.jvm.runtime.Box;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.Type;
import org.imp.jvm.expression.Expression;
import org.imp.jvm.expression.reference.VariableReference;
import org.imp.jvm.expression.StructPropertyAccess;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AssignmentStatement extends Statement {
    public Expression recipient;
    public Expression provider;

    public AssignmentStatement(Expression recipient, Expression provider) {
        this.recipient = recipient;
        this.provider = provider;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {

        Type providerType = provider.type;
        Type recipientType = recipient.type;


        if (recipient instanceof VariableReference variableReference) {
            if (variableReference.reference instanceof LocalReference reference) {
                provider.generate(mv, scope);
                VariableReference idRef = (VariableReference) recipient;
                String varName = idRef.reference.getName();
                int index = scope.getLocalVariableIndex(varName);
                castIfNecessary(providerType, recipientType, mv);
                mv.visitVarInsn(recipientType.getStoreVariableOpcode(), index);
            } else if (variableReference.reference instanceof ClosureReference reference) {
                System.out.println("Boxing");
                int index = scope.getLocalVariableIndex(reference.getName());
                mv.visitVarInsn(Opcodes.ALOAD, index);
                // Todo: change owner based on function name
                mv.visitFieldInsn(Opcodes.GETFIELD, "scratch/Function_modifyG", "g", "Lorg/imp/jvm/runtime/Box;");

                provider.generate(mv, scope);
                if (providerType instanceof BuiltInType builtInType) {
                    builtInType.doBoxing(mv);
                }

                mv.visitFieldInsn(Opcodes.PUTFIELD, "org/imp/jvm/runtime/Box", "t", Object.class.descriptorString());
            }

        } else if (recipient instanceof StructPropertyAccess) {
            StructPropertyAccess access = (StructPropertyAccess) recipient;
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

        recipient.type = provider.type;
    }

    private void castIfNecessary(Type expressionType, Type variableType, MethodVisitor mv) {
        // Todo: this does not work
        if (!expressionType.equals(variableType)) {
            mv.visitTypeInsn(Opcodes.CHECKCAST, variableType.getInternalName());
        }
    }


}
