package org.imp.jvm.domain.statement;

import org.imp.jvm.codegen.FieldGenerator;
import org.imp.jvm.codegen.statement.FunctionGenerator;
import org.imp.jvm.codegen.statement.StatementGenerator;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.types.Type;

import java.util.List;

public class Function extends Identifier implements Statement {
    public final Block block;
    public final List<Identifier> parameters;

    public Function(String identifier, Block block, List<Identifier> parameters, Type returnType) {
        super();
        this.name = identifier;
        this.block = block;
        this.parameters = parameters;
        this.type = returnType;
    }

    public void accept(FunctionGenerator generator) {
        generator.generate(this);
    }

    @Override
    public void accept(StatementGenerator generator) {

    }
}
