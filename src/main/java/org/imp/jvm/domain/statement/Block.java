package org.imp.jvm.domain.statement;

import org.imp.jvm.codegen.statement.StatementGenerator;
import org.imp.jvm.domain.scope.Scope;

import java.util.ArrayList;
import java.util.List;

public class Block implements Statement {
    public final List<Statement> statements;
    public final Scope scope;

    public Block(List<Statement> statements, Scope scope) {
        this.statements = statements;
        this.scope = scope;
    }

    public Block() {
        this.statements = new ArrayList<>();
        this.scope = new Scope("unnamed");
    }

    @Override
    public void accept(StatementGenerator generator) {
        generator.generate(this);
    }
}
