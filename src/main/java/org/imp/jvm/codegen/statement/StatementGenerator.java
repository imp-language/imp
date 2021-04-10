package org.imp.jvm.codegen.statement;

import org.imp.jvm.codegen.expression.ExpressionGenerator;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.statement.Function;
import org.imp.jvm.domain.statement.IfStatement;
import org.imp.jvm.domain.statement.VariableDeclaration;
import org.objectweb.asm.MethodVisitor;

public class StatementGenerator {
    private final VariableDeclarationGenerator variableDeclarationGenerator = new VariableDeclarationGenerator(null, null);
    private final ExpressionGenerator expressionGenerator;
    private final IfGenerator ifGenerator;

    public StatementGenerator(MethodVisitor methodVisitor, Scope scope) {
        expressionGenerator = new ExpressionGenerator(methodVisitor, scope);
        ifGenerator = new IfGenerator(this, expressionGenerator, methodVisitor);
    }

    public void generate(VariableDeclaration variableDeclaration) {
        variableDeclarationGenerator.generate(variableDeclaration);
    }

    public void generate(IfStatement ifStatement) {
        ifGenerator.generate(ifStatement);
    }

    public void generate(Function function) {
    }
}
