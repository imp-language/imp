package org.imp.jvm.types;

import org.apache.commons.lang3.NotImplementedException;
import org.imp.jvm.domain.Identifier;
import org.objectweb.asm.Opcodes;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class StructType implements ImpType, Serializable {
    public final List<Identifier> parameters;
    public String name;

    public String qualifiedName;
    public String parentName;

    public StructType(String name, List<Identifier> identifiers) {
        this.name = name;
        this.parameters = identifiers;

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
    public Object getDefaultValue() {
        return null;
    }

    @Override
    public String getDescriptor() {
        String n = qualifiedName.replace(":", "/");
        return "L" + n + ";";
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
    public int getReturnOpcode() {
        return Opcodes.ARETURN;
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
        // Todo: this is getting dangerously close to endless recursion if you got a struct that holds a union referencing said struct
        // Need to find an end condition.
        return "struct " + getName() + " {" + parameters.stream().map(Object::toString).collect(Collectors.joining(", ")) + "}";
    }

}
