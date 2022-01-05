package org.imp.jvm.types;

import org.imp.jvm.Util;
import org.imp.jvm.domain.Operator;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.types.overloads.OperatorOverload;

import java.util.List;

public class FuncType implements Type {
    public final String name;
    public final Modifier modifier;
    public final List<Identifier> parameters;
    public Type returnType = BuiltInType.VOID;

    public FuncType(String name, Modifier modifier, List<Identifier> parameters) {
        this.name = name;
        this.modifier = modifier;
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "func " + name + "(" + Util.parameterString(parameters) + ") " + returnType;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Class<?> getTypeClass() {
        return null;
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
