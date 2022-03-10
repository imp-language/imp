package org.imp.jvm.types;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

public class TypeResolver {


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


}
