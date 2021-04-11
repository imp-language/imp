package org.imp.jvm.codegen.statement;

import org.imp.jvm.codegen.expression.ExpressionGenerator;
import org.imp.jvm.domain.expression.Expression;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.statement.Block;
import org.imp.jvm.domain.statement.IfStatement;
import org.imp.jvm.domain.statement.Statement;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;
import java.util.Optional;

public class BlockGenerator {
    private final MethodVisitor methodVisitor;

    public BlockGenerator(MethodVisitor methodVisitor) {
        this.methodVisitor = methodVisitor;
    }

    public void generate(Block block) {
        Scope scope = block.scope;
        List<Statement> statements = block.statements;
        StatementGenerator statementGenerator = new StatementGenerator(methodVisitor, scope);
        statements.forEach(stmt -> stmt.accept(statementGenerator));
    }
}