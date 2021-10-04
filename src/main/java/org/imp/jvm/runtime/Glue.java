package org.imp.jvm.runtime;

import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.expression.Expression;
import org.imp.jvm.expression.Function;
import org.imp.jvm.runtime.stdlib.Batteries;
import org.imp.jvm.runtime.stdlib.Math;
import org.imp.jvm.tool.Imp;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.FunctionType;
import org.imp.jvm.types.Type;
import org.imp.jvm.types.TypeResolver;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Expose standard library methods to Imp programs
 */
public class Glue {
//    public static Set<String> coreModules = new HashSet<>(Arrays.asList(
//            "math",
//            "path",
//            "process",
//            "test",
//            "file",
//            "imp"
//    ));

    public static Map<String, Class<?>> coreModules = new HashMap<>();

    static {
        coreModules.put("math", Math.class);
    }


    public static FunctionType findLogFunction(List<Expression> arguments, ImpFile owner) {
        String name = "log";

        // use the faster logger
        if (arguments.size() <= 1) {
            name = "log_single";
        }

        Class<?>[] classes = new Class[1];
        classes[0] = Object[].class;
        Method method = null;

        if (arguments.size() <= 1) {
            try {
                method = Batteries.class.getMethod(name, Object.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        } else {
            try {
                method = Batteries.class.getMethod(name, classes);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        if (method != null) {
            FunctionType f = new FunctionType(name, owner);
            List<Identifier> identifiers = new ArrayList<>();

            for (var arg : arguments) {
                var ident = new Identifier(arg.type.getName(), arg.type);
                identifiers.add(ident);
            }

//            Type r = TypeResolver.getBuiltInTypeByClass(method.getReturnType());

            Function function = new Function(f, identifiers, null, true);
            f.signatures.put(Function.getDescriptor(function.parameters), function);
            return f;
        }
        return null;
    }

    private static Function buildFunctionFromMethod(Method method, FunctionType functionType) {
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
//                System.exit(729);
            }
        }

        var r = TypeResolver.getBuiltInTypeByClass(method.getReturnType());
        if (r.isPresent()) {
            Function function = new Function(functionType, identifiers, r.get(), true);
            return function;
        }

        return null;
    }

    /**
     * Find a function in the always-imported "batteries" module
     *
     * @param methodName name of function
     * @param owner      ImpFile
     * @return FunctionType | null
     */
    public static FunctionType findBatteriesFunction(String methodName, ImpFile owner) {
        return findFunction(Batteries.class, methodName, owner);
    }

    private static FunctionType findFunction(Class<?> module, String methodName, ImpFile owner) {
        String finalMethodName = methodName;
        List<Method> methods = Arrays.stream(module.getMethods()).filter(m -> {
            return m.getName().equals(finalMethodName) || m.getName().equals("_" + finalMethodName);
        }).collect(Collectors.toList());


        if (methods.size() > 0) {
            // Some methods in the JVM implementation of `batteries` must be prefixed
            // (we use a "_") to avoid using Java reserved words like `float` or `int`.
            if (methods.get(0).getName().startsWith("_")) methodName = "_" + methodName;
            FunctionType functionType = new FunctionType(methodName, owner);

            for (var method : methods) {
                Function function = buildFunctionFromMethod(method, functionType);
                functionType.signatures.put(Function.getDescriptor(function.parameters), function);
            }

            return functionType;
        }
        return null;
    }

    public static FunctionType findStandardLibraryFunction(String moduleName, String methodName, ImpFile owner) {
        if (!coreModules.containsKey(moduleName)) return null;
        var c = coreModules.get(moduleName);
        FunctionType functionType = findFunction(c, methodName, owner);
//        functionType.signatures.put(Function.getDescriptor())
        return functionType;
    }

    public static FunctionType findStandardLibraryFunction(List<Expression> arguments, String name, ImpFile owner) {
        if (name.equals("log")) {
            return Glue.findLogFunction(arguments, owner);
        }
        Class<?>[] classes = new Class[arguments.size()];

        if (name.equals("typeof")) {
            classes[0] = Object.class;
        } else {
            // Convert arguments List<Expression> to Class<?>
            for (int i = 0; i < arguments.size(); i++) {
                Type t = arguments.get(i).type;
                var c = t.getTypeClass();
                classes[i] = c;
            }
        }

        // Some methods are prefixed with _ in the Java
        // definitions. This code finds the correct function,
        // assuming `x` and `_x` are not both defined.
        Method method = null;
        try {
            method = Batteries.class.getMethod(name, classes);
        } catch (NoSuchMethodException e) {
            try {
                method = Batteries.class.getMethod("_" + name, classes);
            } catch (NoSuchMethodException noSuchMethodException) {
                method = null;
            }
        }

        // Create a pseudo AST node to support compilation.
        if (method != null) {
            String methodName = method.getName();
            FunctionType f = new FunctionType(methodName, owner);
            List<Identifier> identifiers = new ArrayList<>();
            for (var c : Arrays.asList(classes)) {
                var builtin = TypeResolver.getBuiltInType(c.getTypeName());
                if (builtin.isPresent()) {
                    var i = new Identifier(c.getName(), builtin.get());
                    identifiers.add(i);
                }
            }

            if (name.equals("typeof")) {
                identifiers.add(new Identifier("o", BuiltInType.OBJECT));
            }

            Optional<BuiltInType> r = TypeResolver.getBuiltInTypeByClass(method.getReturnType());

            Function function = new Function(f, identifiers, r.get(), true);
            f.signatures.put(Function.getDescriptor(function.parameters), function);
            return f;
        }
        return null;
    }
}
