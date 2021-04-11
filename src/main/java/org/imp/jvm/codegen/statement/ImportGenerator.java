package org.imp.jvm.codegen.statement;

import org.imp.jvm.codegen.expression.ExpressionGenerator;
import org.imp.jvm.domain.expression.Expression;
import org.imp.jvm.domain.statement.IfStatement;
import org.imp.jvm.domain.statement.Statement;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Optional;

public class ImportGenerator {
    private final StatementGenerator statementGenerator;
    private final ExpressionGenerator expressionGenerator;
    private final MethodVisitor mv;

    public ImportGenerator(StatementGenerator statementGenerator, ExpressionGenerator expressionGenerator, MethodVisitor mv) {
        this.statementGenerator = statementGenerator;
        this.expressionGenerator = expressionGenerator;
        this.mv = mv;
    }

    public void generate(IfStatement ifStatement) {
        Expression condition = ifStatement.condition;
        condition.accept(expressionGenerator);

        Label trueLabel = new Label();
        Label endLabel = new Label();
        mv.visitJumpInsn(Opcodes.IFNE, trueLabel);
        Optional<Statement> falseStatement = Optional.ofNullable(ifStatement.elseIf);
        if (falseStatement.isPresent()) {
            falseStatement.get().accept(statementGenerator);
        }

        mv.visitJumpInsn(Opcodes.GOTO, endLabel);
        mv.visitLabel(trueLabel);
        ifStatement.body.accept(statementGenerator);
        mv.visitLabel(endLabel);
    }
}