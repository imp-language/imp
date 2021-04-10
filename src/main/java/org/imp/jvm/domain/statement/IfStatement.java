package org.imp.jvm.domain.statement;

import org.imp.jvm.codegen.statement.StatementGenerator;
import org.imp.jvm.domain.expression.Expression;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.types.Type;
import org.imp.jvm.parser.visitor.statement.IfVisitor;

import java.util.List;
import java.util.Optional;

public class IfStatement implements Statement {
    public final Expression condition;
    public final Block body;
    public IfStatement elseIf;

    public IfStatement(Expression condition, Block body, IfStatement elseIf) {
        this.condition = condition;
        this.body = body;
        this.elseIf = elseIf;
    }

    @Override
    public void accept(StatementGenerator generator) {

    }
}
