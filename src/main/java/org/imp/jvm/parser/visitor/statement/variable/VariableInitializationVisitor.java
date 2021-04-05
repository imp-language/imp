package org.imp.jvm.parser.visitor.statement.variable;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.codegen.expression.Expression;
import org.imp.jvm.domain.statement.variable.IteratorDestructuring;
import org.imp.jvm.domain.statement.variable.VariableInitialization;

public class VariableInitializationVisitor extends ImpParserBaseVisitor<VariableInitialization> {
    @Override
    public VariableInitialization visitVariableInitialize(ImpParser.VariableInitializeContext ctx) {
        VariableInitialization initialization = new VariableInitialization();
        String identifier = ctx.identifier().getText();

        if (ctx.ASSIGN() != null) {
            ImpParser.ExpressionContext expressionContext = ctx.expression();
            Expression expression = expressionContext.accept(null);
        }
        

//        Expression expression =
        System.out.println("reeeeee");
        return null;
    }

}
