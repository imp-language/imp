package org.imp.jvm.expression.reference;

import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.types.BuiltInType;
import org.objectweb.asm.MethodVisitor;

public class ModuleReference extends Reference {
    public final String name;

    public ModuleReference(String name) {
        this.name = name;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {


    }

    @Override
    public void validate(Scope scope) {
        this.type = BuiltInType.MODULE;
    }

    @Override
    public String getName() {
        return name;
    }
}
