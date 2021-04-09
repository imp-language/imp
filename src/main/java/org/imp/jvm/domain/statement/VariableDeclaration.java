package org.imp.jvm.domain.statement;

import org.imp.jvm.codegen.statement.StatementGenerator;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.statement.variable.Declaration;
import org.imp.jvm.domain.types.Mutability;

public class VariableDeclaration implements Statement {
    public final Mutability mutability;

    public final Declaration declaration;


    public VariableDeclaration(Mutability mutability, Declaration declaration) {
        this.mutability = mutability;
        this.declaration = declaration;
    }

    @Override
    public void accept(StatementGenerator generator) {
        generator.generate(this);
    }
}
