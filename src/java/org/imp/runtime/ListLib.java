package org.imp.runtime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ListLib {
    public static <T> void push(ArrayList<T> r, T a) {
        r.add(a);
    }

    public static <T> T pop(ArrayList<T> r) {
        return r.remove(r.size() - 1);
    }

    public static <T> List<Integer> range(int start, int stop) {
        // Todo: this is not performant (generates the entire array ahead of time)
        // When possible, a stream/iterator should be used instead.
        return IntStream.range(start, stop).boxed().collect(Collectors.toList());
    }
}
