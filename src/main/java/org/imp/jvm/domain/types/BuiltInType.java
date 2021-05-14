package org.imp.jvm.domain.types;

public enum BuiltInType implements Type {
    BOOLEAN("bool", boolean.class, "Z", TypeSpecificOpcodes.INT, false),
    INT("int", int.class, "I", TypeSpecificOpcodes.INT, 0),
    FLOAT("float", float.class, "F", TypeSpecificOpcodes.FLOAT, 0.0f),
    DOUBLE("double", double.class, "D", TypeSpecificOpcodes.DOUBLE, 0.0d),
    STRING("string", String.class, "Ljava/lang/String;", TypeSpecificOpcodes.OBJECT, ""),
    VOID("void", void.class, "V", TypeSpecificOpcodes.VOID, null),

    BOOLEAN_ARR("bool[]", boolean[].class, "[B", TypeSpecificOpcodes.OBJECT),
    INT_ARR("int[]", int[].class, "[I", TypeSpecificOpcodes.OBJECT),
    FLOAT_ARR("float[]", float[].class, "[F", TypeSpecificOpcodes.OBJECT),
    DOUBLE_ARR("double[]", double[].class, "[D", TypeSpecificOpcodes.OBJECT),
    STRING_ARR("string[]", String[].class, "[Ljava/lang/String;", TypeSpecificOpcodes.OBJECT),

    STRUCT("struct", null, "", TypeSpecificOpcodes.OBJECT);;

    private final String name;
    private final Class<?> typeClass;
    private final String descriptor;
    private final TypeSpecificOpcodes opcodes;
    private final Object defaultValue;

    BuiltInType(String name, Class<?> typeClass, String descriptor, TypeSpecificOpcodes opcodes, Object defaultValue) {
        this.name = name;
        this.typeClass = typeClass;
        this.descriptor = descriptor;
        this.opcodes = opcodes;
        this.defaultValue = defaultValue;
    }

    BuiltInType(String name, Class<?> typeClass, String descriptor, TypeSpecificOpcodes opcodes) {
        this.name = name;
        this.typeClass = typeClass;
        this.descriptor = descriptor;
        this.opcodes = opcodes;
        this.defaultValue = null;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Class<?> getTypeClass() {
        return typeClass;
    }

    @Override
    public String getDescriptor() {
        return descriptor;
    }

    @Override
    public String getInternalName() {
        return getDescriptor();
    }

    @Override
    public int getLoadVariableOpcode() {
        return opcodes.getLoad();
    }

    @Override
    public int getStoreVariableOpcode() {
        return opcodes.getStore();
    }

    @Override
    public int getReturnOpcode() {
        return opcodes.getReturn();
    }

    @Override
    public int getAddOpcode() {
        return opcodes.getAdd();
    }

    @Override
    public int getSubstractOpcode() {
        return opcodes.getSubstract();
    }

    @Override
    public int getMultiplyOpcode() {
        return opcodes.getMultiply();
    }

    @Override
    public int getDividOpcode() {
        return opcodes.getDivide();
    }

    @Override
    public Object getDefaultValue() {
        return this.name;
    }
}
