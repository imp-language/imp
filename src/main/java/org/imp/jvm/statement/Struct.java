package org.imp.jvm.statement;

import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.types.StructType;
import org.imp.jvm.exception.SemanticErrors;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Struct extends Statement {
    // Todo: struct content should be moved to StructType.
//    public final Identifier identifier;
//    public final List<Identifier> fields;
//    public final Scope scope;
//    public final ImpFile parent;

    public StructType structType;

    public Struct(StructType structType) {
        super();
//        this.identifier = identifier;
//        this.fields = fields;
//        this.scope = scope;
        this.structType = structType;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        // Generate a new class for each Struct statement
//        throw new NotImplementedException("ree");
    }

    @Override
    public void validate(Scope scope) {
        // Struct validation happens before this is called, so types are
        // accessible by the validation pass on all other statements.
    }


    public void generate(ClassWriter cw) {
//        throw new NotImplementedException("ree");
    }

    @Override
    public String toString() {
        var s = "struct ";
        s += structType.identifier.name;
        s += " { ";
        s += structType.fields.stream().map(field -> field.name + " " + field.type).collect(Collectors.joining(", "));
        s += " }";
        return s;
    }





}
