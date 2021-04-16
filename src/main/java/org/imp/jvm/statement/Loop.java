package org.imp.jvm.statement;

import org.apache.commons.lang3.NotImplementedException;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.expression.Expression;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.util.Optional;

public class Loop extends Statement {
    public final Expression condition;
    public final Block body;

    public Loop(Expression condition, Block body) {
        this.condition = condition;
        this.body = Optional.ofNullable(body).orElse(new Block()); // Todo: empty loop body is probably an error
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        throw new NotImplementedException("todo");
    }

}
