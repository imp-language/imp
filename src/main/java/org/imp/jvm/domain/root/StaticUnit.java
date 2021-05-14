package org.imp.jvm.domain.root;


import org.imp.jvm.statement.Function;
import org.imp.jvm.statement.Statement;
import org.imp.jvm.statement.Struct;

import java.util.ArrayList;
import java.util.List;

public class StaticUnit extends RootUnit {
    public List<Statement> staticInitializations = new ArrayList<>();
    public final List<Function> functions = new ArrayList<>();

    public StaticUnit(String name) {
        super(name);
    }

}
