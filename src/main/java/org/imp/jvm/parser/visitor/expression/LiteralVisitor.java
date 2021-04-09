package org.imp.jvm.parser.visitor.expression;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.domain.expression.Literal;
import org.imp.jvm.domain.statement.VariableDeclaration;
import org.imp.jvm.domain.statement.variable.Declaration;
import org.imp.jvm.domain.types.Mutability;
import org.imp.jvm.parser.visitor.statement.variable.IteratorDestructuringVisitor;
import org.imp.jvm.parser.visitor.statement.variable.VariableInitializationVisitor;

public class LiteralVisitor extends ImpParserBaseVisitor<Literal> {


    @Override
    public Literal visitLiteral(ImpParser.LiteralContext ctx) {
        return new Literal();
    }
}
