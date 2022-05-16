package org.imp.jvm.domain;

import org.apache.commons.collections4.map.LinkedMap;
import org.imp.jvm.errors.Comptime;
import org.imp.jvm.parser.Node;
import org.imp.jvm.types.ImpType;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Environment {


    // Variables referencing values of the above
    private final LinkedMap<String, ImpType> variables = new LinkedMap<>();

    private final Map<String, Mutability> mutability = new HashMap<>();
    private Environment parent;

    public Environment() {
        this.parent = null;
    }


    public void addVariable(String name, ImpType type) {
        variables.put(name, type);
        mutability.put(name, Mutability.Val);
    }

    public void addVariableOrError(String name, ImpType type, File file, Node node) {
        if (getVariable(name) != null) {
            Comptime.Redeclaration.submit(file, node, name);
        } else {
            addVariable(name, type);
        }
    }


    public Environment getParent() {
        return parent;
    }

    public void setParent(Environment parent) {
        this.parent = parent;
    }

    public ImpType getVariable(String name) {
        if (variables.get(name) != null) {
            return variables.get(name);
        }
        if (parent != null) {
            return parent.getVariable(name);
        }
        return null;
    }

    public Mutability getVariableMutability(String name) {
        if (mutability.get(name) != null) {
            return mutability.get(name);
        }
        if (parent != null) {
            return parent.getVariableMutability(name);
        }
        return null;
    }

    public <T> T getVariableTyped(String name, Class<T> clazz) {
        var v = getVariable(name);
        if (clazz.isInstance(v)) {
            //noinspection unchecked
            return (T) v;
        }
        return null;
    }

    public void setVariableMutability(String name, Mutability m) {
        if (mutability.get(name) != null) {
            mutability.put(name, m);
        }

    }

    public void setVariableType(String name, ImpType type) {
        if (variables.get(name) != null) {
            variables.put(name, type);
        }
    }

}
