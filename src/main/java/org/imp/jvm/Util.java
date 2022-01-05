package org.imp.jvm;

import org.imp.jvm.domain.scope.Identifier;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Util {
    public static <A, B, O> void zipMap(List<A> a, List<B> b, BiFunction<A, B, O> lambda) throws ArrayIndexOutOfBoundsException {
        if (a.size() == b.size()) {
            var c = Stream.of(a, b).map(Objects::toString).collect(Collectors.joining(", "));
            var i1 = a.iterator();
            var i2 = b.iterator();
            while (i1.hasNext() && i2.hasNext()) {
                var o = lambda.apply(i1.next(), i2.next());
                System.out.println(o);
            }
        } else {
            throw new ArrayIndexOutOfBoundsException("Can't zip two Lists with differing number of elements.");
        }
    }

    public static <A, B, O> void zip(List<A> a, List<B> b, BiConsumer<A, B> lambda) throws ArrayIndexOutOfBoundsException {
        if (a.size() == b.size()) {
            var c = Stream.of(a, b).map(Objects::toString).collect(Collectors.joining(", "));
            var i1 = a.iterator();
            var i2 = b.iterator();
            while (i1.hasNext() && i2.hasNext()) {
                lambda.accept(i1.next(), i2.next());
            }
        } else {
            throw new ArrayIndexOutOfBoundsException("Can't zip two Lists with differing number of elements.");
        }
    }

    /**
     * Is Object o an instance of one of the following classes?
     */
    public static boolean instanceOfOne(Object o, Class<?>... clazz) {
        for (Class<?> c : clazz) {
            if (c.isInstance(o)) {
                return true;
            }
        }
        return false;
    }

    public static String parameterString(List<Identifier> parameters) {
        return parameters.stream().map(p -> p.name + " " + p.type).collect(Collectors.joining(", "));
    }
}
