package org.imp.jvm.domain.scope;

import org.imp.jvm.statement.Function;
import org.imp.jvm.types.FunctionType;
import org.imp.jvm.types.StructType;
import org.imp.jvm.types.Type;
import org.apache.commons.collections4.map.LinkedMap;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Describes the entities available to expressions and statements in
 * the current block.
 * <p>
 * New Scopes "inherit" entities from higher scopes. Methods defined
 * outside the current block are accessible inside this block, for example.
 */
public class Scope {
    private final LinkedMap<String, LocalVariable> localVariables;

    private final List<Function> functionSignatures;

    public final List<FunctionType> functionTypes;

    private final List<StructType> structs;

    private final String name;

    public Scope(String name) {
        this.name = name;
        localVariables = new LinkedMap<>();
        functionSignatures = new ArrayList<>();
        structs = new ArrayList<>();
        functionTypes = new ArrayList<>();
    }

    public Scope(Scope scope) {
        name = scope.name;
        functionSignatures = scope.functionSignatures;
        localVariables = scope.localVariables.clone();
        structs = scope.structs;
        functionTypes = scope.functionTypes;
    }

    /**
     * @param f string name to search for
     * @return FunctionType if any functions of name `f` exist in the current scope
     */
    public FunctionType findFunctionType(String f) {
        return functionTypes.stream().filter(fType -> fType.name.equals(f)).findFirst().orElse(null);
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
                .orElse(null);
    }

    /**
     * @param varName name to search for
     * @return index of local variable in frame
     */
    public int getLocalVariableIndex(String varName) {
        // `this` and `super` usually occupy position 0 so we start at position 1?
        return localVariables.indexOf(varName) + 1;
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
    public void addSignature(Function signature) {
        functionSignatures.add(signature);
    }

    /**
     * @param name      scoped name of the method
     * @param arguments list of arguments in the function call
     * @return a FunctionSignature if such a function exists in the current scope.
     */
    public Function getSignature(String name, List<Identifier> arguments) {
        return functionSignatures.stream()
                .filter(signature -> signature.matches(name, arguments))
                .findFirst()
                .orElse(null);
    }

    public Function getSignatureByTypes(String name, List<Type> argTypes) {
        var identifiers = argTypes.stream().map(e -> new Identifier("_", e)).collect(Collectors.toList());
        return getSignature(name, identifiers);
    }

    /**
     * @param struct Struct to add to the current scope
     */
    public void addStruct(StructType struct) {
        structs.add(struct);
    }

    /**
     * @param name name of the Struct
     * @return a Struct if one named name exists in the current scope
     */
    public StructType getStruct(String name) {
        return structs.stream().filter(struct -> struct.identifier.name.equals(name)).findFirst()
                .orElse(null);
    }
}
