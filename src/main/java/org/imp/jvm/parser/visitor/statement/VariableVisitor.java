package org.imp.jvm.parser.visitor.statement;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.domain.statement.VariableDeclaration;
import org.imp.jvm.domain.statement.variable.Declaration;
import org.imp.jvm.domain.types.Mutability;
import org.imp.jvm.parser.visitor.statement.variable.IteratorDestructuringVisitor;
import org.imp.jvm.parser.visitor.statement.variable.VariableInitializationVisitor;

public class VariableVisitor extends ImpParserBaseVisitor<VariableDeclaration> {

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
            declaration = iteratorDestructuringContext.accept(new IteratorDestructuringVisitor());
        } else if (variableInitializeContext != null) {
            declaration = variableInitializeContext.accept(new VariableInitializationVisitor());
        } else {
            // ToDo: parser error
            System.err.println("Parser error.");
            System.exit(1);
        }

        return new VariableDeclaration(mutability, declaration);
    }
}
