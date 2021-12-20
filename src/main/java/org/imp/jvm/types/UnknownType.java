package org.imp.jvm.types;

import org.imp.jvm.domain.Operator;
import org.imp.jvm.types.overloads.OperatorOverload;
import org.objectweb.asm.Opcodes;

public class UnknownType implements Type {
    public String typeName = null;

    final int id;

    static int counter = 1;

    public UnknownType(String typeName) {
        this.typeName = typeName;
        id = counter;
        counter++;
    }

    public UnknownType() {
        id = counter;
        counter++;
    }


    @Override
    public String toString() {
        return "$" + id;
    }

    @Override
    public String getName() {
        return "<unknown>";
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
