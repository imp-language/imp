package org.imp.jvm.types;

import org.objectweb.asm.Opcodes;

public record ExternalType(Class<?> foundClass) implements ImpType {


    @Override
    public Object getDefaultValue() {
        return null;
    }

    @Override
    public String getDescriptor() {
        return foundClass.descriptorString();
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
        return 0;
    }

    @Override
    public String getName() {
        return foundClass.getName();
    }


    @Override
    public int getReturnOpcode() {
        return Opcodes.ARETURN;
    }


    @Override
    public Class<?> getTypeClass() {
        return foundClass;
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
        return getName();
    }
}
