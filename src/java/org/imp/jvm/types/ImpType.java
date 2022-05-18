package org.imp.jvm.types;

public interface ImpType {


    Object getDefaultValue();

    String getDescriptor();


    String getInternalName();

    int getLoadVariableOpcode();

    int getMultiplyOpcode();

    String getName();


    int getReturnOpcode();


    Class<?> getTypeClass();

    boolean isNumeric();

    /**
     * The kind we serialize to in SQL.
     *
     * @return "function"|"struct"|"enum"|"variable"|"alias"
     */
    String kind();
}
