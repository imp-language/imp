package org.imp.jvm.expression;

import org.imp.jvm.domain.Operator;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.Type;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Arithmetic extends Expression {
    public final Expression left;
    public final Expression right;
    public final Operator operator;

    public Arithmetic(Expression left, Expression right, Operator operator) {
        this.operator = operator;
        this.type = getCommonType(left, right);
        this.left = left;
        this.right = right;
    }

    public void generate(MethodVisitor mv, Scope scope) {

        // ToDo: currently only addition is implemented


        if (type.equals(BuiltInType.STRING)) {
            mv.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
            mv.visitInsn(Opcodes.DUP);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);

            left.generate(mv, scope);

            String leftExprDescriptor = left.type.getDescriptor();
            String descriptor = "(" + leftExprDescriptor + ")Ljava/lang/StringBuilder;";
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", descriptor, false);

            right.generate(mv, scope);

            String rightExprDescriptor = right.type.getDescriptor();
            descriptor = "(" + rightExprDescriptor + ")Ljava/lang/StringBuilder;";
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", descriptor, false);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        } else {
            if (left.type.equals(right.type)) {
                left.generate(mv, scope);
                right.generate(mv, scope);

            } else {
                // the types don't match
                Type goalType = this.type;
                if (left.type == BuiltInType.INT && right.type == BuiltInType.FLOAT) {
                    left.generate(mv, scope);
                    mv.visitInsn(Opcodes.I2F);
                    right.generate(mv, scope);
                } else if (left.type == BuiltInType.FLOAT && right.type == BuiltInType.INT) {
                    left.generate(mv, scope);
                    right.generate(mv, scope);
                    mv.visitInsn(Opcodes.I2F);
                } else if (left.type == BuiltInType.INT && right.type == BuiltInType.DOUBLE) {
                    left.generate(mv, scope);
                    mv.visitInsn(Opcodes.I2D);
                    right.generate(mv, scope);
                } else if (left.type == BuiltInType.DOUBLE && right.type == BuiltInType.INT) {
                    left.generate(mv, scope);
                    right.generate(mv, scope);
                    mv.visitInsn(Opcodes.I2D);
                }
            }
            int op = switch (operator) {
                case ADD -> type.getAddOpcode();
                case SUBTRACT -> type.getSubstractOpcode();
                case MULTIPLY -> type.getMultiplyOpcode();
                case DIVIDE -> type.getDivideOpcode();

                default -> type.getAddOpcode();
            };
            mv.visitInsn(op);
        }
    }

    @Override
    public void validate(Scope scope) {
        left.validate(scope);
        right.validate(scope);
        // Todo: ensure types are compatible

        this.type = getCommonType(left, right);
    }

    private static Type getCommonType(Expression left, Expression right) {
        if (right.type == BuiltInType.STRING) return BuiltInType.STRING;
        return left.type;
    }


}
