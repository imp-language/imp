package org.imp.jvm.types;

import org.apache.commons.lang3.NotImplementedException;
import org.imp.jvm.Util;
import org.imp.jvm.domain.Identifier;
import org.objectweb.asm.Opcodes;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof StructType o) {
            return this.name.equals(o.name);
        } else {
            return false;
        }
    }


    @Override
    public int getAddOpcode() {
        throw new NotImplementedException("Opcode not implemented");
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
        throw new NotImplementedException("Opcode not implemented");
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
        throw new NotImplementedException("Opcode not implemented");
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
    public int getSubtractOpcode() {
        throw new NotImplementedException("Opcode not implemented");
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
