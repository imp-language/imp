package org.imp.jvm.statement;

import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.types.StructType;
import org.objectweb.asm.MethodVisitor;

import java.util.stream.Collectors;

public class Struct extends Statement {

    public final StructType structType;

    public Struct(StructType structType) {
        super();
        this.structType = structType;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        // Structs are generated separately in Compiler.java.
    }

    @Override
    public String toString() {
        var s = "struct ";
        s += structType.name;
        s += " { ";
        s += structType.fields.stream().map(field -> field.name + " " + field.type).collect(Collectors.joining(", "));
        s += " }";
        return s;
    }

    @Override
    public void validate(Scope scope) {
        // Struct validation happens before this is called, so types are
        // accessible by the validation pass on all other statements.
    }


}
