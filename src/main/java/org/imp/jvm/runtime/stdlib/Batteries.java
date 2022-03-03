package org.imp.jvm.runtime.stdlib;

/**
 * The _ prefix is removed in Imp and only exists to allow
 * this Java class to compile.
 */
@SuppressWarnings("unused")
public class Batteries {


    public static void log(Object arg) {
        System.out.println(arg);
    }


    public static int length(String s) {
        return s.length();
    }
}
