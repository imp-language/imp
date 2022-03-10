package org.imp.jvm.runtime;


/**
 * Used for storing primitive types through closures
 *
 * @param <T>
 */
@SuppressWarnings("ALL")
public class Box<T> {
    public T t;

    public Box(T t) {
        this.t = t;
    }
}
