package org.imp.jvm.domain.statement;

import org.imp.jvm.codegen.statement.StatementGenerator;
import org.imp.jvm.domain.scope.Scope;

import java.util.List;

public class Block implements Statement {
    public final List<Statement> statements;
    public final Scope scope;

    public Block(List<Statement> statements, Scope scope) {
        this.statements = statements;
        this.scope = scope;
    }

    @Override
    public void accept(StatementGenerator generator) {

    }
}
