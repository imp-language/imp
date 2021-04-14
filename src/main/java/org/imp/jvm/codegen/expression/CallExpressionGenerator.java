package org.imp.jvm.codegen.expression;

import org.imp.jvm.domain.expression.FunctionCall;
import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.Scope;
import org.objectweb.asm.MethodVisitor;

import java.util.Optional;

public class CallExpressionGenerator {

    private final ExpressionGenerator expressionGenerator;
    private final Scope scope;
    private final MethodVisitor methodVisitor;


    public CallExpressionGenerator(ExpressionGenerator expressionGenerator, Scope scope, MethodVisitor methodVisitor) {
        this.expressionGenerator = expressionGenerator;
        this.scope = scope;
        this.methodVisitor = methodVisitor;
    }

    public void generate(FunctionCall functionCall) {
//        callExpressionGenerator.generate(functionCall);
    }


}
