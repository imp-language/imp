package org.imp.jvm.expression;

import org.imp.jvm.domain.CompareSign;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.types.BuiltInType;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Relational extends Expression {
    public final Expression left;
    public final Expression right;
    public final CompareSign compareSign;

    public Relational(Expression left, Expression right, CompareSign compareSign) {
        this.left = left;
        this.right = right;
        this.compareSign = compareSign;
        this.type = BuiltInType.BOOLEAN;
    }

    public void generate(MethodVisitor mv, Scope scope) {

        // Currently only primitive comparisons are implemented
        generatePrimitivesComparison(mv, scope, left, right);
        Label endLabel = new Label();
        Label trueLabel = new Label();
        mv.visitJumpInsn(compareSign.getOpcode(), trueLabel);
        mv.visitInsn(Opcodes.ICONST_0);
        mv.visitJumpInsn(Opcodes.GOTO, endLabel);
        mv.visitLabel(trueLabel);
        mv.visitInsn(Opcodes.ICONST_1);
        mv.visitLabel(endLabel);
    }

    @Override
    public void validate(Scope scope) {
        left.validate(scope);
        right.validate(scope);
        // Todo: make sure operation is compatible
    }

    private void generatePrimitivesComparison(MethodVisitor mv, Scope scope, Expression left, Expression right) {
        left.generate(mv, scope);
        right.generate(mv, scope);
//        mv.visitInsn(Opcodes.ISUB);
    }

}
