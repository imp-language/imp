package org.imp.jvm.types;

import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionType implements Type {

    public final String name;
    public final List<FunctionSignature> signatures;

    public FunctionType(String name) {
        this.name = name;
        this.signatures = new ArrayList<>();
    }

    public FunctionSignature getSignatureByTypes(String name, List<Type> argTypes) {
        var identifiers = argTypes.stream().map(e -> new Identifier("_", e)).collect(Collectors.toList());
        return getSignature(name, identifiers);
    }

    /**
     * @param name      scoped name of the method
     * @param arguments list of arguments in the function call
     * @return a FunctionSignature if such a function exists in the current scope.
     */
    public FunctionSignature getSignature(String name, List<Identifier> arguments) {
        return signatures.stream()
                .filter(signature -> signature.matches(name, arguments))
                .findFirst()
                .orElse(null);
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
    public int getSubstractOpcode() {
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
}
