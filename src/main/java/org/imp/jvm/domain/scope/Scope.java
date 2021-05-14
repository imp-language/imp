package org.imp.jvm.domain.scope;

import org.imp.jvm.domain.types.Type;
import org.imp.jvm.exception.LocalVariableNotFoundException;
import org.imp.jvm.exception.MethodSignatureNotFoundException;
import org.apache.commons.collections4.map.LinkedMap;
import org.imp.jvm.exception.StructNotFoundException;
import org.imp.jvm.statement.Struct;

import java.util.*;

/**
 * Describes the entities available to expressions and statements in
 * the current block.
 * <p>
 * New Scopes "inherit" entities from higher scopes. Methods defined
 * outside the current block are accessible inside this block, for example.
 */
public class Scope {
    private final LinkedMap<String, LocalVariable> localVariables;

    private final List<FunctionSignature> functionSignatures;

    private final List<Struct> structs;

    private final String name;

    public Scope(String name) {
        this.name = name;
        localVariables = new LinkedMap<>();
        functionSignatures = new ArrayList<>();
        structs = new ArrayList<>();
    }

    public Scope(Scope scope) {
        name = scope.name;
        functionSignatures = scope.functionSignatures;
        localVariables = scope.localVariables;
        structs = scope.structs;
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
     * @param varName name to search for
     * @return index of local variable in frame
     */
    public int getLocalVariableIndex(String varName) {
        return localVariables.indexOf(varName);
    }

    /**
     * @param varName name to search for
     * @return whether a variable exists in the current scope
     */
    public boolean variableExists(String varName) {
        // ToDo: decide what happens with scope and local variable overriding by inner scopes.
        return true;
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

    /**
     * @param struct Struct to add to the current scope
     */
    public void addStruct(Struct struct) {
        structs.add(struct);
    }

    /**
     * @param name name of the Struct
     * @return a Struct if one named name exists in the current scope
     */
    public Struct getStruct(String name) {
        return structs.stream().filter(struct -> struct.identifier.name.equals(name)).findFirst()
                .orElseThrow(() -> new StructNotFoundException(this, name));
    }
}
