package org.imp.jvm.types;

import org.imp.jvm.domain.Operator;
import org.imp.jvm.types.overloads.ListOverloads;
import org.imp.jvm.types.overloads.OperatorOverload;
import org.imp.jvm.types.overloads.StringOverloads;
import org.objectweb.asm.Opcodes;

public class ListType implements Type {

    public final Type contentType;

    public ListType(Type contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getName() {
        return "List<" + contentType + ">";
    }

    @Override
    public Class<?> getTypeClass() {
        return java.util.List.class;
    }

    @Override
    public String getDescriptor() {
        return null;
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
        return new ListOverloads();
    }
}
