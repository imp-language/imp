package org.imp.jvm.types;

import org.objectweb.asm.Opcodes;

import java.util.Set;
import java.util.stream.Collectors;

public class UnionType implements ImpType {
    public Set<ImpType> types;

    public UnionType(Set<ImpType> types) {
        this.types = types;

    }

    @Override
    public int getAddOpcode() {
        return 0;
    }

    @Override
    public Object getDefaultValue() {
        return null;
    }

    @Override
    public String getDescriptor() {
        return "Ljava/lang/Object;";
    }

    @Override
    public int getDivideOpcode() {
        return 0;
    }

    @Override
    public String getInternalName() {
        return null;
    }

    @Override
    public int getLoadVariableOpcode() {
        return Opcodes.ALOAD;
    }

    @Override
    public int getMultiplyOpcode() {
        return 0;
    }

    @Override
    public String getName() {
        return "name";
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
        return 0;
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
        return null;
    }


    @Override
    public String toString() {
        return types.stream().map(Object::toString).collect(Collectors.joining(" | "));
    }
}
