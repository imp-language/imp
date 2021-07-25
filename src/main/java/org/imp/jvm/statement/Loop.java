package org.imp.jvm.statement;

import org.apache.commons.lang3.NotImplementedException;
import org.imp.jvm.domain.scope.Scope;
import org.objectweb.asm.MethodVisitor;

public abstract class Loop extends Statement {
    public final Block body;
    public LoopKind loopKind;


    public enum LoopKind {
        For,
        ForIn,
        While
    }

    public Loop(Block block) {
        this.body = block;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        throw new NotImplementedException("todo");
    }


}


