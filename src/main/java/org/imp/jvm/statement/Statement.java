package org.imp.jvm.statement;

import org.antlr.v4.runtime.ParserRuleContext;
import org.imp.jvm.domain.scope.Scope;
import org.objectweb.asm.MethodVisitor;

import java.util.List;
import java.util.Optional;

public abstract class Statement {
    private ParserRuleContext ctx;

    public void setCtx(ParserRuleContext ctx) {
        this.ctx = ctx;
    }

    public ParserRuleContext getCtx() {
        return Optional.ofNullable(this.ctx).orElseThrow();
    }

    public abstract void generate(MethodVisitor mv, Scope scope);

    public abstract void validate(Scope scope);

    public List<Statement> getChildren() {
        return null;
    }

}
