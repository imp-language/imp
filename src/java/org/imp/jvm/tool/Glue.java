package org.imp.jvm.tool;

import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.ExternalType;
import org.imp.jvm.types.FuncType;
import org.imp.jvm.types.ImpType;
import org.imp.runtime.Batteries;
import org.imp.runtime.MathLib;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
                var parameters = new ArrayList<Pair<String, ImpType>>();
                for (var p : method.getParameterTypes()) {

                    ImpType t = switch (p.getName()) {
                        case "int" -> BuiltInType.INT;
                        case "float" -> BuiltInType.FLOAT;
                        case "boolean" -> BuiltInType.BOOLEAN;
                        default -> new ExternalType(p);
                    };

                    var id = Pair.with("_", t);
                    parameters.add(id);
                }
                boolean isPrefixed = false;
                if (name.startsWith("_")) {
                    name = name.substring(1);
                    isPrefixed = true;
                }
                var funcType = new FuncType(name, parameters);
                funcType.isPrefixed = isPrefixed;
                funcType.returnType = new ExternalType(method.getReturnType());

                var bt = BuiltInType.getFromString(method.getReturnType().getName());
                if (bt != null) funcType.returnType = bt;

                funcType.glue = true;
                funcType.owner = c.getName().replace('.', '/');
                result.add(funcType);

            }

        }
        return result;
    }


}
