package org.imp.jvm.statement;

import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.scope.LocalVariable;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.exception.Errors;
import org.imp.jvm.expression.Expression;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.Mutability;
import org.imp.jvm.types.Type;
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
    public void validate(Scope scope) {
        expression.validate(scope);

        if (expression.type == BuiltInType.VOID) {
            Logger.syntaxError(Errors.VoidAssignment, this, this.name, expression.type);
            return;
        }

        if (scope.variableExists(name)) {
            Logger.syntaxError(Errors.Redeclaration, this, name);
        }

        localVariable = new LocalVariable(name, expression.type, mutability);
        localVariable.type = expression.type;

        scope.addLocalVariable(localVariable);

    }


    @Override
    public void generate(MethodVisitor mv, Scope scope) {

        if (localVariable.closure) {
            // Generate the box
            String ownerDescriptor = "org/imp/jvm/runtime/Box";
            mv.visitTypeInsn(Opcodes.NEW, ownerDescriptor);
            mv.visitInsn(Opcodes.DUP);
            expression.generate(mv, scope);

            if (expression.type instanceof BuiltInType builtInType) {
                builtInType.doBoxing(mv);
            } else {
                System.err.println("Boxing isn't supported for custom types.");
                System.exit(27);
            }

            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, ownerDescriptor, "<init>", "(Ljava/lang/Object;)V", false);

            int index = scope.getLocalVariableIndex(name);
            mv.visitVarInsn(Opcodes.ASTORE, index);
        } else {
            expression.generate(mv, scope);
            Type type = expression.type;
            int index = scope.getLocalVariableIndex(name);
            localVariable.type = expression.type;
            //Type localVariableType = localVariable.getType();
            // Todo: for now no casting is supported
            Type localVariableType = expression.type;
            castIfNecessary(type, localVariableType, mv);


            mv.visitVarInsn(type.getStoreVariableOpcode(), index);
        }
    }


    private void castIfNecessary(Type expressionType, Type variableType, MethodVisitor mv) {
        if (!expressionType.equals(variableType)) {
            mv.visitTypeInsn(Opcodes.CHECKCAST, variableType.getInternalName());
        }
    }


}
