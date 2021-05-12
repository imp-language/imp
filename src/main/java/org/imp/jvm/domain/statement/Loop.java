package org.imp.jvm.domain.statement;

import org.imp.jvm.codegen.statement.StatementGenerator;
import org.imp.jvm.domain.expression.Expression;

import java.util.Optional;

public class Loop implements Statement {
    public final Expression condition;
    public final Block body;

    public Loop(Expression condition, Block body) {
        this.condition = condition;
        this.body = Optional.ofNullable(body).orElse(new Block()); // Todo: empty loop body is probably an error
    }

    @Override
    public void accept(StatementGenerator generator) {
    }
}
