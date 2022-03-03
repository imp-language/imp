package org.imp.jvm.legacy.statement;

import org.imp.jvm.legacy.domain.scope.Scope;
import org.imp.jvm.legacy.expression.Expression;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class If extends Statement {
    public final Expression condition;
    public final Statement trueStatement;
    public final Statement falseStatement;


    public If(Expression condition, Statement trueStatement, Statement falseStatement) {
        this.condition = condition;
        this.trueStatement = trueStatement;
        this.falseStatement = falseStatement;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        condition.generate(mv, scope);

        Label trueLabel = new Label();
        Label endLabel = new Label();
        mv.visitJumpInsn(Opcodes.IFNE, trueLabel);

        if (falseStatement != null) {
            falseStatement.generate(mv, scope);
        }

        mv.visitJumpInsn(Opcodes.GOTO, endLabel);
        mv.visitLabel(trueLabel);
        trueStatement.generate(mv, scope);
        mv.visitLabel(endLabel);

    }

    @Override
    public void validate(Scope scope) {
        condition.validate(scope);
        trueStatement.validate(scope);
        if (falseStatement != null) {

            falseStatement.validate(scope);
        }
    }

}
