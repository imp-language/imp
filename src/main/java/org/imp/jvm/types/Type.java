package org.imp.jvm.types;

import org.imp.jvm.domain.Operator;
import org.imp.jvm.types.overloads.OperatorOverload;

public interface Type {
    String getName();

    Class<?> getTypeClass();

    String getDescriptor();

    String getInternalName();

    int getLoadVariableOpcode();

    int getStoreVariableOpcode();

    int getReturnOpcode();

    int getAddOpcode();

    int getSubtractOpcode();

    int getMultiplyOpcode();

    int getDivideOpcode();

    Object getDefaultValue();

    boolean isNumeric();

    OperatorOverload getOperatorOverload(Operator operator);
}
