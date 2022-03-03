package org.imp.jvm.types;

import org.imp.jvm.legacy.domain.Operator;
import org.imp.jvm.types.overloads.OperatorOverload;
import org.objectweb.asm.Opcodes;

import java.io.Serializable;

public class UnknownType implements ImpType, Serializable {
    static int counter = 1;
    final int id;
    public String typeName;

    public UnknownType(String typeName) {
        this.typeName = typeName;
        id = counter;
        counter++;
    }

    public UnknownType() {
        this.typeName = "";
        id = counter;
        counter++;
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
        return "L" + getInternalName() + ";";
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
        return typeName.length() > 0 ? typeName : "<unknown>";
    }

    @Override
    public int getNegOpcode() {
        return 0;
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
        return null;
    }

    @Override
    public String toString() {
        return "$" + getName();
    }
}
