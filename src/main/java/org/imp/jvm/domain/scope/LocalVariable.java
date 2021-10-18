package org.imp.jvm.domain.scope;

import org.imp.jvm.types.Type;

public class LocalVariable {
    public final String name;
    public Type type;
    public boolean closure = false;

    public LocalVariable(String name, Type type) {
        this.type = type;
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return getName() + " " + getType();
    }
}