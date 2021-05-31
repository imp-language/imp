package org.imp.jvm.statement;

import org.apache.commons.lang3.NotImplementedException;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.expression.Expression;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import javax.swing.plaf.nimbus.State;
import java.util.Optional;

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
    public void validate() {
        condition.validate();
        trueStatement.validate();
        falseStatement.validate();
    }

}
