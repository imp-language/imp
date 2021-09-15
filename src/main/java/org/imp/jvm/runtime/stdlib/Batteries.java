package org.imp.jvm.runtime.stdlib;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * The _ prefix is removed in Imp and only exists to allow
 * this Java class to compile.
 */
public class Batteries {

    // Todo: if only one element is passed to log(), call a more performant version
    public static void log(Object... args) {
        String result = Arrays.stream(args).map(Object::toString).collect(Collectors.joining(" "));
        System.out.println(result);
    }

    public static int _int(float f) {
        return (int) f;
    }

    public static int _int(boolean b) {
        if (b) {
            return 1;
        } else {
            return 0;
        }
    }

    public static int _int(String s) {
        int i = Integer.parseInt(s);
        return i;
    }

    public static float _float(int i) {
        return (float) i;
    }

    public static float _float(String s) {
        return Float.parseFloat(s);
    }

    public static String string(int i) {
        return i + "";
    }

    public static String string(float f) {
        return f + "";
    }

    public static String string(boolean b) {
        return b + "";
    }

    // Todo: not working yet
    public static String typeof(Object o) {
        return o.getClass().getName();
    }
}
