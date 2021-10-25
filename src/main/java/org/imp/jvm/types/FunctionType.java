package org.imp.jvm.types;

import org.apache.commons.collections4.map.LinkedMap;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.Operator;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.expression.Function;
import org.imp.jvm.expression.reference.VariableReference;
import org.imp.jvm.types.overloads.OperatorOverload;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionType implements Type {

    public final String name;
    //    public final List<Function> signatures;
    private final LinkedMap<String, Function> signatures;
    public final ImpFile parent;

    public final List<VariableReference> closures = new ArrayList<>();

    public final boolean isStatic;

    public FunctionType(String name, ImpFile parent, boolean isStatic) {
        this.name = name;
        this.isStatic = isStatic;
        this.signatures = new LinkedMap<>();
        this.parent = parent;
    }


    public Function getSignatureByTypes(List<Type> argTypes) {
        if (this.name.equals("log")) {
            if (argTypes.size() == 0) return this.signatures.get("");
            return this.signatures.get("[Ljava/lang/Object;");
        }
        var identifiers = argTypes.stream().map(e -> new Identifier("_", e)).collect(Collectors.toList());
        String descriptor = Function.getDescriptor(identifiers);
        return getSignature(descriptor);
    }

    /**
     * @param descriptor generated by Function.getDescriptor(List<Identifier> identifiers)
     * @return Function if such an overload exists on this function type
     */
    public Function getSignature(String descriptor) {
        return signatures.get(descriptor);
    }

    public void addSignature(String descriptor, Function signature) {
        signatures.put(descriptor, signature);
    }

    public Function getSignature(int pos) {
        return signatures.getValue(pos);
    }

    public LinkedMap<String, Function> getSignatures() {
        return signatures;
    }

    @Override
    public String toString() {

        String s = name + ", " + signatures.size() + " overloads";

        if (isStatic) {
            s += ", static";
        }
        return s;
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
