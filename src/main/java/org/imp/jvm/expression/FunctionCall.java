package org.imp.jvm.expression;

import org.imp.jvm.compiler.DescriptorFactory;
import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.LocalVariable;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.exception.SemanticErrors;
import org.imp.jvm.statement.Function;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.FunctionType;
import org.imp.jvm.types.StructType;
import org.imp.jvm.types.Type;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionCall extends Expression {
    public Function function;
    public List<Expression> arguments;
    public final ImpFile owner;

    public List<Type> argTypes;
    public String name;


    public FunctionCall(Function function, List<Expression> arguments, ImpFile owner) {
        this.function = function;
        this.arguments = arguments;
        this.owner = owner;
        this.type = function.functionType;
    }

    public FunctionCall(String name, List<Expression> arguments, ImpFile owner) {
        this.name = name;
        this.arguments = arguments;

        this.function = null;
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
            Logger.syntaxError(SemanticErrors.FunctionNotFound, getCtx());
            return;
        }

        // Find a function that exists in the current scope that matches the FunctionSignature

        function = functionType.getSignatureByTypes(this.name, this.argTypes);
        if (function == null) {
            Logger.syntaxError(SemanticErrors.FunctionSignatureMismatch, getCtx());
            return;
        }
        this.type = function.functionType;
    }

    public void generate(MethodVisitor mv, Scope scope) {
        // generate arguments

        if (name.equals("log")) {
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            arguments.get(0).generate(mv, scope);

            // get the variation of println to call
            Type argType = arguments.get(0).type;

            if (argType instanceof FunctionType) {
                argType = BuiltInType.STRING;
            }


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
            // 0. Initialize the first-class function closure object
            String ownerDescriptor = this.type.getInternalName();
            mv.visitTypeInsn(Opcodes.NEW, ownerDescriptor);
            mv.visitInsn(Opcodes.DUP);

            // 1. Call constructor on first-class function closure object
            List<Identifier> params = Collections.emptyList();
            String methodDescriptor = DescriptorFactory.getMethodDescriptor(params, BuiltInType.VOID);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, ownerDescriptor, "<init>", methodDescriptor, false);

            // 2. Store the function closure as a local variable.
            // Todo: this doesn't always need to happen. Think about closures.
            String localVariableName = ownerDescriptor + function.functionType.callSites;
            scope.addLocalVariable(new LocalVariable(localVariableName, function.functionType));
            mv.visitVarInsn(Opcodes.ASTORE, scope.getLocalVariableIndex(localVariableName));
            function.functionType.callSites++;

            int index = scope.getLocalVariableIndex(localVariableName);
            mv.visitVarInsn(Opcodes.ALOAD, index);

            // 2. Generate arguments
            for (var arg : arguments) {
                arg.generate(mv, scope);
            }

            params = arguments.stream().map(arg -> new Identifier(arg.type.getName(), arg.type)).collect(Collectors.toList());
            methodDescriptor = DescriptorFactory.getMethodDescriptor(params, BuiltInType.VOID);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ownerDescriptor, "invoke", methodDescriptor, false);

            // bytecode
//            String methodDescriptor = DescriptorFactory.getMethodDescriptor(function);


            // Function calls withing a single module never are accessed like module.func()
            // So the owner of each is the static class.
//            String ownerDescriptor = owner.getInternalName();
//            String ownerDescriptor = owner.name + "/Entry";
//            mv.visitMethodInsn(Opcodes.INVOKESTATIC, ownerDescriptor, function.functionType.name, methodDescriptor, false);

        }


    }


}
