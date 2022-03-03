package org.imp.jvm.runtime.stdlib.collections;

import java.util.ArrayList;

public class ImpList<T> {
    private final ArrayList<T> arrayList;

    public ImpList() {
        arrayList = new ArrayList<>();
    }


    public void add(T item) {
        arrayList.add(item);
    }

    public void remove(T item) {
        arrayList.remove(item);
    }

    public boolean contains(T item) {
        return arrayList.contains(item);
    }

    public T get(int position) {
        return arrayList.get(position);
    }
}
