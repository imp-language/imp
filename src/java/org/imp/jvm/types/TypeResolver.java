package org.imp.jvm.types;

import org.apache.commons.lang3.StringUtils;

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


    /**
     * Defines the type hierarchy for the whole language tbh.
     * Be careful with what goes on here. Rules described below.
     * <ol>
     *     <li>If either type is "java.lang.Object" (meaning "any") the types match.
     *      Assume this is a standard library function that accepts Object.</li>
     *     <li>If both types are BuiltInType, they must be equal to match.</li>
     * </ol>
     */
    public static boolean typesMatch(ImpType a, ImpType b) {
        // 1
        if (a.getName().equals("java.lang.Object") || b.getName().equals("java.lang.Object")) {
            return true;
        }
        // 2
        if (a instanceof BuiltInType bta && b instanceof BuiltInType btb) {
            if (bta.equals(btb)) return true;
        }
        // 3
        if (isList(a)) {
            if (a instanceof ListType lta && b instanceof ListType ltb) {
                if (lta.contentType.equals(ltb.contentType)) return true;
            }
            if (a instanceof ListType lta && b.getName().equals("java.util.List")) {
                return true;
            }
        }
        if (isList(b)) {
            if (a instanceof ListType lta && b instanceof ListType ltb) {
                if (lta.contentType.equals(ltb.contentType)) return true;
            }
            return b instanceof ListType ltb && a.getName().equals("java.util.List");
        }

        return false;
    }

    private static boolean isList(ImpType type) {
        return type.getName().equals("java.util.List") || type instanceof ListType;
    }

}
