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
    public final Identifier identifier;
    public final List<Identifier> fields;
    public final Scope scope;
    public final ImpFile parent;

    public Struct(Identifier identifier, List<Identifier> fields, Scope scope, ImpFile parent) {
        super();
        this.identifier = identifier;
        this.fields = fields;
        this.scope = scope;
        this.parent = parent;
    }


    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        // Generate a new class for each Struct statement
//        throw new NotImplementedException("ree");
    }

    @Override
    public void validate() {
        // Struct validation happens before this is called, so types are
        // accessible by the validation pass on all other statements.
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

    /**
     * @param fieldName String name
     * @return type of struct field if fieldName exists in this struct
     */
    public static Optional<Identifier> findStructField(Struct struct, String fieldName) {
        Optional<Identifier> identifier = struct.fields.stream().filter(id -> id.name.equals(fieldName)).findFirst();
        return identifier;
    }


    /**
     * Recursively attempt to find the type of a method access expression.
     *
     * @param parent    starting point, already known
     * @param fieldPath list of identifiers
     * @return type of struct field if fieldPath is valid
     */
    public List<Identifier> findStructField(Struct parent, List<Identifier> fieldPath) {
        List<Identifier> validatedPath = new ArrayList<>();
        Identifier first = fieldPath.remove(0);
        var attempt = Struct.findStructField(parent, first.name);
        if (attempt.isPresent()) {
            var found = attempt.get();
            validatedPath.add(found);

            // recurse if the found field is another struct
            if (found.type instanceof StructType && fieldPath.size() > 0) {
                StructType foundStructType = (StructType) found.type;
                var rescursedPath = findStructField(foundStructType.struct, fieldPath);
                validatedPath.addAll(rescursedPath);
            } else if (fieldPath.size() > 0) {
                Logger.syntaxError(SemanticErrors.StructFieldNotFound, fieldPath.get(0).getLine());
            }
        } else {
            Logger.syntaxError(SemanticErrors.StructFieldNotFound, first.getLine());
        }

        return validatedPath;
    }
}
