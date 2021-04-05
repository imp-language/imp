package org.imp.jvm.codegen.statement;

import org.imp.jvm.domain.statement.VariableDeclaration;

public class StatementGenerator {
    private final VariableDeclarationGenerator variableDeclarationGenerator = new VariableDeclarationGenerator(null, null);

    public void generate(VariableDeclaration variableDeclaration) {
        variableDeclarationGenerator.generate(variableDeclaration);
    }
}
