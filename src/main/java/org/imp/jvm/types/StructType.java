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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StructType implements Type, Serializable {
    public final Scope scope;
    // Todo: replace with Map<String,Type>
    public final List<Identifier> fields;
    public final String[] fieldNames;
    public final Type[] fieldTypes;
    public final ImpFile parent;
    public String name;

    public String qualifiedName;

    public StructType(String name, String[] fieldNames, Type[] fieldTypes) {
        this.name = name;
        this.fields = Collections.emptyList();
        this.fieldNames = fieldNames;
        this.fieldTypes = fieldTypes;
        this.parent = null;
        this.scope = null;
    }

    public StructType(String name, List<Identifier> identifiers) {
        this.name = name;
        this.fields = identifiers;
        this.fieldNames = new String[0];
        this.fieldTypes = new Type[0];
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof StructType o) {
            return this.name.equals(o.name);
        } else {
            return false;
        }
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

    public Optional<Type> findType(String name) {
        for (int i = 0; i < fieldNames.length; i++) {
            if (fieldNames[i].equals(name)) {
                return Optional.of(fieldTypes[i]);
            }
        }
        return Optional.empty();
    }

    @Override
    public int getAddOpcode() {
        throw new RuntimeException("Addition operation not (yet ;) ) supported for custom objects");
    }

    @Override
    public Object getDefaultValue() {
        return null;
    }

    @Override
    public String getDescriptor() {
        String n = qualifiedName.replace(":", "/");
        return "L" + n + ";";
    }

    @Override
    public int getDivideOpcode() {
        throw new RuntimeException("Division operation not (yet ;) ) supported for custom objects");
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
    public int getMultiplyOpcode() {
        throw new RuntimeException("Multiplication operation not (yet ;) ) supported for custom objects");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public OperatorOverload getOperatorOverload(Operator operator) {
        return null;
    }

    @Override
    public int getReturnOpcode() {
        return Opcodes.ARETURN;
    }

    @Override
    public int getStoreVariableOpcode() {
        return Opcodes.ASTORE;
    }

    @Override
    public int getSubtractOpcode() {
        throw new RuntimeException("Subtraction operation not (yet ;) ) supported for custom objects");
    }

    @Override
    public Class<?> getTypeClass() {
        return null;
    }

    @Override
    public boolean isNumeric() {
        return false;
    }

    @Override
    public String kind() {
        return "struct";
    }

    @Override
    public String toString() {
        return "struct " + getName() + " {" + Util.parameterString(fieldNames, fieldTypes) + "}";
    }
}
