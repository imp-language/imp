package org.imp.jvm.parser.visitor.statement;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.domain.expression.Expression;
import org.imp.jvm.domain.statement.Assignment;
import org.imp.jvm.domain.statement.Function;
import org.imp.jvm.parser.visitor.expression.ExpressionVisitor;

public class AssignmentVisitor extends ImpParserBaseVisitor<Assignment> {

    private final ExpressionVisitor expressionVisitor;

    public AssignmentVisitor(ExpressionVisitor expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public Assignment visitAssignment(ImpParser.AssignmentContext ctx) {
        Expression left = ctx.expression(0).accept(expressionVisitor);
        Expression right = ctx.expression(1).accept(expressionVisitor);

        return new Assignment(left, right);
    }
}
