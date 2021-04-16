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
        String name = ctx.identifier().getText();
        Expression expression = ctx.expression().accept(expressionVisitor);

        return new Assignment(name, expression);
    }
}
