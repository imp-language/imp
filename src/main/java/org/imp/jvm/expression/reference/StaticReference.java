package org.imp.jvm.expression.reference;

import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.Type;
import org.objectweb.asm.MethodVisitor;

public class StaticReference extends Reference {
    public final String name;

    public StaticReference(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {


    }

    @Override
    public void validate(Scope scope) {

    }

    @Override
    public String getName() {
        return name;
    }
}
