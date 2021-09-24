package org.imp.jvm.runtime.stdlib.collections;

import java.util.ArrayList;

public class List<T> {
    private ArrayList<T> arrayList;

    public List() {
        arrayList = new ArrayList<T>();
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
