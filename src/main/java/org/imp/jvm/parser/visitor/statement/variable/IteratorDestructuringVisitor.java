package org.imp.jvm.parser.visitor.statement.variable;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.domain.statement.variable.IteratorDestructuring;

public class IteratorDestructuringVisitor extends ImpParserBaseVisitor<IteratorDestructuring> {
    @Override
    public IteratorDestructuring visitIteratorDestructuring(ImpParser.IteratorDestructuringContext ctx) {
        return super.visitIteratorDestructuring(ctx);
    }
}
