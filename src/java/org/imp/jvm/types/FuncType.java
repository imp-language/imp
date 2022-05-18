package org.imp.jvm.types;

import org.imp.jvm.Util;
import org.imp.jvm.domain.Identifier;
import org.objectweb.asm.commons.GeneratorAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FuncType extends StructType {
    public final List<Identifier> parameters;
    public final Map<String, Integer> localMap = new HashMap<>();
    public final Map<String, Integer> argMap = new HashMap<>();
    public String name;
    public ImpType returnType = BuiltInType.VOID;
    public GeneratorAdapter ga = null;
    public boolean glue = false;
    public boolean hasReturn2 = false;
    public boolean isPrefixed = false;
    public String owner;

    public FuncType(String name, List<Identifier> parameters) {
        super(name, parameters);
        this.name = name;
        this.parameters = parameters;

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
    public int getReturnOpcode() {
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
