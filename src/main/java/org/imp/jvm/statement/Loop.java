package org.imp.jvm.statement;

import org.apache.commons.lang3.NotImplementedException;
import org.imp.jvm.expression.Expression;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Optional;

public class Loop extends Statement {
    public final Expression condition;
    public final Block body;

    public Loop(Expression condition, Block body) {
        this.condition = condition;
        this.body = Optional.ofNullable(body).orElse(new Block()); // Todo: empty loop body is probably an error
    }

    @Override
    public void generate(MethodVisitor mv) {
        throw new NotImplementedException("todo");
    }

    @Override
    public void generate(ClassWriter cw) {
        throw new NotImplementedException("ree");
    }
}
