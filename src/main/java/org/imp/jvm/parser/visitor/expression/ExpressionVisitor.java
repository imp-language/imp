package org.imp.jvm.parser.visitor.expression;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.domain.expression.Expression;
import org.imp.jvm.domain.expression.Literal;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.statement.Function;

public class ExpressionVisitor extends ImpParserBaseVisitor<Expression> {
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
    private final CallStatementVisitor callStatementVisitor;
    private final NewObjectVisitor newObjectVisitor;
    private final MemberIndexVisitor memberIndexVisitor;


    public ExpressionVisitor(Scope scope) {
        literalVisitor = new LiteralVisitor(this);
        unaryNotVisitor = new UnaryNotVisitor();
        unaryAdditiveVisitor = new UnaryAdditiveVisitor();
        powerVisitor = new PowerVisitor();
        multiplicativeVisitor = new MultiplicativeVisitor();
        additiveVisitor = new AdditiveVisitor();
        relationalVisitor = new RelationalVisitor();
        logicalVisitor = new LogicalVisitor();
        propertyAccessVisitor = new PropertyAccessVisitor();
        methodCallVisitor = new MethodCallVisitor();
        postIncrementVisitor = new PostIncrementVisitor();
        callStatementVisitor = new CallStatementVisitor();
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
}
