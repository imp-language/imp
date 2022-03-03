package org.imp.jvm.legacy.statement;

import org.antlr.v4.runtime.ParserRuleContext;
import org.imp.jvm.legacy.domain.scope.Scope;
import org.objectweb.asm.MethodVisitor;

import java.util.List;
import java.util.Optional;

public abstract class Statement {
    private ParserRuleContext ctx;
    private String filename;

    public void setCtx(ParserRuleContext ctx, String filename) {
        this.ctx = ctx;
        this.filename = filename;
    }

    public ParserRuleContext getCtx() {
        return Optional.ofNullable(this.ctx).orElseThrow();
    }

    public String getFilename() {
        return Optional.ofNullable(this.filename).orElseThrow();
    }

    public abstract void generate(MethodVisitor mv, Scope scope);

    public abstract void validate(Scope scope);

    public List<Statement> getChildren() {
        return null;
    }

}
