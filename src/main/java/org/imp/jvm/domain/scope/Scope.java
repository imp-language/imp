package org.imp.jvm.domain.scope;

import org.imp.jvm.domain.types.Type;
import org.imp.jvm.exception.LocalVariableNotFoundException;
import org.imp.jvm.exception.MethodSignatureNotFoundException;

import java.util.*;

public class Scope {
    private final Map<String, LocalVariable> localVariables;

    private final List<FunctionSignature> functionSignatures;

    public Scope() {
        localVariables = new HashMap<>();
        functionSignatures = new ArrayList<>();
    }

    /**
     * @param variable LocalVariable to add to the current scope
     */
    public void addLocalVariable(LocalVariable variable) {
        localVariables.put(variable.getName(), variable);
    }


    /**
     * @param varName name to search for
     * @return a LocalVariable if such a variable exists in the current scope
     */
    public LocalVariable getLocalVariable(String varName) {
        return Optional.ofNullable(localVariables.get(varName))
                .orElseThrow(() -> new LocalVariableNotFoundException(this, varName));
    }


    /**
     * @param signature FunctionSignature to add to the current scope
     */
    public void addSignature(FunctionSignature signature) {
        functionSignatures.add(signature);
    }

    /**
     * @param name      scoped name of the method
     * @param arguments list of arguments in the function call
     * @return a FunctionSignature if such a function exists in the current scope.
     */
    public FunctionSignature getSignature(String name, List<Identifier> arguments) {
        return functionSignatures.stream()
                .filter(signature -> signature.matches(name, arguments))
                .findFirst()
                .orElseThrow(() -> new MethodSignatureNotFoundException(this, name, arguments));
    }
}
