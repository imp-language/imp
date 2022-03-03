package org.imp.jvm.legacy.parsing.visitor.expression;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.legacy.expression.ListLiteral;
import org.imp.jvm.legacy.expression.Literal;
import org.imp.jvm.types.BuiltInType;

import java.util.stream.Collectors;

public class LiteralVisitor extends ImpParserBaseVisitor<Literal> {
    public final ExpressionVisitor expressionVisitor;

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


    @Override
    public Literal visitCollectionLiteral(ImpParser.CollectionLiteralContext ctx) {
        // Currently parsing list literals only.
        // type inference, first element in list defines type of whole collection
        // Todo: add [type] empty list literals
        var listElements = ctx.expressionList().expression();

        var listExpressions = listElements.stream().map(listEl -> listEl.accept(expressionVisitor)).collect(Collectors.toList());

        var listLiteral = new ListLiteral(listExpressions);
        listLiteral.setCtx(ctx, "parent.name");
        return listLiteral;
    }

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
