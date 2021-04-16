package org.imp.jvm.expression;

import org.imp.jvm.codegen.DescriptorFactory;
import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.Identifier;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;

public class FunctionCall extends Expression {
    public final FunctionSignature signature;
    public List<Expression> arguments;

    public FunctionCall(FunctionSignature signature, List<Expression> arguments) {
        this.signature = signature;
        this.arguments = arguments;
        this.type = signature.type;
    }

    public void generate(MethodVisitor mv) {
        // generate arguments
        arguments.forEach(argument -> argument.generate(mv));

        // bytecode
        String methodDescriptor = DescriptorFactory.getMethodDescriptor(signature);
        String ownerDescriptor = "java/lang/Object";
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, ownerDescriptor, signature.name, methodDescriptor, false);

    }

}
