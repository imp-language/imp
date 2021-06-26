package org.imp.jvm.domain.scope;

import org.imp.jvm.types.FunctionType;
import org.imp.jvm.types.Type;

import java.util.List;
import java.util.stream.Collectors;

public class FunctionSignature extends Identifier {
    public final List<Identifier> parameters;

    public final FunctionType functionType;

    public FunctionSignature(FunctionType functionType, List<Identifier> parameters, Type returnType) {
        this.name = functionType.name;
        this.functionType = functionType;
        this.parameters = parameters;
        this.type = returnType;

    }

    public FunctionSignature(List<Identifier> parameters, Type returnType) {
        this.name = "<init>";
        this.parameters = parameters;
        this.type = returnType;
        this.functionType = null;

    }

    public boolean matches(String otherSignatureName, List<Identifier> otherSignatureParameters) {
        boolean namesAreEqual = this.name.equals(otherSignatureName);
        if (!namesAreEqual) return false;

        return doParametersMatch(parameters, otherSignatureParameters);
    }

    private boolean doParametersMatch(List<Identifier> a, List<Identifier> b) {
        if (a.size() != b.size()) return false;
        for (int i = 0; i < a.size(); i++) {
            Identifier aIdent = a.get(i);
            Identifier bIdent = b.get(i);
            if (!aIdent.type.equals(bIdent.type)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        // ToDo: this throws an exception, probably something with null parameters or return type
        String params = parameters.stream().map(Object::toString)
                .collect(Collectors.joining(", "));
        return "(" + String.join(", ", params) + ") " + type;
    }

    public String toStringRepr() {
        // ToDo: this throws an exception, probably something with null parameters or return type
        String params = parameters.stream().map(parameters -> parameters.type.toString())
                .collect(Collectors.joining(", "));
        return "(" + String.join(", ", params) + ") " + type;
    }
}
