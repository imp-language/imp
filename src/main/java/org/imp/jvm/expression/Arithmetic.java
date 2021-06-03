package org.imp.jvm.expression;

import org.imp.jvm.domain.CompareSign;
import org.imp.jvm.domain.Operator;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.types.BuiltInType;
import org.imp.jvm.domain.types.Type;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Arithmetic extends Expression {
    public final Expression left;
    public final Expression right;
    public final Operator op;

    public Arithmetic(Expression left, Expression right, Operator op) {
        this.op = op;
        this.type = getCommonType(left, right);
        this.left = left;
        this.right = right;
    }

    public void generate(MethodVisitor mv, Scope scope) {

        // ToDo: currently only addition is implemented


        if (type.equals(BuiltInType.STRING)) {

            /*
            left.generate(mv, scope);
//            right.generate(mv, scope);
            String owner = "java/lang/invoke/StringConcatFactory";
            String name = "makeConcatWithConstants";
            String descriptor = "(Ljava/lang/String;)Ljava/lang/String;";
//            mv.visitMethodInsn(Opcodes.INVOKEDYNAMIC, owner, name, descriptor, false);

            Handle handle = new Handle(Opcodes.H_INVOKEVIRTUAL, owner, name, descriptor, false);
            mv.visitInvokeDynamicInsn(name, descriptor, handle, "penis");
            */


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
            left.generate(mv, scope);
            right.generate(mv, scope);
            int op = type.getAddOpcode();
            mv.visitInsn(op);
        }
    }

    @Override
    public void validate() {
        left.validate();
        right.validate();
        // Todo: ensure types are compatible
    }

    private static Type getCommonType(Expression left, Expression right) {
        if (right.type == BuiltInType.STRING) return BuiltInType.STRING;
        return left.type;
    }


}
