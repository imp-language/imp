package org.imp.jvm.runtime;

import org.imp.jvm.legacy.ImpFile;
import org.imp.jvm.legacy.domain.scope.Identifier;
import org.imp.jvm.legacy.expression.Function;
import org.imp.jvm.runtime.stdlib.Batteries;
import org.imp.jvm.runtime.stdlib.Math;
import org.imp.jvm.runtime.stdlib.Process;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.FunctionType;
import org.imp.jvm.types.TypeResolver;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Expose standard library methods to Imp programs
 */
public class GlueOld {

    public static final Map<String, Class<?>> coreModules = new HashMap<>();

    static {
        coreModules.put("batteries", Batteries.class);
        coreModules.put("math", MathLib.class);
        coreModules.put("process", Process.class);
    }

    /**
     * Find method from the standard library.
     *
     * @param moduleName batteries, math, path, etc
     * @param methodName the name of the standard library method
     * @param owner      current file
     * @return the function type
     */
    public static FunctionType findStandardLibraryFunction(String moduleName, String methodName, ImpFile owner) {
        if (!coreModules.containsKey(moduleName)) return null;
        var c = coreModules.get(moduleName);
        return findFunction(c, methodName, owner);
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


    private static FunctionType findFunction(Class<?> module, String methodName, ImpFile owner) {
        String finalMethodName = methodName;
        List<Method> methods = Arrays.stream(module.getMethods())
                .filter(m -> m.getName().equals(finalMethodName) || m.getName().equals("_" + finalMethodName)).toList();

        if (methods.size() > 0) {
            // Some methods in the JVM implementation of `batteries` must be prefixed
            // (we use a "_") to avoid using Java reserved words like `float` or `int`.
            if (methods.get(0).getName().startsWith("_")) methodName = "_" + methodName;
            FunctionType functionType = new FunctionType(methodName, owner, false);

            for (var method : methods) {
                Function function = buildImpFunctionFromJavaMethod(method, functionType);
                functionType.addSignature(Function.getDescriptor(function.parameters), function);
            }

            return functionType;
        }
        return null;
    }


}
