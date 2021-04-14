package org.imp.jvm.codegen.statement;

import org.imp.jvm.codegen.expression.ExpressionGenerator;
import org.imp.jvm.domain.expression.Expression;
import org.imp.jvm.domain.statement.VariableDeclaration;

public class VariableDeclarationGenerator {
    private final StatementGenerator statementGenerator;
    private final ExpressionGenerator expressionGenerator;

    public VariableDeclarationGenerator(StatementGenerator statementGenerator, ExpressionGenerator expressionGenerator) {
        this.statementGenerator = statementGenerator;
        this.expressionGenerator = expressionGenerator;
    }

    public void generate(VariableDeclaration variableDeclaration) {
        // Todo: https://github.com/JakubDziworski/Enkel-JVM-language/blob/1527076545f7402a279db2c19f1e28ba7f084585/compiler/src/main/java/com/kubadziworski/bytecodegeneration/statement/VariableDeclarationStatementGenerator.java
//        Expression expression = variableDeclaration.getExpression();
//        expression.accept(expressionGenerator);
//        Assignment assignment = new Assignment(variableDeclaration);
//        assignment.accept(statementGenerator);
    }
}
