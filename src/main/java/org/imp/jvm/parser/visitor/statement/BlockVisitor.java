package org.imp.jvm.parser.visitor.statement;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.statement.Block;
import org.imp.jvm.domain.statement.Statement;

import java.util.List;
import java.util.stream.Collectors;

public class BlockVisitor extends ImpParserBaseVisitor<Block> {
    private final Scope scope;

    public BlockVisitor(Scope scope) {
        this.scope = scope;
    }

    @Override
    public Block visitBlock(ImpParser.BlockContext ctx) {
        if (ctx.statementList() != null) {
            List<ImpParser.StatementContext> blockStatementsCtx = ctx.statementList().statement();
//        Scope newScope = new Scope(scope);
            Scope newScope = new Scope();
            StatementVisitor statementVisitor = new StatementVisitor(newScope);
            List<Statement> statements = blockStatementsCtx.stream().map(smtt -> smtt.accept(statementVisitor)).collect(Collectors.toList());
            return new Block(statements, newScope);
        }
        return new Block();

    }


}
