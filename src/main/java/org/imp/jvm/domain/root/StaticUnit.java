package org.imp.jvm.domain.root;


import org.imp.jvm.domain.statement.Function;
import org.imp.jvm.domain.statement.Statement;

import java.util.ArrayList;
import java.util.List;

public class StaticUnit extends RootUnit {
    public List<Statement> staticInitializations = new ArrayList<>();
    public List<Function> functions = new ArrayList<>();

    public StaticUnit(String name) {
        super(name);
    }

}
