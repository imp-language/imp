package org.imp.jvm.legacy.expression.reference;

import org.imp.jvm.legacy.domain.scope.Scope;
import org.imp.jvm.types.ImpType;
import org.objectweb.asm.MethodVisitor;

public class StaticReference extends Reference {
    public final String name;

    public StaticReference(String name, ImpType type) {
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
