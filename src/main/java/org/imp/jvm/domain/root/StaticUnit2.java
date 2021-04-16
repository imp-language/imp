package org.imp.jvm.domain.root;


import org.imp.jvm.statement.Function;
import org.imp.jvm.statement.Statement;

import java.util.ArrayList;
import java.util.List;

public class StaticUnit2 extends RootUnit {
    public List<Statement> staticInitializations = new ArrayList<>();
    public final List<Function> functions = new ArrayList<>();

    public StaticUnit2(String name) {
        super(name);
    }

}
