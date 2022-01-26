package org.imp.jvm;

import org.apache.commons.collections4.map.LinkedMap;
import org.imp.jvm.errors.Comptime;
import org.imp.jvm.types.Type;

import java.io.File;

public class Environment {


    // Type aliases, functions, structs, and enums (Don't use, Todo: deprecate)
    private final LinkedMap<String, Type> types = new LinkedMap<>();
    // Variables referencing values of the above
    private final LinkedMap<String, Type> variables = new LinkedMap<>();
    private Environment parent;

    public Environment() {
        this.parent = null;
    }

    @Deprecated
    public void addType(String name, Type type) {
        variables.put(name, type);
    }

    public void addVariable(String name, Type type) {
        variables.put(name, type);
    }

    public void addVariableOrError(String name, Type type, File file, Node node) {
        if (getVariable(name) != null) {
            Comptime.Redeclaration.submit(file, node, name);
        } else {
            addVariable(name, type);
        }
    }

    /**
     * @param varName name to search for
     * @return index of local variable in frame
     */
    public int getLocalVariableIndex(String varName) {
        // `this` and `super` usually occupy position 0 so we start at position 1?
        if (getVariable(varName) == null) {
            throw new Error("variable lost somewhere during compilation.");
        }
        return variables.indexOf(varName) + 1;
    }

    public Environment getParent() {
        return parent;
    }

    public void setParent(Environment parent) {
        this.parent = parent;
    }

    public Type getVariable(String name) {
        if (variables.get(name) != null) {
            return variables.get(name);
        }
        if (parent != null) {
            return parent.getVariable(name);
        }
        return null;
    }

    // Todo: This is (better but still) bad and errors ofter.
    public <T> T getVariableTyped(String name, Class<T> clazz) {
        var v = getVariable(name);
        if (clazz.isInstance(v)) {
            //noinspection unchecked
            return (T) v;
        }
        return null;
    }

    public void setVariableType(String name, Type type) {
        if (variables.get(name) != null) {
            variables.put(name, type);
        } else {
//            System.err.println("reee");
        }
    }

}
