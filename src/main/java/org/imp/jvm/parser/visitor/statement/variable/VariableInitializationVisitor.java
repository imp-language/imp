package org.imp.jvm.parser.visitor.statement.variable;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.domain.expression.Expression;
import org.imp.jvm.domain.scope.LocalVariable;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.statement.variable.VariableInitialization;
import org.imp.jvm.parser.visitor.expression.ExpressionVisitor;

public class VariableInitializationVisitor extends ImpParserBaseVisitor<VariableInitialization> {
    private final ExpressionVisitor expressionVisitor;
    private final Scope scope;

    public VariableInitializationVisitor(ExpressionVisitor expressionVisitor, Scope scope) {
        this.expressionVisitor = expressionVisitor;
        this.scope = scope;
    }

    @Override
    public VariableInitialization visitVariableInitialize(ImpParser.VariableInitializeContext ctx) {
        VariableInitialization initialization;
        String identifier = ctx.identifier().getText();
        Expression expression = null;

        if (ctx.ASSIGN() != null) {
            ImpParser.ExpressionContext expressionContext = ctx.expression();
            expression = expressionContext.accept(expressionVisitor);
            initialization = new VariableInitialization(expression);
            initialization.type = expression.getType();
        } else {
            initialization = new VariableInitialization(null);
            initialization.type = null; // ToDo: read type notations
        }

        scope.addLocalVariable(new LocalVariable(identifier, initialization.type));


//        Expression expression =
        return initialization;
    }

}
