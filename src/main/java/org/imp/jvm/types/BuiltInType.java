package org.imp.jvm.types;

import org.imp.jvm.domain.Operator;
import org.imp.jvm.types.overloads.OperatorOverload;
import org.imp.jvm.types.overloads.StringOverloads;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public enum BuiltInType implements Type {
    BOOLEAN("bool", boolean.class, "Z", TypeSpecificOpcodes.INT, false, false),
    INT("int", int.class, "I", TypeSpecificOpcodes.INT, 0, true),
    FLOAT("float", float.class, "F", TypeSpecificOpcodes.FLOAT, 0.0f, true),
    DOUBLE("double", double.class, "D", TypeSpecificOpcodes.DOUBLE, 0.0d, true),
    STRING("string", String.class, "Ljava/lang/String;", TypeSpecificOpcodes.OBJECT, "", false),
    VOID("void", void.class, "V", TypeSpecificOpcodes.VOID, null, false),

    BOOLEAN_ARR("bool[]", boolean[].class, "[B", TypeSpecificOpcodes.OBJECT, false, false),
    INT_ARR("int[]", int[].class, "[I", TypeSpecificOpcodes.OBJECT, false, false),
    FLOAT_ARR("float[]", float[].class, "[F", TypeSpecificOpcodes.OBJECT, false, false),
    DOUBLE_ARR("double[]", double[].class, "[D", TypeSpecificOpcodes.OBJECT, false, false),
    STRING_ARR("string[]", String[].class, "[Ljava/lang/String;", TypeSpecificOpcodes.OBJECT, false, false),

    BOX("box", null, "Lorg/imp/jvm/runtime/Box;", TypeSpecificOpcodes.OBJECT, false, false),
    STRUCT("struct", null, "Ljava/lang/Object;", TypeSpecificOpcodes.OBJECT, false, false),
    ENUM("enum", null, "Ljava/lang/Object;", TypeSpecificOpcodes.OBJECT, false, false),

    OBJECT("object", Object.class, "Ljava/lang/Object;", TypeSpecificOpcodes.OBJECT, false, false),
    OBJECT_ARR("object[]", Object[].class, "[Ljava/lang/Object;", TypeSpecificOpcodes.OBJECT, false, false),

    MODULE("module", Object.class, "Ljava/lang/Object;", TypeSpecificOpcodes.OBJECT, false, false)

    //
    ;

    private final String name;
    private final Class<?> typeClass;
    private final String descriptor;
    private final TypeSpecificOpcodes opcodes;
    private final Object defaultValue;
    private final boolean isNumeric;

    BuiltInType(String name, Class<?> typeClass, String descriptor, TypeSpecificOpcodes opcodes, Object defaultValue, boolean isNumeric) {
        this.name = name;
        this.typeClass = typeClass;
        this.descriptor = descriptor;
        this.opcodes = opcodes;
        this.defaultValue = defaultValue;
        this.isNumeric = isNumeric;
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
    public int getDivideOpcode() {
        return opcodes.getDivide();
    }

    @Override
    public Object getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public boolean isNumeric() {
        return isNumeric;
    }

    @Override
    public OperatorOverload getOperatorOverload(Operator operator) {
        OperatorOverload overload = null;
        if (this == STRING) {
            if (operator == Operator.INDEX) {
                return new StringOverloads();
            }
        }


        return null;
    }


    public void doBoxing(MethodVisitor mv) {
        switch (this) {
            case INT:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
                break;
            case FLOAT:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;", false);
                break;
            case DOUBLE:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
                break;
            case BOOLEAN:
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false);
                break;
            case STRING:
                // No boxing required, String is already an object.
                break;
            default:
                System.err.println("Boxing isn't supported for that type.");
//                System.exit(28);
                break;
        }
    }

    public void doUnboxing(MethodVisitor mv) {
        // No unboxing required, String is already an object.
        //                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "valueOf", "()Z", false);
        switch (this) {
            case INT -> {
                mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Integer");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
            }
            case FLOAT -> {
                mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Integer");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Integer", "floatValue", "()F", false);
            }
            case DOUBLE -> {
                mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Double");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D", false);
            }
            case BOOLEAN -> {
                mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/Boolean");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z", false);
            }
            case STRING -> mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/String");
            default -> {
                System.err.println("Unboxing isn't supported for that type.");
                System.exit(29);
            }
        }
    }
}
