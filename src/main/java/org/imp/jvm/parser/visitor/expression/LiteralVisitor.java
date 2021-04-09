package org.imp.jvm.parser.visitor.expression;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.domain.expression.Literal;
import org.imp.jvm.domain.statement.VariableDeclaration;
import org.imp.jvm.domain.statement.variable.Declaration;
import org.imp.jvm.domain.types.BuiltInType;
import org.imp.jvm.domain.types.Mutability;
import org.imp.jvm.parser.visitor.statement.variable.IteratorDestructuringVisitor;
import org.imp.jvm.parser.visitor.statement.variable.VariableInitializationVisitor;

public class LiteralVisitor extends ImpParserBaseVisitor<Literal> {

    @Override
    public Literal visitIntegerLiteral(ImpParser.IntegerLiteralContext ctx) {
        return new Literal(BuiltInType.INT, ctx.DECIMAL_LIT().getText());
    }

    @Override
    public Literal visitBooleanLiteral(ImpParser.BooleanLiteralContext ctx) {
        return new Literal(BuiltInType.BOOLEAN, ctx.BooleanLiteral().getText());
    }

    @Override
    public Literal visitFloatLiteral(ImpParser.FloatLiteralContext ctx) {
        return new Literal(BuiltInType.FLOAT, ctx.FLOAT_LIT().getText());
    }

    @Override
    public Literal visitListLiteral(ImpParser.ListLiteralContext ctx) {
        // List literals are converted to collections
        return null;
    }

    @Override
    public Literal visitStringLiteral(ImpParser.StringLiteralContext ctx) {
        return new Literal(BuiltInType.STRING, ctx.STRING_LITERAL().getText());
    }

//    @Override
//    public Literal visitLiteral(ImpParser.LiteralContext ctx) {
//        return new Literal(Kind.);
//    }
}
