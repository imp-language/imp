package org.imp.jvm.types;

import org.imp.jvm.Util;
import org.imp.jvm.domain.Operator;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.types.overloads.OperatorOverload;
import org.objectweb.asm.MethodVisitor;

import java.io.Serializable;
import java.util.List;

public class FuncType implements Type, Serializable {
    public final Modifier modifier;
    public final List<Identifier> parameters;
    //    public final String[] fieldNames;
//    public final Type[] fieldTypes;
    public String name;
    public Type returnType = BuiltInType.VOID;

    public MethodVisitor mv = null; // careful!
    // Todo: refactor this to have a subclass with a generate method using in CodegenVisitor
    public boolean glue = false;

    // Todo: refactor to use the String[] pattern from StructType instead of List<Identifier>
    public FuncType(String name, Modifier modifier, List<Identifier> parameters) {
        this.name = name;
        this.modifier = modifier;
        this.parameters = parameters;
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
        return null;
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
        return 0;
    }

    @Override
    public int getMultiplyOpcode() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public OperatorOverload getOperatorOverload(Operator operator) {
        return null;
    }

    @Override
    public int getReturnOpcode() {
        return 0;
    }

    @Override
    public int getStoreVariableOpcode() {
        return 0;
    }

    @Override
    public int getSubtractOpcode() {
        return 0;
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
        return "function";
    }

    @Override
    public String toString() {
        return "func " + name + "(" + Util.parameterString(parameters) + ") " + returnType;
    }
}
