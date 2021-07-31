package org.imp.jvm.parsing.visitor;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.types.TypeResolver;

import java.util.ArrayList;
import java.util.List;

public class ArgumentsVisitor extends ImpParserBaseVisitor<List<Identifier>> {

    public final Scope scope;

    public ArgumentsVisitor(Scope scope) {
        this.scope = scope;
    }

    @Override
    public List<Identifier> visitArguments(ImpParser.ArgumentsContext ctx) {
        var argumentsCtx = ctx.argument();

        var arguments = new ArrayList<Identifier>();
        for (var argCtx : argumentsCtx) {
            var identifier = new Identifier();
            identifier.name = argCtx.identifier().getText();
            identifier.type = TypeResolver.getFromTypeContext(argCtx.type(), scope);
            arguments.add(identifier);
        }

        return arguments;
    }
}
