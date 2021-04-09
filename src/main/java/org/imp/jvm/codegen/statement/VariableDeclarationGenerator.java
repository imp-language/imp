package org.imp.jvm.codegen.statement;

import org.imp.jvm.codegen.expression.ExpressionGenerator;
import org.imp.jvm.domain.statement.VariableDeclaration;

public class VariableDeclarationGenerator {
    private final StatementGenerator statementGenerator;
    private final ExpressionGenerator expressionGenerator;

    public VariableDeclarationGenerator(StatementGenerator statementGenerator, ExpressionGenerator expressionGenerator) {
        this.statementGenerator = statementGenerator;
        this.expressionGenerator = expressionGenerator;
    }

    public void generate(VariableDeclaration variableDeclaration) {
//        Expression expression = variableDeclaration.getExpression();
//        expression.accept(expressionGenerator);
//        Assignment assignment = new Assignment(variableDeclaration);
//        assignment.accept(statementGenerator);
    }
}
