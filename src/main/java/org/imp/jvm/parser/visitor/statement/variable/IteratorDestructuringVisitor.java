package org.imp.jvm.parser.visitor.statement.variable;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.statement.variable.IteratorDestructuring;
import org.imp.jvm.parser.visitor.expression.ExpressionVisitor;

public class IteratorDestructuringVisitor extends ImpParserBaseVisitor<IteratorDestructuring> {
    private final ExpressionVisitor expressionVisitor;
    private final Scope scope;

    public IteratorDestructuringVisitor(ExpressionVisitor expressionVisitor, Scope scope) {
        this.expressionVisitor = expressionVisitor;
        this.scope = scope;
    }

    @Override
    public IteratorDestructuring visitIteratorDestructuring(ImpParser.IteratorDestructuringContext ctx) {
        return super.visitIteratorDestructuring(ctx);
    }
}
