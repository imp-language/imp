package org.imp.jvm.expression;

import org.imp.jvm.codegen.DescriptorFactory;
import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.types.Type;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.PrintStream;
import java.util.List;

public class FunctionCall extends Expression {
    public final FunctionSignature signature;
    public List<Expression> arguments;
    public final Expression owner;

    public FunctionCall(FunctionSignature signature, List<Expression> arguments, Expression owner) {
        this.signature = signature;
        this.arguments = arguments;
        this.owner = owner;
        this.type = signature.type;
    }

    public void generate(MethodVisitor mv, Scope scope) {
        // generate arguments

        if (signature.name.equals("log")) {
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            arguments.get(0).generate(mv, scope);
            Type argType = arguments.get(0).type;
            String descriptor = "(" + argType.getDescriptor() + ")V";

            String name = "java.io.PrintStream";
            String fieldDescriptor = "L" + name.replace('.', '/') + ";";

            descriptor = org.objectweb.asm.Type.getDescriptor(java.io.PrintStream.class);


            String owner = "java/io/PrintStream";
            name = "println"; // name of the method we call
            descriptor = "(Ljava/lang/String;)V";

            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, owner, "println", descriptor, false);
            return;
        } else {
            arguments.forEach(argument -> argument.generate(mv, scope));


            // bytecode
            String methodDescriptor = DescriptorFactory.getMethodDescriptor(signature);
            String ownerDescriptor = owner.type.getInternalName();
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, ownerDescriptor, signature.name, methodDescriptor, false);

        }


    }

}
