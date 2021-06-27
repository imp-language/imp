package org.imp.jvm.types;

import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.statement.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionType implements Type {

    public final String name;
    public final List<Function> signatures;
    public final ImpFile parent;
    public int callSites = 1;

    public FunctionType(String name, ImpFile parent) {
        this.name = name;
        this.signatures = new ArrayList<>();
        this.parent = parent;
    }

    public Function getSignatureByTypes(String name, List<Type> argTypes) {
        var identifiers = argTypes.stream().map(e -> new Identifier("_", e)).collect(Collectors.toList());
        return getSignature(name, identifiers);
    }

    /**
     * @param name      scoped name of the method
     * @param arguments list of arguments in the function call
     * @return a Function if such a function exists in the current scope.
     */
    public Function getSignature(String name, List<Identifier> arguments) {
        return signatures.stream()
                .filter(signature -> signature.matches(name, arguments))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        String repr = signatures.stream().map(signature -> name + signature.toStringRepr())
                .collect(Collectors.joining(", "));

        return repr;
    }

    @Override
    public String getName() {
        return this.parent.name + "/Function_" + name;
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
        return getName().replace(".", "/");
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
