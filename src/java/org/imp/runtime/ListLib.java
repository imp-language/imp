package org.imp.runtime;

import java.util.ArrayList;

public class ListLib {
    public static <T> void push(ArrayList<T> r, T a) {
        r.add(a);
    }

    public static <T> T pop(ArrayList<T> r, T a) {
        return r.remove(r.size() - 1);
    }
}
