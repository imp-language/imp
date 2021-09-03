package org.imp.jvm.parsing.visitor;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.exception.Errors;
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

        try {
            for (var argCtx : argumentsCtx) {
                var identifier = new Identifier();
                identifier.name = argCtx.identifier().getText();
                identifier.type = TypeResolver.getFromTypeContext(argCtx.type(), scope);
                if (identifier.type == null) {
                    throw new Error("reeses");
                }

                arguments.add(identifier);
            }
        } catch (Error err) {
            arguments = new ArrayList<Identifier>();
            Logger.syntaxError(Errors.TypeNotFound, "no filename", ctx, ctx.getStop().getText());

        }


        return arguments;
    }
}
