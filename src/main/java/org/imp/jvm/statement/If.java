package org.imp.jvm.statement;

import org.apache.commons.lang3.NotImplementedException;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.expression.Expression;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Optional;

public class If extends Statement {
    public final Expression condition;
    public final Block body;
    public If elseIf;


    public If(Expression condition, Block body, If elseIf) {
        this.condition = condition;
        this.body = Optional.ofNullable(body).orElse(new Block());
        this.elseIf = elseIf;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        condition.generate(mv);

        Label trueLabel = new Label();
        Label endLabel = new Label();
        mv.visitJumpInsn(Opcodes.IFNE, trueLabel);

        mv.visitJumpInsn(Opcodes.GOTO, endLabel);
        mv.visitLabel(trueLabel);
        body.generate(mv, null);
        mv.visitLabel(endLabel);
    }

    @Override
    public void generate(ClassWriter cw) {
        throw new NotImplementedException("ree");
    }
}
