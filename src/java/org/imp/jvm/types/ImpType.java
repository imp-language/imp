package org.imp.jvm.types;

public interface ImpType {


    int getAddOpcode();

    Object getDefaultValue();

    String getDescriptor();

    int getDivideOpcode();

    String getInternalName();

    int getLoadVariableOpcode();

    int getMultiplyOpcode();

    String getName();

    int getNegOpcode();


    int getReturnOpcode();

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
