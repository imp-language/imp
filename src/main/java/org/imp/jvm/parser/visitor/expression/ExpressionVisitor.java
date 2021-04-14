package org.imp.jvm.parser.visitor.expression;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.domain.expression.Expression;
import org.imp.jvm.domain.scope.Scope;

public class ExpressionVisitor extends ImpParserBaseVisitor<Expression> {
    private final IdentifierReferenceVisitor identifierReferenceVisitor;
    private final LiteralVisitor literalVisitor;
    private final UnaryNotVisitor unaryNotVisitor;

    private final UnaryAdditiveVisitor unaryAdditiveVisitor;
    private final PowerVisitor powerVisitor;
    private final MultiplicativeVisitor multiplicativeVisitor;
    private final AdditiveVisitor additiveVisitor;
    private final RelationalVisitor relationalVisitor;
    private final LogicalVisitor logicalVisitor;
    private final PropertyAccessVisitor propertyAccessVisitor;
    private final MethodCallVisitor methodCallVisitor;
    private final PostIncrementVisitor postIncrementVisitor;
    private final CallVisitor callStatementVisitor;
    private final NewObjectVisitor newObjectVisitor;
    private final MemberIndexVisitor memberIndexVisitor;


    public ExpressionVisitor(Scope scope) {
        identifierReferenceVisitor = new IdentifierReferenceVisitor(scope);
        literalVisitor = new LiteralVisitor(this);
        unaryNotVisitor = new UnaryNotVisitor();
        unaryAdditiveVisitor = new UnaryAdditiveVisitor();
        powerVisitor = new PowerVisitor();
        multiplicativeVisitor = new MultiplicativeVisitor();
        additiveVisitor = new AdditiveVisitor(this);
        relationalVisitor = new RelationalVisitor(this);
        logicalVisitor = new LogicalVisitor();
        propertyAccessVisitor = new PropertyAccessVisitor();
        methodCallVisitor = new MethodCallVisitor();
        postIncrementVisitor = new PostIncrementVisitor();
        callStatementVisitor = new CallVisitor(scope, this);
        newObjectVisitor = new NewObjectVisitor();
        memberIndexVisitor = new MemberIndexVisitor();
    }

    @Override
    public Expression visitLiteral(ImpParser.LiteralContext ctx) {
        return literalVisitor.visitLiteral(ctx);
    }

    @Override
    public Expression visitUnaryNotExpression(ImpParser.UnaryNotExpressionContext ctx) {
        return super.visitUnaryNotExpression(ctx);
    }

    @Override
    public Expression visitCallStatementExpression(ImpParser.CallStatementExpressionContext ctx) {
        return callStatementVisitor.visitCallStatementExpression(ctx);
    }

    @Override
    public Expression visitRelationalExpression(ImpParser.RelationalExpressionContext ctx) {
        return relationalVisitor.visitRelationalExpression(ctx);
    }

    @Override
    public Expression visitIdentifierReferenceExpression(ImpParser.IdentifierReferenceExpressionContext ctx) {
        return identifierReferenceVisitor.visitIdentifierReferenceExpression(ctx);
    }


    @Override
    public Expression visitAdditiveExpression(ImpParser.AdditiveExpressionContext ctx) {
        return additiveVisitor.visitAdditiveExpression(ctx);
    }
}
