package org.imp.jvm.domain.scope;

import java.util.HashMap;
import java.util.Map;

public class Scope {
    private final Map<String, LocalVariable> localVariables;

    public Scope() {
        localVariables = new HashMap<>();
    }

    public void addLocalVariable(LocalVariable variable) {
        localVariables.put(variable.getName(), variable);
    }
}
