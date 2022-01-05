package org.imp.jvm.types;

import org.imp.jvm.Util;
import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.Operator;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.exception.Errors;
import org.imp.jvm.types.overloads.OperatorOverload;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StructType implements Type {
    public final Identifier identifier;
    public final Scope scope;

    // Todo: replace with Map<String,Type>
    public final List<Identifier> fields;
    public final ImpFile parent;


    public StructType(Identifier identifier, List<Identifier> fields) {
        this.identifier = identifier;
        this.fields = fields;
        this.parent = null;
        this.scope = null;
    }

    /**
     * @param fieldName String name
     * @return type of struct field if fieldName exists in this struct
     */
    public static Optional<Identifier> findStructField(StructType st, String fieldName) {
        return st.fields.stream().filter(id -> id.name.equals(fieldName)).findFirst();
    }

    /**
     * Recursively attempt to find the type of the method access expression.
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
            if (found.type instanceof StructType foundStructType && fieldPath.size() > 0) {
                var rescursedPath = findStructField(foundStructType, fieldPath);
                validatedPath.addAll(rescursedPath);
            } else if (fieldPath.size() > 0) {
                Logger.syntaxError(Errors.StructFieldNotFound, fieldPath.get(0), fieldPath.get(0).getCtx().getText());
            }
        } else {
            Logger.syntaxError(Errors.StructFieldNotFound, first, first.getCtx().getText());
        }

        return validatedPath;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof StructType o) {
            return this.identifier.name.equals(o.identifier.name);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "struct " + getName() + " {" + Util.parameterString(fields) + "}";
    }

    @Override
    public String getName() {
        return identifier.name;
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
    public int getSubtractOpcode() {
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

    @Override
    public OperatorOverload getOperatorOverload(Operator operator) {
        return null;
    }
}
