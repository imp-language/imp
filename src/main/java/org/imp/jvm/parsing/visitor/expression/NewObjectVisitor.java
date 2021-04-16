package org.imp.jvm.parsing.visitor.expression;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.domain.expression.Literal;

public class NewObjectVisitor extends ImpParserBaseVisitor<Literal> {


    @Override
    public Literal visitLiteral(ImpParser.LiteralContext ctx) {
        return super.visitLiteral(ctx);
    }
}
