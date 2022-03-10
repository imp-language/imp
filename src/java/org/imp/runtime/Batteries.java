package org.imp.runtime;

/**
 * The _ prefix is removed in Imp and only exists to allow
 * this Java class to compile.
 */
public class Batteries {


    public static void log(Object arg) {
        System.out.println(arg);
    }

    public static float _float(Object o) {
        if (o == null) return 0;
        if (o instanceof Integer i) return i.floatValue();
        if (o instanceof Float f) return f;
        if (o instanceof Double d) return d.floatValue();
        if (o instanceof Long l) return l.floatValue();
        if (o instanceof String s) return Float.parseFloat(s);

        return 0;
    }


    public static int length(String s) {
        return s.length();
    }
}
