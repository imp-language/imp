package org.imp.jvm.codegen.statement;

import org.imp.jvm.codegen.expression.ExpressionGenerator;
import org.imp.jvm.domain.expression.Expression;
import org.imp.jvm.domain.statement.IfStatement;
import org.imp.jvm.domain.statement.Loop;
import org.imp.jvm.domain.statement.Statement;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Optional;

public class LoopGenerator {
    private final StatementGenerator statementGenerator;
    private final ExpressionGenerator expressionGenerator;
    private final MethodVisitor mv;

    public LoopGenerator(StatementGenerator statementGenerator, ExpressionGenerator expressionGenerator, MethodVisitor mv) {
        this.statementGenerator = statementGenerator;
        this.expressionGenerator = expressionGenerator;
        this.mv = mv;
    }

    public void generate(Loop loop) {
        
    }
}