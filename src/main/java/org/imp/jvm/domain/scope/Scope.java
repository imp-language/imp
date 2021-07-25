package org.imp.jvm.domain.scope;

import org.imp.jvm.expression.reference.ClosureReference;
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


    public final LinkedMap<String, ClosureReference> closures;

    public FunctionType functionType = null;

    public final List<FunctionType> functionTypes;

    private final List<StructType> structs;

    private final String name;

    public final Scope parentScope;

    // Root scopes
    public Scope() {
        this.name = "root";
        localVariables = new LinkedMap<>();
        closures = new LinkedMap<>();
        structs = new ArrayList<>();
        functionTypes = new ArrayList<>();
        this.parentScope = null;
    }

    // Scopes from parent scopes
    public Scope(Scope scope) {
        name = scope.name + "-|";
        localVariables = new LinkedMap<>();
        closures = new LinkedMap<>();
        structs = scope.structs;
        functionTypes = scope.functionTypes;
        functionType = scope.functionType;
        this.parentScope = scope;
    }


    public void addClosure(ClosureReference closureReference) {
        closures.put(closureReference.getName(), closureReference);
    }

    public ClosureReference getClosure(String name) {
        return Optional.ofNullable(closures.get(name))
                .orElse(null);
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
        return getLocalVariable(varName) != null;
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
