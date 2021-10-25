package org.imp.jvm.domain.scope;

import org.apache.commons.collections4.map.LinkedMap;
import org.imp.jvm.expression.reference.ClosureReference;
import org.imp.jvm.types.FunctionType;
import org.imp.jvm.types.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


    private final String name;

    public final Scope parentScope;

    // Todo: migrate all "types" to this field
    // Types include functions, structs, external Java classes, type aliases, etc
    private final LinkedMap<String, Type> types;

    // Root scopes
    public Scope() {
        this.name = "root";
        localVariables = new LinkedMap<>();
        closures = new LinkedMap<>();
        functionTypes = new ArrayList<>();
        this.parentScope = null;
        this.types = new LinkedMap<>();
    }

    // Scopes from parent scopes
    public Scope(Scope scope) {
        name = scope.name + "-|";
        localVariables = scope.localVariables.clone();
        closures = new LinkedMap<>();
        functionTypes = scope.functionTypes;
        functionType = scope.functionType;
        this.parentScope = scope;
        this.types = scope.types;
    }

    public void addType(String name, Type type) {
        this.types.put(name, type);
    }

    /**
     * Retrieves a type if it has been aliased to
     * a name in the current scope.
     *
     * @param alias name of the type
     * @return a StructType, FunctionType, or ExternalType
     */
    public Type getType(String alias) {
        return types.get(alias);
    }


    public void addClosure(ClosureReference closureReference) {
        closures.put(closureReference.getName(), closureReference);
    }

    public ClosureReference getClosure(String name) {
        return Optional.ofNullable(closures.get(name))
                .orElse(null);
    }

    /**
     * @param f        string name to search for
     * @param isStatic
     * @return FunctionType if any functions of name `f` exist in the current scope
     */
    public FunctionType findFunctionType(String f, boolean isStatic) {
        return functionTypes.stream().filter(fType -> fType.name.equals(f) && fType.isStatic == isStatic).findFirst().orElse(null);

    }

    public void addFunctionType(FunctionType functionType) {
        functionTypes.add(functionType);
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
        if (!localVariables.containsKey(varName)) {
            throw new Error("variable lost somewhere during compilation.");
        }
        return localVariables.indexOf(varName) + 1;
    }

    /**
     * @param varName name to search for
     * @return whether a variable exists in the current scope
     */
    public boolean variableExists(String varName) {
        return getLocalVariable(varName) != null;
    }


}
