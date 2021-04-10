package org.imp.jvm.domain.scope;

import org.imp.jvm.codegen.FieldGenerator;
import org.imp.jvm.domain.types.Type;

public class Identifier {
    public String name;
    public Type type;

    public void accept(FieldGenerator generator) {
        generator.generate(this);
    }
}