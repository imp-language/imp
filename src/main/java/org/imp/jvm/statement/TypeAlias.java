package org.imp.jvm.statement;

import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.LocalVariable;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.exception.Errors;
import org.imp.jvm.expression.Function;
import org.imp.jvm.types.*;
import org.objectweb.asm.MethodVisitor;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Optional;

public class TypeAlias extends Statement {

    public final String name;
    public final String extern;

    private LocalVariable localVariable;

    public TypeAlias(String name, String extern) {

        this.name = name;
        this.extern = extern;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {

    }

    @Override
    public void validate(Scope scope) {
        /*
         * Steps to support Java interop:
         * 1. Find the extern class
         * 2. Add new type in scope
         * 3. Add all methods on class for valid interop
         */

        // Find the external class
        var c = getClass(extern);
        if (c.isEmpty()) {
            Logger.syntaxError(Errors.ExternNotFound, this, extern);
            return;
        }
        Class<?> foundClass = c.get();

        // Add a new type to the scope
        ExternalType type = new ExternalType(foundClass);
        scope.addType(name, type);
        localVariable = new LocalVariable(name, type);
        scope.addLocalVariable(localVariable);


        // Add all methods on the class to the scope
        var methods = foundClass.getMethods();
        for (var method : methods) {
            int modifiers = method.getModifiers();
            boolean isStatic = Modifier.isStatic(modifiers);
//            System.out.println(method);

            String methodName = method.getName();

            /**
             * java.lang.Math.sqrt() and stdlib.math.sqrt() are conflicting.
             * The java version is static, the imp version is not.
             * We're currently tracking if a function is static in the function
             * type. Instead, we must keep this in the signature.
             */

            FunctionType functionType = scope.findFunctionType(methodName, isStatic);
            // If no FunctionTypes of name exist on the current scope,
            if (functionType == null) {
                // Create a new FunctionType and add it to the scope
                functionType = new FunctionType(methodName, null, isStatic);
                scope.addFunctionType(functionType);
            }
            var argumentClasses = method.getParameterTypes();
            var identifiers = new ArrayList<Identifier>();

            for (Class<?> arg : argumentClasses) {
                String typeName = arg.getTypeName();
                // Todo: support parameters of non-primitive types
                Optional<BuiltInType> t = TypeResolver.getBuiltInTypeByClass(arg);
                if (t.isPresent()) {
                    var identifier = new Identifier(typeName, t.get());
                    identifiers.add(identifier);
                } else {
                    var identifier = new Identifier(typeName, BuiltInType.OBJECT_ARR);
                    identifiers.add(identifier);
                }
            }
            try {
                var r = TypeResolver.getBuiltInTypeByClass(method.getReturnType());

                if (r.isPresent()) {
                    BuiltInType bt = r.get();
                    Function function = new Function(functionType, identifiers, bt, Function.FunctionKind.External);

                    if (!isStatic) {
                        function.parameters.add(0, new Identifier("_", type));
                    }
                    functionType.addSignature(Function.getDescriptor(function.parameters), function);

                }

            } catch (NullPointerException e) {
                System.err.println("Skipped " + methodName);
            }


        }

    }

    private Optional<Class<?>> getClass(String externalName) {
        try {
            Class<?> c = Class.forName(externalName);
            return Optional.of(c);
        } catch (ClassNotFoundException e) {
            return Optional.empty();
        }
    }
}
