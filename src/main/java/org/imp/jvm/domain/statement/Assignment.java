package org.imp.jvm.domain.statement;

import org.imp.jvm.codegen.statement.StatementGenerator;
import org.imp.jvm.domain.expression.Expression;
import org.imp.jvm.domain.statement.variable.VariableInitialization;


// Only regular assignment, e.g. = is implemented so far
public class Assignment implements Statement {
    public final String name;
    public final Expression expression;

    public Assignment(String name, Expression expression) {
        this.name = name;
        this.expression = expression;
    }

    public Assignment(VariableDeclaration variableDeclaration) {
        this.name = variableDeclaration.declaration.name;
        // ToDo: need to account for iterator destructuring declarations
        var declaration = (VariableInitialization) variableDeclaration.declaration;
        this.expression = declaration.expression;
    }


    @Override
    public void accept(StatementGenerator generator) {
        generator.generate(this);
    }
}
