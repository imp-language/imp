package org.imp.jvm.domain;

import org.imp.jvm.codegen.FieldGenerator;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.ImpType;

import java.io.Serializable;


public class Identifier implements Serializable {
    public String name;
    public ImpType type;

    public Identifier(String name, ImpType type) {
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
        return name + " " + type.getName();
    }


}