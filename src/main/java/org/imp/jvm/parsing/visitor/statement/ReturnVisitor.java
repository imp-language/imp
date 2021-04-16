package org.imp.jvm.parsing.visitor.statement;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.domain.expression.Expression;
import org.imp.jvm.domain.statement.ReturnStatement;
import org.imp.jvm.parsing.visitor.expression.ExpressionVisitor;

public class ReturnVisitor extends ImpParserBaseVisitor<ReturnStatement> {
    private final ExpressionVisitor expressionVisitor;

    public ReturnVisitor(ExpressionVisitor expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public ReturnStatement visitReturnStatement(ImpParser.ReturnStatementContext ctx) {
        Expression expression = ctx.expression().accept(expressionVisitor);
        return new ReturnStatement(expression);
    }


}
