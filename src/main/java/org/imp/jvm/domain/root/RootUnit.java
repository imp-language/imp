package org.imp.jvm.domain.root;

import org.imp.jvm.domain.scope.Method;
import org.imp.jvm.domain.scope.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * An Imp file is split into several root units,
 * one for all global content, and one for each class
 */
public abstract class RootUnit {
    public final String name;

    public final List<Property> properties = new ArrayList<>();
    public final List<Method> methods = new ArrayList<>();


    public RootUnit(String name) {
        this.name = name;
    }

}
