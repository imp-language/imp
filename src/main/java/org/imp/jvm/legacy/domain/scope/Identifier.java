package org.imp.jvm.legacy.domain.scope;

import org.imp.jvm.codegen.FieldGenerator;
import org.imp.jvm.legacy.expression.Expression;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.ImpType;

import java.io.Serializable;


public class Identifier extends Expression implements Serializable {
    public String name;

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