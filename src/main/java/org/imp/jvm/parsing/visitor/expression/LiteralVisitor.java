package org.imp.jvm.parsing.visitor.expression;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.expression.Literal;
import org.imp.jvm.types.BuiltInType;

public class LiteralVisitor extends ImpParserBaseVisitor<Literal> {
    private final ExpressionVisitor expressionVisitor;


    public LiteralVisitor(ExpressionVisitor expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public Literal visitIntegerLiteral(ImpParser.IntegerLiteralContext ctx) {
        return new Literal(BuiltInType.INT, ctx.getText());
    }

    @Override
    public Literal visitBooleanLiteral(ImpParser.BooleanLiteralContext ctx) {
        return new Literal(BuiltInType.BOOLEAN, ctx.BooleanLiteral().getText());
    }

    @Override
    public Literal visitFloatLiteral(ImpParser.FloatLiteralContext ctx) {
        return new Literal(BuiltInType.FLOAT, ctx.getText());
    }

    @Override
    public Literal visitDoubleLiteral(ImpParser.DoubleLiteralContext ctx) {
        return new Literal(BuiltInType.DOUBLE, ctx.getText());
    }


//    @Override
//    public Literal visitCollectionLiteral(ImpParser.CollectionLiteralContext ctx) {
////        // List literals are converted to collections
////        var elements = ctx.elementList().expression();
////        List<Expression> expressions = elements.stream().map(expressionVisitor::visit).collect(Collectors.toList());
////
////        // ToDo: type check lists
////        // type inference, first element in list defines type of whole collection
//
//        return new Literal(BuiltInType.VOID, null);
//    }

    @Override
    public Literal visitStringLiteral(ImpParser.StringLiteralContext ctx) {
        var lit = ctx.STRING_LITERAL();
        var stringLiteralWithEscapes = lit.getText().translateEscapes();

        return new Literal(BuiltInType.STRING, stringLiteralWithEscapes);
    }

//    @Override
//    public Literal visitLiteral(ImpParser.LiteralContext ctx) {
//        return new Literal(Kind.);
//    }
}
