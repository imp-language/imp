package org.imp.jvm.expression;

import org.imp.jvm.compiler.DescriptorFactory;
import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.LocalVariable;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.exception.Errors;
import org.imp.jvm.expression.reference.ModuleReference;
import org.imp.jvm.expression.reference.VariableReference;
import org.imp.jvm.runtime.Glue;
import org.imp.jvm.runtime.stdlib.Batteries;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.FunctionType;
import org.imp.jvm.types.Type;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionCall extends Expression {
    public final ImpFile owner;
    public final String name;
    public Function function;
    public List<Expression> arguments;
    public List<Type> argTypes;
    private boolean hasBeenInitialized = false;

    private Class<?> moduleClass = null;


    public FunctionCall(String name, List<Expression> arguments, ImpFile owner) {
        this.name = name;
        this.arguments = arguments;

        this.function = null;
        this.owner = owner;
    }

    public void generate(MethodVisitor mv, Scope scope) {
        // generate arguments

        // Todo: split isStandard into an enum with Standard, Internal, and External cases.
        // External doesn't need the closure init that Internal does.
        if (function.kind == Function.FunctionKind.Standard) {
            generateStandardCall(mv, scope);
        } else if (function.kind == Function.FunctionKind.External) {
            generateExternalCall(mv, scope);
        } else {
            generateInternalCall(mv, scope);


        }
    }

    @Override
    public void validate(Scope scope) {

        // Find the types of each of the arguments
        for (var arg : arguments) {
            arg.validate(scope);
        }
        argTypes = arguments.stream().map(expression -> expression.type).collect(Collectors.toList());





        /*
         * Functions are resolved in a specific order, first by name and then by parameter types.
         * The order of search by name is as follows:
         *
         * 1. Functions in the always-imported "batteries" module
         * 2. Functions defined in the current scope.
         * 3. Functions from any imported standard library modules.
         * 4. Functions from user defined modules.
         *
         * If no match is found by name, search by parameters does not occur.
         *
         * Search by parameters performs an exact match.
         */
        FunctionType functionType = this.searchByName(scope);

        // If not found at all, error
        if (functionType == null) {
            Logger.syntaxError(Errors.FunctionNotFound, this, getCtx().getStart().getText());
            return;
        }

        if (this.arguments.size() > 0 && this.arguments.get(0).type == BuiltInType.MODULE) {
            var variableReference = (VariableReference) this.arguments.get(0);
            var moduleReference = (ModuleReference) variableReference.reference;
            this.argTypes = this.argTypes.subList(1, this.argTypes.size());
            this.arguments = this.arguments.subList(1, this.arguments.size());
            this.moduleClass = Glue.coreModules.get(moduleReference.name);
        }

        if (functionType.isStatic) {
            this.argTypes = this.argTypes.subList(1, this.argTypes.size());
//            this.argTypes.add(0, null);
        }

        this.function = functionType.getSignatureByTypes(this.argTypes);
        if (this.function == null) {
            String types = this.argTypes.stream().map(Object::toString).collect(Collectors.joining(", "));
            Logger.syntaxError(Errors.FunctionSignatureMismatch, this, getCtx().getStart().getText(), types);
            Logger.killIfErrors("Functions not found.");
            return;
        }
        this.type = function.returnType;

        // Store as new local variable
        if (!functionType.isStatic) {
            scope.addLocalVariable(new LocalVariable(function.functionType.name, function.functionType));
        }

        Logger.killIfErrors("Missing variables.");
    }

    private void castLogArgument(MethodVisitor mv, Scope scope) {
        var arg = arguments.get(0);
        arg.generate(mv, scope);
        if (arg.type instanceof BuiltInType bt) {
            bt.doBoxing(mv);
        }
    }

    private void generateExternalCall(MethodVisitor mv, Scope scope) {
        int opcode = Opcodes.INVOKESTATIC;
        // Load the instance of a Java class
        var owner = arguments.get(0);
        if (!function.functionType.isStatic) {
            owner.generate(mv, scope);
            opcode = Opcodes.INVOKEVIRTUAL;
        }

        // Generate all other method args
        var otherArgs = arguments.subList(1, arguments.size());
        for (var arg : otherArgs) {
            arg.generate(mv, scope);
        }

        Type ownerType = owner.type;

        String descriptor = DescriptorFactory.getMethodDescriptor(otherArgs.stream().map(a -> new Identifier("_", a.type)).collect(Collectors.toList()), function.returnType);

        mv.visitMethodInsn(opcode, ownerType.getInternalName(), this.name, descriptor, false);
    }

    private void generateInternalCall(MethodVisitor mv, Scope scope) {
        // 0. If the First Class Function has not been initialized, do so.
        String localVariableName = this.name;

        if (!hasBeenInitialized) {
            // Initialize the first-class function closure object
            String ownerDescriptor = this.function.functionType.getInternalName();
            mv.visitTypeInsn(Opcodes.NEW, ownerDescriptor);
            mv.visitInsn(Opcodes.DUP);

            // Call constructor on first-class function closure object
            List<Identifier> params = Collections.emptyList();
            String methodDescriptor = DescriptorFactory.getMethodDescriptor(params, BuiltInType.VOID);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, ownerDescriptor, "<init>", methodDescriptor, false);

            mv.visitVarInsn(Opcodes.ASTORE, scope.getLocalVariableIndex(localVariableName));
            hasBeenInitialized = true;
        }

        // 1. Load the First Class Function object
        String ownerDescriptor = this.function.functionType.getInternalName();
        int index = scope.getLocalVariableIndex(localVariableName);
        mv.visitVarInsn(Opcodes.ALOAD, index);

        // 2. Load the variables that must be passed to the closure
        var s = function.functionType.getSignature(0).block.scope;
        List<Identifier> closureParams = new ArrayList<>();
        for (var p : s.closures.values()) {
            var identifier = new Identifier(p.getName(), BuiltInType.BOX);
            closureParams.add(identifier);
            int i = scope.getLocalVariableIndex(p.getName());
            mv.visitVarInsn(Opcodes.ALOAD, i);
        }

        // 3. Call the closure() method on the First Class Function object
        String methodDescriptor = DescriptorFactory.getMethodDescriptor(closureParams, BuiltInType.VOID);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ownerDescriptor, "closure", methodDescriptor, false);

        // 4. Generate arguments for the function itself
        index = scope.getLocalVariableIndex(localVariableName);
        mv.visitVarInsn(Opcodes.ALOAD, index);
        // 6. Box types if necessary
        for (int i = 0; i < function.parameters.size(); i++) {
            var param = function.parameters.get(i);
            var arg = arguments.get(i);
            arg.generate(mv, scope);
            if (arg.type instanceof BuiltInType bt && param.type != arg.type) {
                bt.doBoxing(mv);
            }
        }

        // 5. Call the appropriate invoke method on the First Class Function object
        List<Identifier> params = arguments.stream().map(arg -> new Identifier(arg.type.getName(), arg.type)).collect(Collectors.toList());

        Type returnType = this.function.returnType;
        methodDescriptor = DescriptorFactory.getMethodDescriptor(function.parameters, returnType);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, ownerDescriptor, "invoke", methodDescriptor, false);

    }

    private void generateStandardCall(MethodVisitor mv, Scope scope) {
        String owner = Batteries.class.getName().replace('.', '/');
        /*
         * Before calling the function, we must consider 3 cases:
         *
         * 1. The call is to `log(...args)`, we must generate the code to pass varargs to the function
         * 2. The call is to `log(arg)`, we just have to cast the value to Object.
         * 3. The call is to any other function, just generate the arguments.
         */

        if (this.name.equals("log") && arguments.size() > 1) {
            wrapLogArguments(mv, scope);
        } else if (this.name.equals("log") && arguments.size() == 1) {
            castLogArgument(mv, scope);
        } else {
            var argList = this.arguments;
            if (this.moduleClass != null) {
                owner = this.moduleClass.getName().replace(".", "/");
            }
            for (var arg : argList) {
                arg.generate(mv, scope);
            }

        }

        List<Identifier> params = arguments.stream().map(arg -> new Identifier(arg.type.getName(), arg.type)).collect(Collectors.toList());
        Type returnType = this.function.returnType;
        String name = this.function.functionType.name;
        String methodDescriptor = DescriptorFactory.getMethodDescriptor(params, returnType);

        // Todo: this won't need to happen once the type system has settled
        // For now `log` must accept all values.
        if (this.name.equals("log")) {
            if (arguments.size() > 1) methodDescriptor = "([Ljava/lang/Object;)V";
            else if (arguments.size() == 1) methodDescriptor = "(Ljava/lang/Object;)V";
        }
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, owner, name, methodDescriptor, false);
    }

    /**
     * @param name a
     * @return a
     * @deprecated
     */
    private FunctionType getFunctionType(String name) {
        for (var imported : this.owner.qualifiedImports) {
            for (var func : imported.functions) {
                if (func.functionType.name.equals(name)) {
                    return func.functionType;
                }
            }
        }
        return null;
    }

    private FunctionType searchByName(Scope scope) {
        FunctionType functionType;

        // 1. Functions in the always-imported "batteries" module
        functionType = Glue.findStandardLibraryFunction("batteries", this.name, this.owner);
        if (functionType != null) return functionType;

        // 2. Functions defined in the current scope.
        boolean isStatic = false;

        functionType = scope.findFunctionType(this.name, true);
        if (functionType != null) return functionType;
        functionType = scope.findFunctionType(this.name, false);
        if (functionType != null) return functionType;

        if (arguments.size() > 0) {
            var potentialVariableReference = arguments.get(0);
            if (potentialVariableReference instanceof VariableReference variableReference) {
                if (variableReference.reference instanceof ModuleReference moduleReference) {
                    String moduleName = moduleReference.name;
                    if (this.owner.stdlibImports.contains(moduleName)) {
                        // 3. Functions from any imported standard library modules.
                        functionType = Glue.findStandardLibraryFunction(moduleName, this.name, this.owner);
                    } else {
                        // 4. Functions from user defined modules.
                        var importedFile = this.owner.qualifiedImports.stream().filter(impFile -> impFile.getBaseName().equals(moduleName)).findFirst();
                        if (importedFile.isPresent()) {
                            var imported = importedFile.get();
                            var func = imported.functions.stream().filter(f -> f.functionType.name.equals(this.name)).findFirst();
                            if (func.isPresent()) {
                                functionType = func.get().functionType;
                            }
                        }
                        // file imported here
                    }
                    // Todo: potentially error here, don't need to check anything else if name not in module.
                }
            }
        }
        return functionType;
    }

    private void wrapLogArguments(MethodVisitor mv, Scope scope) {
        mv.visitLdcInsn(arguments.size());
        mv.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object");

        for (int i = 0; i < arguments.size(); i++) {
            mv.visitInsn(Opcodes.DUP);
            mv.visitLdcInsn(i);
            var arg = arguments.get(i);
            arg.generate(mv, scope);
            if (arg.type instanceof BuiltInType bt) {
                bt.doBoxing(mv);
            }
            mv.visitInsn(Opcodes.AASTORE);
        }
    }
}
