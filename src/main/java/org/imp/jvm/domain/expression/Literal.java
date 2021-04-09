package org.imp.jvm.domain.expression;

import org.imp.jvm.codegen.statement.StatementGenerator;
import org.imp.jvm.domain.types.Type;

public class Literal implements Expression {
    @Override
    public Type getType() {
        return null;
    }

    @Override
    public void accept(StatementGenerator generator) {

    }
}
