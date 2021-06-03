package org.imp.jvm.domain.scope;

import org.imp.jvm.compiler.FieldGenerator;
import org.imp.jvm.domain.types.BuiltInType;
import org.imp.jvm.domain.types.Type;
import org.imp.jvm.expression.Expression;
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
        // Todo: can this be replaced with "(name:type)"?
        return name + " " + type.getName();
//        return type.getDescriptor();
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {

    }

    @Override
    public void validate() {

    }
}