package org.imp.jvm.runtime.stdlib;

/**
 * The _ prefix is removed in Imp and only exists to allow
 * this Java class to compile.
 */
public class Batteries {
    public static void log(Object arg) {
        System.out.println(arg);
    }

    public static int _int(float f) {
        return (int) f;
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
