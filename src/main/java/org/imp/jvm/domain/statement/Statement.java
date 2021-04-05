package org.imp.jvm.domain.statement;

import org.imp.jvm.codegen.statement.StatementGenerator;

public interface Statement {
    void accept(StatementGenerator generator);
}
