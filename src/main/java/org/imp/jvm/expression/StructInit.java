package org.imp.jvm.expression;

import org.imp.jvm.compiler.DescriptorFactory;
import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.types.BuiltInType;
import org.imp.jvm.domain.types.StructType;
import org.imp.jvm.domain.types.Type;
import org.imp.jvm.statement.Struct;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Collections;
import java.util.List;

public class StructInit extends Expression {
    public final Struct struct;
    public List<Expression> arguments;
    public final Type owner;

    public StructInit(Struct struct, List<Expression> arguments, Type owner) {
        this.struct = struct;
        this.arguments = arguments;
        this.owner = owner;
        this.type = new StructType(struct);
    }

    public void generate(MethodVisitor mv, Scope scope) {
        // generate arguments


//        String ownerDescriptor = scope.getClassInternalName(); //example : java/lang/String
        String ownerDescriptor = "scratch/Person";
        mv.visitTypeInsn(Opcodes.NEW, ownerDescriptor); //NEW instruction takes object decriptor as an input
        mv.visitInsn(Opcodes.DUP); //Duplicate (we do not want invokespecial to "eat" our brand new object

//        FunctionSignature methodCallSignature = scope.getMethodCallSignature(constructorCall.getIdentifier(), constructorCall.getArguments());
        FunctionSignature constructorSignature = new FunctionSignature("todo: unused", Collections.emptyList(), BuiltInType.VOID);
        String methodDescriptor = DescriptorFactory.getMethodDescriptor(constructorSignature);
//        String methodDescriptor = "main/Person.<init>:()V";

        arguments.forEach(argument -> argument.generate(mv, scope));


        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, ownerDescriptor, "<init>", methodDescriptor, false);

    }

}
