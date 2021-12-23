package org.imp.jvm;

import org.apache.commons.collections4.map.LinkedMap;
import org.imp.jvm.types.Type;

public class Environment {


    private Environment parent;


    // Type aliases, functions, structs, and enums
    private final LinkedMap<String, Type> types = new LinkedMap<>();
    // Variables referencing values of the above
    private final LinkedMap<String, Type> variables = new LinkedMap<>();

    public Environment() {
        this.parent = null;
    }

    public void addType(String name, Type type) {
        variables.put(name, type);
    }

    public void addVariable(String name, Type type) {
        variables.put(name, type);
    }

    // Todo: This is (better but still) bad and errors ofter.
    public <T> T getVariableTyped(String name, Class<T> clazz) {
        var v = getVariable(name);
        if (clazz.isInstance(v)) {
            return (T) v;
        }
        return null;
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

    public void setVariableType(String name, Type type) {
        if (variables.get(name) != null) {
            variables.put(name, type);
        } else {
            System.err.println("reee");
        }
    }

    public Environment getParent() {
        return parent;
    }

    public void setParent(Environment parent) {
        this.parent = parent;
    }

}
