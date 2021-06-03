package org.imp.jvm.domain.scope;

import org.imp.jvm.domain.types.Type;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FunctionSignature extends Identifier {
    public final List<Identifier> parameters;

    public FunctionSignature(String name, List<Identifier> parameters, Type returnType) {
        this.name = name;
        this.parameters = parameters;
        this.type = returnType;

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
        return name + " (" + String.join(", ", params) + ") " + type;
    }
}
