package org.imp.jvm.expression;

import org.imp.jvm.compiler.DescriptorFactory;
import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.types.StructType;
import org.imp.jvm.domain.types.Type;
import org.imp.jvm.statement.Statement;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;

public class FunctionCall extends Expression {
    public final FunctionSignature signature;
    public List<Expression> arguments;
    public final Type owner;

    public FunctionCall(FunctionSignature signature, List<Expression> arguments, Type owner) {
        this.signature = signature;
        this.arguments = arguments;
        this.owner = owner;
        this.type = signature.type;
    }

    @Override
    public void validate() {
        arguments.forEach(Statement::validate);
    }

    public void generate(MethodVisitor mv, Scope scope) {
        // generate arguments

        if (signature.name.equals("log")) {
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
            arguments.forEach(argument -> argument.generate(mv, scope));


            // bytecode
            String methodDescriptor = DescriptorFactory.getMethodDescriptor(signature);
            methodDescriptor = "(Lscratch/Person;)V";

            // Function calls withing a single module never are accessed like module.func()
            // So the owner of each is the static class.
            String ownerDescriptor = owner.getInternalName();
            ownerDescriptor = "scratch/Entry";
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, ownerDescriptor, signature.name, methodDescriptor, false);

        }


    }


}
