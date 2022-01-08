package org.imp.jvm.types;

import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.Operator;
import org.imp.jvm.types.overloads.OperatorOverload;

import java.util.List;
import java.util.stream.Collectors;

public class TupleType implements Type {

    public final String name;
    public final List<Type> types;
    public final ImpFile parent;


    public TupleType(String name, List<Type> types, ImpFile parent) {
        this.name = name;
        this.types = types;
        this.parent = parent;
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
        return getName().replace(".", "/");
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
        return this.parent.name + "/Tuple_" + name;
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
        return "tuple";
    }

    @Override
    public String toString() {
        String repr = types.stream().map(Object::toString)
                .collect(Collectors.joining(", "));

        return "(" + repr + ")";
    }
}
