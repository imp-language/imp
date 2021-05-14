package org.imp.jvm.statement;

import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.util.List;
import java.util.stream.Collectors;

public class Struct extends Statement {
    public final Identifier identifier;
    public final List<Identifier> fields;

    public Struct(Identifier identifier, List<Identifier> fields) {
        super();
        this.identifier = identifier;
        this.fields = fields;
    }


    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        // Generate a new class for each Struct statement
//        throw new NotImplementedException("ree");
    }


    public void generate(ClassWriter cw) {
//        throw new NotImplementedException("ree");
    }

    @Override
    public String toString() {
        var s = "struct ";
        s += identifier.name;
        s += " { ";
        s += fields.stream().map(field -> field.name + " " + field.type).collect(Collectors.joining(", "));
        s += " }";
        return s;
    }
}
