package org.imp.jvm.domain.types;

import org.antlr.v4.runtime.ParserRuleContext;
import org.apache.commons.lang3.StringUtils;
import org.imp.jvm.ImpParser;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.statement.Struct;

import java.util.Arrays;
import java.util.Optional;

public class TypeResolver {

    public static Type getFromTypeContext(ImpParser.TypeContext typeContext, Scope scope) {
        if (typeContext instanceof ImpParser.TypePrimitiveContext) {
            Optional<BuiltInType> builtInType = getBuiltInType(typeContext.getText());
            if (builtInType.isPresent()) {
                return builtInType.get();
            }
        } else if (typeContext instanceof ImpParser.TypeStructContext) {
            ImpParser.TypeStructContext tsc = (ImpParser.TypeStructContext) typeContext;
            Struct struct = scope.getStruct(tsc.identifier().getText());
            return new StructType(struct);
        } else if (typeContext instanceof ImpParser.TypeListContext) {
            return null;
        } else {
            System.err.println("ree");
        }
        return null;
    }

    public static Type getFromName(String name, Scope scope) {
        Optional<BuiltInType> builtInType = getBuiltInType(name);
        if (builtInType.isPresent()) return builtInType.get();

        Struct struct = scope.getStruct(name);
        return new StructType(struct);

    }

    public static Type getTemporaryType(ImpParser.TypeContext typeContext) {
        if (typeContext == null) return null;
        String text = typeContext.getText();
        if (typeContext instanceof ImpParser.TypePrimitiveContext) {
            Optional<BuiltInType> builtInType = getBuiltInType(text);
            if (builtInType.isPresent()) {
                return builtInType.get();
            }
        } else if (text.length() > 0) {
            return new StructType(text);
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

    private static Optional<BuiltInType> getBuiltInType(String typeName) {
        return Arrays.stream(BuiltInType.values())
                .filter(type -> type.getName().equals(typeName))
                .findFirst();
    }

    public static final class TypeChecker {
        public static boolean isInt(Type type) {
            return type == BuiltInType.INT;
        }

        public static boolean isBool(Type type) {
            return type == BuiltInType.BOOLEAN;
        }

        public static boolean isFloat(Type type) {
            return type == BuiltInType.FLOAT;
        }

        public static boolean isDouble(Type type) {
            return type == BuiltInType.DOUBLE;
        }
    }
}
