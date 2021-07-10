package org.imp.jvm.domain.scope;

import org.imp.jvm.types.Type;

public class LocalVariable implements Variable {
    public final String name;
    public Type type;
    public boolean closure = false;

    public LocalVariable(String name, Type type) {
        this.type = type;
        this.name = name;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName() + " " + getType();
    }
}