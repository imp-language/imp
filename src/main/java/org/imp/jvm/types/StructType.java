package org.imp.jvm.types;

import org.imp.jvm.Util;
import org.imp.jvm.legacy.domain.scope.Identifier;
import org.objectweb.asm.Opcodes;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StructType implements ImpType, Serializable {
    // Todo: replace with Map<String,Type>
    public final List<Identifier> fields;
    public final String[] fieldNames;
    public final ImpType[] fieldTypes;
    public String name;

    public String qualifiedName;
    public String parentName;

    public StructType(String name, String[] fieldNames, ImpType[] fieldTypes) {
        this.name = name;
        this.fields = Collections.emptyList();
        this.fieldNames = fieldNames;
        this.fieldTypes = fieldTypes;
    }

    public StructType(String name, List<Identifier> identifiers) {
        this.name = name;
        this.fields = identifiers;
        this.fieldNames = new String[0];
        this.fieldTypes = new ImpType[0];

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


    public Optional<ImpType> findType(String name) {
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
    public int getNegOpcode() {
        return 0;
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

    public class Date {
        public int day;
        public int month;
        public int year;

        public Date() {
        }
    }
}
