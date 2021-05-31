package org.imp.jvm.statement;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.imp.jvm.domain.scope.Scope;
import org.objectweb.asm.MethodVisitor;

import java.util.Optional;

public abstract class Statement {
    private Token ctx;

    public void setLine(Token ctx) {
        this.ctx = ctx;
    }

    public Token getLine() {
        return Optional.ofNullable(this.ctx).orElseThrow();
    }

    public abstract void generate(MethodVisitor mv, Scope scope);

    public abstract void validate();

}
