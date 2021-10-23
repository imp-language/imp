package org.imp.jvm.statement;

import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.LocalVariable;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.exception.Errors;
import org.imp.jvm.expression.Function;
import org.imp.jvm.types.*;
import org.objectweb.asm.MethodVisitor;

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
        System.err.println();

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
//        localVariable = new LocalVariable(name, type);
//        scope.addLocalVariable(localVariable);


        // Add all methods on the class to the scope
        var methods = foundClass.getMethods();
        for (var method : methods) {
//            System.out.println(method);
            String methodName = method.getName();
            FunctionType functionType = scope.findFunctionType(methodName);
            // If no FunctionTypes of name exist on the current scope,
            if (functionType == null) {
                // Create a new FunctionType and add it to the scope
                functionType = new FunctionType(methodName, null);
                scope.functionTypes.add(functionType);
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

                    function.parameters.add(0, new Identifier("_", type));
                    functionType.signatures.put(Function.getDescriptor(function.parameters), function);
                    scope.functionTypes.add(functionType);

                }

            } catch (NullPointerException e) {
                System.err.println("Skipped " + methodName);
            }


        }

        System.out.println(scope);
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
