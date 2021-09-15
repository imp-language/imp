package org.imp.jvm.runtime;

import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.expression.Expression;
import org.imp.jvm.expression.Function;
import org.imp.jvm.runtime.stdlib.Batteries;
import org.imp.jvm.types.FunctionType;
import org.imp.jvm.types.Type;
import org.imp.jvm.types.TypeResolver;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Expose standard library methods to Imp programs
 */
public class Glue {
    public static FunctionType findLogFunction(List<Expression> arguments, ImpFile owner) {

        Class<?>[] classes = new Class[1];
        classes[0] = Object[].class;
        Method method = null;
        try {
            method = Batteries.class.getMethod("log", classes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (method != null) {
            FunctionType f = new FunctionType("log", owner);
            List<Identifier> identifiers = new ArrayList<>();

            for (var arg : arguments) {
                var ident = new Identifier(arg.type.getName(), arg.type);
                identifiers.add(ident);
            }
//            arguments.stream().map(arg -> new Identifier(arg.type.getName(), arg.type)).collect(Collectors.toList());


            Type r = TypeResolver.getBuiltInTypeByClass(method.getReturnType());

            Function function = new Function(f, identifiers, r, true);
            f.signatures.put(Function.getDescriptor(function.parameters), function);
            return f;
        }
        return null;
    }

    public static FunctionType findStandardLibraryFunction(List<Expression> arguments, String name, ImpFile owner) {
        if (name.equals("log")) {
            return Glue.findLogFunction(arguments, owner);
        }

        // Convert arguments List<Expression> to Class<?>
        Class<?>[] classes = new Class[arguments.size()];
        for (int i = 0; i < arguments.size(); i++) {
            Type t = arguments.get(i).type;
            var c = t.getTypeClass();
            classes[i] = c;
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

            Type r = TypeResolver.getBuiltInTypeByClass(method.getReturnType());

            Function function = new Function(f, identifiers, r, true);
            f.signatures.put(Function.getDescriptor(function.parameters), function);
            return f;
        }
        return null;
    }
}
