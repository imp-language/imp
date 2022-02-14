package org.imp.jvm.types;

import org.imp.jvm.domain.Operator;
import org.imp.jvm.types.overloads.OperatorOverload;

public interface Type {
    int getAddOpcode();

    Object getDefaultValue();

    String getDescriptor();

    int getDivideOpcode();

    String getInternalName();

    int getLoadVariableOpcode();

    int getMultiplyOpcode();

    String getName();

    int getNegOpcode();

    OperatorOverload getOperatorOverload(Operator operator);

    int getReturnOpcode();

    int getStoreVariableOpcode();

    int getSubtractOpcode();

    Class<?> getTypeClass();

    boolean isNumeric();

    /**
     * The kind we serialize to in SQL.
     *
     * @return "function"|"struct"|"enum"|"variable"|"alias"
     */
    String kind();
}
