package org.imp.jvm.statement;

import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.types.EnumType;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class Enum extends Statement {

    public final EnumType enumType;

    public Enum(EnumType enumType) {
        super();
        this.enumType = enumType;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        // Structs are generated separately in Compiler.java.
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
        var s = "enum ";
        s += enumType.name;
        s += " { ";
//        s += enumType.elements.stream().map(field -> field.name + " " + field.type).collect(Collectors.joining(", "));
        s += " }";
        return s;
    }


}
