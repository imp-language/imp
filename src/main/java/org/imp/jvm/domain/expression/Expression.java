package org.imp.jvm.domain.expression;

import org.imp.jvm.codegen.statement.StatementGenerator;
import org.imp.jvm.domain.statement.Statement;
import org.imp.jvm.domain.types.Type;

public interface Expression extends Statement {
    Type getType();

    void accept(StatementGenerator generator);
}
