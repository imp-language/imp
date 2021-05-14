package org.imp.jvm.domain.types;

import org.imp.jvm.statement.Struct;
import org.objectweb.asm.Opcodes;

public class StructType implements Type {

    private final Struct struct;
//
//    private static final Map<String, String> shortcuts = HashMap.of(
//            "List", "java.util.ArrayList"
//    );

    public StructType(Struct struct) {
        this.struct = struct;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getName() {
        return struct.identifier.name;
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
        return 0;
    }

    @Override
    public int getStoreVariableOpcode() {
        return 0;
    }

    @Override
    public int getReturnOpcode() {
        return 0;
    }

    @Override
    public int getAddOpcode() {
        return 0;
    }

    @Override
    public int getSubstractOpcode() {
        return 0;
    }

    @Override
    public int getMultiplyOpcode() {
        return 0;
    }

    @Override
    public int getDividOpcode() {
        return 0;
    }

    @Override
    public Object getDefaultValue() {
        return null;
    }
}
