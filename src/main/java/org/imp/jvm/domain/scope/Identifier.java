package org.imp.jvm.domain.scope;

import org.imp.jvm.compiler.FieldGenerator;
import org.imp.jvm.expression.Expression;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.Type;
import org.objectweb.asm.MethodVisitor;


public class Identifier extends Expression {
    public String name;

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
        return name + " " + type.getName();
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {

    }

    @Override
    public void validate(Scope scope) {

    }
}