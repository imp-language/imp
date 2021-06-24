package org.imp.jvm.expression;

import org.imp.jvm.compiler.DescriptorFactory;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.types.FunctionType;
import org.imp.jvm.types.StructType;
import org.imp.jvm.types.Type;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;
import java.util.stream.Collectors;

public class FunctionCall extends Expression {
    public FunctionSignature signature;
    public List<Expression> arguments;
    public final ImpFile owner;

    public List<Type> argTypes;
    public String name;

    public FunctionCall(FunctionSignature signature, List<Expression> arguments, ImpFile owner) {
        this.signature = signature;
        this.arguments = arguments;
        this.owner = owner;
        this.type = signature.type;
    }

    public FunctionCall(String name, List<Expression> arguments, ImpFile owner) {
        this.name = name;
        this.arguments = arguments;

        this.signature = null;
        this.owner = owner;
    }

    @Override
    public void validate(Scope scope) {
        // Find the types of each of the arguments
        for (var arg : arguments) {
            arg.validate(scope);
        }
        argTypes = arguments.stream().map(expression -> expression.type).collect(Collectors.toList());

        if (name.equals("log")) {
            return;
        }

        // Find a FunctionType in the current scope by name
        FunctionType functionType = scope.findFunctionType(this.name);
        if (functionType == null) {
            System.err.println("Error! no functions of name " + this.name);
            System.exit(11);
        }

        // Find a function that exists in the current scope that matches the FunctionSignature

        signature = functionType.getSignatureByTypes(this.name, this.argTypes);
        if (signature == null) {
            System.err.println("Error! no matching parameters for function " + this.name);
            System.exit(12);
        }
    }

    public void generate(MethodVisitor mv, Scope scope) {
        // generate arguments

        if (name.equals("log")) {
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            arguments.get(0).generate(mv, scope);

            // get the variation of println to call
            Type argType = arguments.get(0).type;


            String descriptor = "(" + argType.getDescriptor() + ")V";

            // Todo: account for custom toString methods on structs
            if (argType instanceof StructType) {
                descriptor = "(Ljava/lang/Object;)V";
            }

            String name = "java.io.PrintStream";
            String fieldDescriptor = "L" + name.replace('.', '/') + ";";

//            descriptor = org.objectweb.asm.Type.getDescriptor(java.io.PrintStream.class);


            String owner = "java/io/PrintStream";
            name = "println"; // name of the method we call

            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, owner, name, descriptor, false);
            return;
        } else {
            // bytecode
            String methodDescriptor = DescriptorFactory.getMethodDescriptor(signature);
            // Todo: error for missing function signatures

            for (var arg : arguments) {
                arg.generate(mv, scope);
            }

            // Function calls withing a single module never are accessed like module.func()
            // So the owner of each is the static class.
//            String ownerDescriptor = owner.getInternalName();
            String ownerDescriptor = owner.name + "/Entry";
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, ownerDescriptor, signature.name, methodDescriptor, false);

        }


    }


}
