package org.imp.runtime;

import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

/**
 * The _ prefix is removed in Imp and only exists to allow
 * this Java class to compile.
 */
@SuppressWarnings("unused")
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


    public static <T> void push(List<T> r, T a) {
        r.add(a);
    }

    // Todo: only allow items of same type to be pushed to list
    public static <T> T pop(List<T> r) {
        return r.remove(r.size() - 1);
    }


    public static <T> Iterator<Integer> range(int start, int stop) {
        return IntStream.range(start, stop).boxed().iterator();
    }


    public static <T> T at(ListWrapper r, int pos) {
        return (T) r.list().get(pos);
    }

    public static int len(List r) {
        return r.size();
    }
}
