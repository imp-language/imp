package org.imp.jvm.codegen.expression;

import org.imp.jvm.domain.CompareSign;
import org.imp.jvm.domain.expression.Expression;
import org.imp.jvm.domain.expression.FunctionCall;
import org.imp.jvm.domain.expression.RelationalExpression;
import org.imp.jvm.domain.scope.Scope;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class RelationalExpressionGenerator {

    private final ExpressionGenerator expressionGenerator;
    private final MethodVisitor methodVisitor;


    public RelationalExpressionGenerator(ExpressionGenerator expressionGenerator, MethodVisitor methodVisitor) {
        this.expressionGenerator = expressionGenerator;
        this.methodVisitor = methodVisitor;
    }

    public void generate(RelationalExpression relation) {
        Expression left = relation.left;
        Expression right = relation.right;
        var compareSign = relation.compareSign;

        // ToDo: currently only primitive comparisons are implemented
        generatePrimitivesComparison(left, right, compareSign);
        Label endLabel = new Label();
        Label trueLabel = new Label();
        methodVisitor.visitJumpInsn(compareSign.getOpcode(), trueLabel);
        methodVisitor.visitInsn(Opcodes.ICONST_0);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel);
        methodVisitor.visitLabel(trueLabel);
        methodVisitor.visitInsn(Opcodes.ICONST_1);
        methodVisitor.visitLabel(endLabel);
    }


    private void generatePrimitivesComparison(Expression leftExpression, Expression rightExpression, CompareSign compareSign) {
        leftExpression.accept(expressionGenerator);
        rightExpression.accept(expressionGenerator);
        methodVisitor.visitInsn(Opcodes.ISUB);
    }


}
