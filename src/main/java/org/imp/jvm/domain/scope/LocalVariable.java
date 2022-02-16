package org.imp.jvm.domain.scope;

import org.imp.jvm.types.Mutability;
import org.imp.jvm.types.ImpType;

public class LocalVariable {
    public final String name;
    public ImpType type;
    public boolean closure = false;
    public final Mutability mutability;

    public LocalVariable(String name, ImpType type) {
        this.type = type;
        this.name = name;
        this.mutability = Mutability.Val;
    }

    public LocalVariable(String name, ImpType type, Mutability mutability) {
        this.type = type;
        this.name = name;
        this.mutability = mutability;
    }

    public ImpType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return getName() + " " + getType();
    }
}