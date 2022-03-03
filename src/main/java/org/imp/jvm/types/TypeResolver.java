package org.imp.jvm.types;

import org.apache.commons.lang3.StringUtils;
import org.imp.jvm.ImpParser;
import org.imp.jvm.domain.scope.Scope;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class TypeResolver {

    public static ImpType getFromTypeContext(ImpParser.TypeContext typeContext, Scope scope) {
        if (typeContext instanceof ImpParser.TypePrimitiveContext) {
            Optional<BuiltInType> builtInType = getBuiltInType(typeContext.getText());
            if (builtInType.isPresent()) {
                return builtInType.get();
            }
        } else if (typeContext instanceof ImpParser.TypeStructContext tsc) {
            return scope.getType(tsc.identifier().getText());
        } else if (typeContext instanceof ImpParser.TypeListContext typeListContext) {
            Optional<BuiltInType> builtInType = getBuiltInType(typeListContext.t.getText());
            return builtInType.map(ListType::new).orElse(null);
        } else {
            System.err.println("reea");
        }
        return null;
    }

    public static ImpType getFromName(String name, Scope scope) {
        Optional<BuiltInType> builtInType = getBuiltInType(name);
        if (builtInType.isPresent()) return builtInType.get();

        return scope.getType(name);

    }

    public static ImpType getTemporaryType(ImpParser.TypeContext typeContext) {
        if (typeContext == null) return null;
        String text = typeContext.getText();
        if (typeContext instanceof ImpParser.TypePrimitiveContext) {
            Optional<BuiltInType> builtInType = getBuiltInType(text);
            if (builtInType.isPresent()) {
                return builtInType.get();
            }
        } else if (text.length() > 0) {
            return new StructType(text, new ArrayList<>());
        }
        return null;
    }


    public static Object getValueFromString(String value, BuiltInType t) {
        Object result;
        switch (t) {
            case BOOLEAN -> result = Boolean.valueOf(value);
            case INT -> result = Integer.valueOf(value);
            case FLOAT -> result = Float.valueOf(value);
            case DOUBLE -> result = Double.valueOf(value);
            case STRING -> {
                value = StringUtils.removeStart(value, "\"");
                value = StringUtils.removeEnd(value, "\"");
                result = value;
            }
            default -> throw new AssertionError("Objects not yet implemented!");

        }
        return result;
    }


    public static Optional<BuiltInType> getBuiltInType(String typeName) {
        // Todo: bad way of doing this
        if (typeName.equals("boolean")) {
            typeName = "bool";
        }
        if (typeName.equals("java.lang.String")) typeName = "string";
        String finalTypeName = typeName;
        return Arrays.stream(BuiltInType.values())
                .filter(type -> type.getName().equals(finalTypeName))
                .findFirst();
    }


    public static Optional<BuiltInType> getBuiltInTypeByClass(Class<?> c) {
        return Arrays.stream(BuiltInType.values())
                .filter(type -> c.equals(type.getTypeClass()))
                .findFirst();
    }

    public static final class TypeChecker {
        public static boolean isInt(ImpType type) {
            return type == BuiltInType.INT;
        }

        public static boolean isBool(ImpType type) {
            return type == BuiltInType.BOOLEAN;
        }

        public static boolean isFloat(ImpType type) {
            return type == BuiltInType.FLOAT;
        }

        public static boolean isDouble(ImpType type) {
            return type == BuiltInType.DOUBLE;
        }
    }
}
