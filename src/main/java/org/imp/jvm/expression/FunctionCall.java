package org.imp.jvm.expression;

import org.imp.jvm.compiler.DescriptorFactory;
import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.types.ClassType;
import org.imp.jvm.domain.types.StructType;
import org.imp.jvm.domain.types.Type;
import org.imp.jvm.statement.Statement;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;
import java.util.stream.Collectors;

public class FunctionCall extends Expression {
    public final FunctionSignature signature;
    public List<Expression> arguments;
    public final Type owner;

    public List<Type> argTypes;
    public String name;

    public FunctionCall(FunctionSignature signature, List<Expression> arguments, Type owner) {
        this.signature = signature;
        this.arguments = arguments;
        this.owner = owner;
        this.type = signature.type;
    }

    public FunctionCall(String name, List<Expression> arguments) {
        this.name = name;
        this.arguments = arguments;

        this.signature = null;
        this.owner = new ClassType("Entry");
    }

    @Override
    public void validate() {
        // Find the types of each of the arguments
        arguments.forEach(Statement::validate);
        argTypes = arguments.stream().map(expression -> expression.type).collect(Collectors.toList());

        // Find a function that exists in the current scope that matches the FunctionSignature

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
            FunctionSignature signature = scope.getSignatureByTypes(this.name, this.argTypes);
            arguments.forEach(argument -> argument.generate(mv, scope));


            // bytecode
            String methodDescriptor = DescriptorFactory.getMethodDescriptor(signature);
//            methodDescriptor = "(Lscratch/Person;)V";

            // Function calls withing a single module never are accessed like module.func()
            // So the owner of each is the static class.
            String ownerDescriptor = owner.getInternalName();
            ownerDescriptor = "scratch/Entry";
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, ownerDescriptor, signature.name, methodDescriptor, false);

        }


    }


}
