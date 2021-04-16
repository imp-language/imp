package org.imp.jvm.parsing.visitor.statement;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.statement.VariableDeclaration;
import org.imp.jvm.domain.statement.variable.Declaration;
import org.imp.jvm.domain.types.Mutability;
import org.imp.jvm.parsing.visitor.expression.ExpressionVisitor;
import org.imp.jvm.parsing.visitor.statement.variable.IteratorDestructuringVisitor;
import org.imp.jvm.parsing.visitor.statement.variable.VariableInitializationVisitor;

public class VariableVisitor extends ImpParserBaseVisitor<VariableDeclaration> {
    private final ExpressionVisitor expressionVisitor;
    private final Scope scope;

    private final VariableInitializationVisitor variableInitializationVisitor;
    private final IteratorDestructuringVisitor iteratorDestructuringVisitor;

    public VariableVisitor(ExpressionVisitor expressionVisitor, Scope scope) {
        this.expressionVisitor = expressionVisitor;
        this.scope = scope;
        variableInitializationVisitor = new VariableInitializationVisitor(expressionVisitor, scope);
        iteratorDestructuringVisitor = new IteratorDestructuringVisitor(expressionVisitor, scope);
    }

    @Override
    public VariableDeclaration visitVariableStatement(ImpParser.VariableStatementContext ctx) {
        Mutability mutability = Mutability.Val;

        // Is the variable described as `mut` or `val`?
        if (ctx.VAL() != null && ctx.MUT() == null) {
            mutability = Mutability.Val;
        } else if (ctx.VAL() == null && ctx.MUT() != null) {
            mutability = Mutability.Mut;
        }


        // Variable initialization or iterator descructuring?
        ImpParser.IteratorDestructuringContext iteratorDestructuringContext = ctx.iteratorDestructuring();
        ImpParser.VariableInitializeContext variableInitializeContext = ctx.variableInitialize();
        Declaration declaration = null;

        if (iteratorDestructuringContext != null) {
            declaration = iteratorDestructuringContext.accept(iteratorDestructuringVisitor);
        } else if (variableInitializeContext != null) {
            declaration = variableInitializeContext.accept(variableInitializationVisitor);
        } else {
            // ToDo: parser error
            System.err.println("Parser error.");
            System.exit(1);
        }

        return new VariableDeclaration(mutability, declaration);
    }
}
