package org.imp.jvm.types;

import org.objectweb.asm.Opcodes;

import java.util.Objects;

public class ListType implements ImpType {

    public final ImpType contentType;

    public ListType(ImpType contentType) {
        this.contentType = contentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListType listType = (ListType) o;
        return contentType.equals(listType.contentType);
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
        return "Ljava/util/List;";
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
        return contentType + "[]";
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
        return 0;
    }

    @Override
    public Class<?> getTypeClass() {
        return java.util.List.class;
    }

    @Override
    public int hashCode() {
        return Objects.hash(contentType);
    }

    @Override
    public boolean isNumeric() {
        return false;
    }

    @Override
    public String kind() {
        return "list";
    }

    @Override
    public String toString() {
        return getName();
    }
}
