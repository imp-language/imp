package org.imp.jvm.codegen.expression;

import org.imp.jvm.domain.Operator;
import org.imp.jvm.domain.expression.*;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.types.Type;
import org.objectweb.asm.MethodVisitor;

public class AdditiveGenerator {
    private final ExpressionGenerator expressionGenerator;
    private final MethodVisitor methodVisitor;

    public AdditiveGenerator(ExpressionGenerator expressionGenerator, MethodVisitor methodVisitor) {
        this.expressionGenerator = expressionGenerator;
        this.methodVisitor = methodVisitor;
    }

    public void generate(AdditiveExpression additiveExpression) {
        evaluateArithmeticComponents(additiveExpression);
        Type type = additiveExpression.getType();
        methodVisitor.visitInsn(type.getAddOpcode());
    }

    private void evaluateArithmeticComponents(AdditiveExpression expression) {
        Expression leftExpression = expression.leftExpression;
        Expression rightExpression = expression.rightExpression;
        leftExpression.accept(expressionGenerator);
        rightExpression.accept(expressionGenerator);
    }


}
