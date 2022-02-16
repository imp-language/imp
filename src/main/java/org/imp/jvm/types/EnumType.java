package org.imp.jvm.types;

import org.imp.jvm.domain.Operator;
import org.imp.jvm.expression.Expression;
import org.imp.jvm.types.overloads.OperatorOverload;

import java.util.Map;
import java.util.Optional;

public class EnumType implements ImpType {
    public final String name;
    public final Map<String, Optional<Expression>> elements;

    public EnumType(String name, Map<String, Optional<Expression>> elements) {
        this.name = name;
        this.elements = elements;
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
    public int getNegOpcode() {
        return 0;
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
        return "enum";
    }
}
