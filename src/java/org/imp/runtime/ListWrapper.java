package org.imp.runtime;

import java.util.ArrayList;

/**
 * ListWrapper exists to avoid the type erasure issue.
 * Todo: expand `name` parameter to support struct types.
 */
public record ListWrapper(ArrayList<?> list, String name) {

    public ListWrapper(String name) {
        this(new ArrayList<>(), name);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
