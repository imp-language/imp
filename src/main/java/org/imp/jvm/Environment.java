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

    public Environment getParent() {
        return parent;
    }

    public void setParent(Environment parent) {
        this.parent = parent;
    }
}
