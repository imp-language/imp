package org.imp.jvm.runtime.stdlib;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * The _ prefix is removed in Imp and only exists to allow
 * this Java class to compile.
 */
public class Batteries {
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
        try {

            int i = Integer.parseInt(s);
            return i;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static float _float(int i) {
        return (float) i;
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
}
