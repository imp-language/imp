package org.imp.jvm.domain.types;

import org.imp.jvm.statement.Struct;
import org.objectweb.asm.Opcodes;

public class StructType implements Type {

    public final Struct struct;

    private final String name;

//
//    private static final Map<String, String> shortcuts = HashMap.of(
//            "List", "java.util.ArrayList"
//    );

    public StructType(Struct struct) {
        this.struct = struct;
        this.name = struct.identifier.name;
    }

    public StructType(String structName) {
        this.name = structName;
        this.struct = null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj instanceof StructType) {
            StructType o = (StructType) obj;
            return this.name.equals(o.name);
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getName() {
        if (struct == null) return this.name;
        return "scratch/" + struct.identifier.name;
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
    public int getSubstractOpcode() {
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
}
