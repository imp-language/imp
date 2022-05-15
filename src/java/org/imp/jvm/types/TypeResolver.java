package org.imp.jvm.types;

import org.apache.commons.lang3.StringUtils;
import org.imp.runtime.ListWrapper;

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
     *
     * @param par the parameter type, e.g. from the function definition
     * @param arg the argument type, e.g. what is passed to the function call
     *            <ol>
     *                <li>If both are unions, check that arg is a subset of par. If only
     *                the parameter is a union, check that the arg is contained in said union.</li>
     *                <li>If either type is "java.lang.Object" (meaning "any") the types match.
     *                 Assume this is a standard library function that accepts Object.</li>
     *                <li>If both types are BuiltInType, they must be equal to match.</li>
     *                <li>List types.</li>
     *            </ol>
     */
    public static boolean typesMatch(ImpType par, ImpType arg) {
        // 0
        if (par instanceof UnionType uPar && arg instanceof UnionType uArg) {
            if (uPar.types.containsAll(uArg.types)) return true;
        }
        if (par instanceof UnionType ua && ua.types.contains(arg)) return true;
        // 1
        if (par.getName().equals("java.lang.Object") || arg.getName().equals("java.lang.Object")) {
            return true;
        }
        // 2
        if (par instanceof BuiltInType bta && arg instanceof BuiltInType btb) {
            if (bta.equals(btb)) return true;
        }
        // 3
        if (isList(par)) {
            if (par instanceof ListType lta && arg instanceof ListType ltb) {
                if (lta.contentType.equals(ltb.contentType)) return true;
            }
            if (par instanceof ListType lta && arg.getName().equals("java.util.List")) {
                return true;
            }
        }
        if (isList(arg)) {
            // Todo: this is shoddy, fix
            if (par instanceof ExternalType et && et.foundClass() == ListWrapper.class) {
                return true;
            }

            if (par instanceof ListType lta && arg instanceof ListType ltb) {
                if (lta.contentType.equals(ltb.contentType)) return true;
            }
            return arg instanceof ListType ltb && par.getName().equals("java.util.List");
        }

        return false;
    }

    private static boolean isList(ImpType type) {
        return type.getName().equals("java.util.List") || type instanceof ListType;
    }

}
