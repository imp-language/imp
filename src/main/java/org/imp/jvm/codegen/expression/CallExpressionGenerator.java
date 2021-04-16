package org.imp.jvm.codegen.expression;

import org.imp.jvm.codegen.DescriptorFactory;
import org.imp.jvm.domain.expression.Call;
import org.imp.jvm.domain.expression.Expression;
import org.imp.jvm.domain.expression.FunctionCall;
import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.lang.reflect.Parameter;
import java.util.List;
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
        generateArguments(functionCall, functionCall.signature);
        String functionName = functionCall.signature.name;
        String methodDescriptor = DescriptorFactory.getMethodDescriptor(functionCall.signature);
        String ownerDescriptor = "java/lang/Object";
        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, ownerDescriptor, functionName, methodDescriptor, false);

    }

    private void generateArguments(FunctionCall call, FunctionSignature signature) {
        List<Identifier> parameters = signature.parameters;
        List<Expression> arguments = call.arguments;
//        if (arguments.size() > parameters.size()) {
//            throw new BadArgumentsToFunctionCallException(call);
//        }
//        arguments = getSortedArguments(arguments, parameters);
        arguments.forEach(argument -> argument.accept(expressionGenerator));
//        generateDefaultParameters(call, parameters, arguments);
    }


}
