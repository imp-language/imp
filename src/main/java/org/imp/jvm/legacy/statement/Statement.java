package org.imp.jvm.legacy.statement;

import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Optional;

public abstract class Statement {
    private ParserRuleContext ctx;
    private String filename;

    public ParserRuleContext getCtx() {
        return Optional.ofNullable(this.ctx).orElseThrow();
    }

    public String getFilename() {
        return Optional.ofNullable(this.filename).orElseThrow();
    }

    public void setCtx(ParserRuleContext ctx, String filename) {
        this.ctx = ctx;
        this.filename = filename;
    }


}
