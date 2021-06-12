package org.imp.jvm.domain.types;

import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.exception.SemanticErrors;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StructType implements Type {


    public final Identifier identifier;
    public final Scope scope;

    public final List<Identifier> fields;
    public final ImpFile parent;

    private boolean unknown = false;


    public StructType(Identifier identifier, List<Identifier> fields, ImpFile parent, Scope scope) {
        this.identifier = identifier;
        this.fields = fields;
        this.parent = parent;
        this.scope = scope;
    }

    public StructType() {
        this.unknown = true;
        this.scope = null;
        this.identifier = null;
        this.fields = null;
        this.parent = null;
    }

    /**
     * @param fieldName String name
     * @return type of struct field if fieldName exists in this struct
     */
    public static Optional<Identifier> findStructField(StructType st, String fieldName) {
        Optional<Identifier> identifier = st.fields.stream().filter(id -> id.name.equals(fieldName)).findFirst();
        return identifier;
    }

    /**
     * Recursively attempt to find the type of a method access expression.
     *
     * @param parent    starting point, already known
     * @param fieldPath list of identifiers
     * @return type of struct field if fieldPath is valid
     */
    public List<Identifier> findStructField(StructType parent, List<Identifier> fieldPath) {
        List<Identifier> validatedPath = new ArrayList<>();
        Identifier first = fieldPath.remove(0);
        var attempt = StructType.findStructField(parent, first.name);
        if (attempt.isPresent()) {
            var found = attempt.get();
            validatedPath.add(found);

            // recurse if the found field is another struct
            if (found.type instanceof StructType && fieldPath.size() > 0) {
                StructType foundStructType = (StructType) found.type;
                var rescursedPath = findStructField(foundStructType, fieldPath);
                validatedPath.addAll(rescursedPath);
            } else if (fieldPath.size() > 0) {
                Logger.syntaxError(SemanticErrors.StructFieldNotFound, fieldPath.get(0).getCtx());
            }
        } else {
            Logger.syntaxError(SemanticErrors.StructFieldNotFound, first.getCtx());
        }

        return validatedPath;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof StructType) {
            StructType o = (StructType) obj;
            return this.identifier.name.equals(o.identifier.name);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getName() {
//        if (struct == null) return this.name;
        return this.parent.name + "/" + identifier.name;
    }

    @Override
    public Class<?> getTypeClass() {
        return null;
    }

    @Override
    public String getDescriptor() {
        return "L" + getInternalName() + ";";
    }

    @Override
    public String getInternalName() {
        return getName().replace(".", "/");
    }

    @Override
    public int getLoadVariableOpcode() {
        return Opcodes.ALOAD;
    }

    @Override
    public int getStoreVariableOpcode() {
        return Opcodes.ASTORE;
    }

    @Override
    public int getReturnOpcode() {
        return Opcodes.ARETURN;
    }

    @Override
    public int getAddOpcode() {
        throw new RuntimeException("Addition operation not (yet ;) ) supported for custom objects");
    }

    @Override
    public int getSubstractOpcode() {
        throw new RuntimeException("Substraction operation not (yet ;) ) supported for custom objects");
    }

    @Override
    public int getMultiplyOpcode() {
        throw new RuntimeException("Multiplcation operation not (yet ;) ) supported for custom objects");
    }

    @Override
    public int getDivideOpcode() {
        throw new RuntimeException("Division operation not (yet ;) ) supported for custom objects");
    }

    @Override
    public Object getDefaultValue() {
        return null;
    }

    @Override
    public boolean isNumeric() {
        return false;
    }
}
