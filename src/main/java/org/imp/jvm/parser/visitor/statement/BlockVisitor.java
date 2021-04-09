package org.imp.jvm.parser.visitor.statement;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.statement.Block;

public class BlockVisitor extends ImpParserBaseVisitor<Block> {
    private final Scope scope;

    public BlockVisitor(Scope scope) {
        this.scope = scope;
    }

    @Override
    public Block visitBlock(ImpParser.BlockContext ctx) {
        return super.visitBlock(ctx);
    }
}
