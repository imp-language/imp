package org.imp.jvm.domain.scope;

import org.imp.jvm.compiler.FieldGenerator;
import org.imp.jvm.domain.types.BuiltInType;
import org.imp.jvm.domain.types.Type;


public class Identifier {
    public String name;
    public Type type;

    public Identifier(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public Identifier() {
        this.name = "_";
        this.type = BuiltInType.VOID;
    }

    public void accept(FieldGenerator generator) {
        generator.generate(this);
    }

    @Override
    public String toString() {
        return type.getDescriptor();
    }
}