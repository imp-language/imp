package org.imp.jvm.runtime;

import org.imp.jvm.SourceFile;
import org.imp.jvm.legacy.domain.scope.Identifier;
import org.imp.jvm.legacy.expression.Function;
import org.imp.jvm.types.*;
import org.imp.runtime.Batteries;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Expose standard library methods to Imp programs
 */
public class Glue {

    public static final Map<String, Class<?>> coreModules = new HashMap<>();

    static {
        coreModules.put("batteries", Batteries.class);
        coreModules.put("math", MathLib.class);
    }

    public static List<FuncType> getExports(String module) {
        var result = new ArrayList<FuncType>();
        if (coreModules.containsKey(module)) {
            var c = coreModules.get(module);
            var m = c.getMethods();
            for (var method : m) {
                String name = method.getName();
                if (method.getDeclaringClass().equals(Object.class)) {
                    continue;
                }
                var parameters = new ArrayList<Identifier>();
                for (var p : method.getParameterTypes()) {
                    var id = new Identifier("_", new ExternalType(p));
                    parameters.add(id);
                }
                boolean isPrefixed = false;
                if (name.startsWith("_")) {
                    name = name.substring(1);
                    isPrefixed = true;
                }
                var funcType = new FuncType(name, Modifier.NONE, parameters);
                funcType.isPrefixed = isPrefixed;
                funcType.returnType = new ExternalType(method.getReturnType());

                var bt = BuiltInType.getFromString(method.getReturnType().getName());
                if (bt != null) funcType.returnType = bt;

                funcType.glue = true;
                result.add(funcType);

            }

        }
        return result;
    }

    /**
     * Find method from the standard library.
     *
     * @param moduleName batteries, math, path, etc
     * @param methodName the name of the standard library method
     * @param source     current file
     * @return the function type
     */
    public static FunctionType findStandardLibraryFunction(String moduleName, String methodName, SourceFile source) {
        if (!coreModules.containsKey(moduleName)) return null;
        var c = coreModules.get(moduleName);
        return findFunction(c, methodName, source);
    }

    private static Function buildImpFunctionFromJavaMethod(Method method, FunctionType functionType) {
        var argumentClasses = method.getParameterTypes();
        var identifiers = new ArrayList<Identifier>();

        for (Class<?> c : argumentClasses) {
            String typeName = c.getTypeName();
            Optional<BuiltInType> type = TypeResolver.getBuiltInTypeByClass(c);
            if (type.isPresent()) {
                var identifier = new Identifier(typeName, type.get());
                identifiers.add(identifier);
            } else {
                var identifier = new Identifier(typeName, BuiltInType.OBJECT_ARR);
                identifiers.add(identifier);
            }
        }

        var r = TypeResolver.getBuiltInTypeByClass(method.getReturnType());
        return r.map(builtInType -> new Function(functionType, identifiers, builtInType, Function.FunctionKind.Standard)).orElse(null);
    }


    private static FunctionType findFunction(Class<?> module, String methodName, SourceFile source) {
        List<Method> methods = Arrays.stream(module.getMethods())
                .filter(m -> m.getName().equals(methodName) || m.getName().equals("_" + methodName))
                .collect(Collectors.toList());

//        if (methods.size() > 0) {
//            // Some methods in the JVM implementation of `batteries` must be prefixed
//            // (we use a "_") to avoid using Java reserved words like `float` or `int`.
//            if (methods.get(0).getName().startsWith("_")) methodName = "_" + methodName;
//            FunctionType functionType = new FunctionType(methodName, source, false);
//
//            for (var method : methods) {
//                Function function = buildImpFunctionFromJavaMethod(method, functionType);
//                functionType.addSignature(Function.getDescriptor(function.parameters), function);
//            }
//
//            return functionType;
//        }
        return null;
    }


}
