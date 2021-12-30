package org.imp.jvm.types;

import org.imp.jvm.domain.Operator;
import org.imp.jvm.types.overloads.OperatorOverload;
import org.objectweb.asm.Opcodes;

public class ExternalType implements Type {

    public final Class<?> foundClass;

    public ExternalType(Class<?> foundClass) {
        this.foundClass = foundClass;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getName() {
        return foundClass.getName();
    }

    @Override
    public Class<?> getTypeClass() {
        return foundClass;
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
    public int getStoreVariableOpcode() {
        return Opcodes.ASTORE;
    }

    @Override
    public int getReturnOpcode() {
        return Opcodes.ARETURN;
    }

    @Override
    public int getAddOpcode() {
        return 0;
    }

    @Override
    public int getSubtractOpcode() {
        return 0;
    }

    @Override
    public int getMultiplyOpcode() {
        return 0;
    }

    @Override
    public int getDivideOpcode() {
        return 0;
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
