package org.imp.jvm.parsing.visitor.statement;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.LocalVariable;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.statement.Block;
import org.imp.jvm.domain.statement.Function;
import org.imp.jvm.domain.types.BuiltInType;
import org.imp.jvm.domain.types.Type;
import org.imp.jvm.domain.types.TypeResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// ToDo: support anonymous fat arrow functions
public class FunctionVisitor extends ImpParserBaseVisitor<Function> {
    private final Scope scope;

    public FunctionVisitor(Scope scope) {
        this.scope = new Scope(scope);
    }

    @Override
    public Function visitFunctionStatement(ImpParser.FunctionStatementContext ctx) {
        // Identifier
        String name = ctx.identifier().getText();

        // Block
        BlockVisitor blockVisitor = new BlockVisitor(scope);
        ImpParser.BlockContext blockContext = ctx.block();
        Block block = Optional.ofNullable(blockContext.accept(blockVisitor)).orElse(new Block());

        // Arguments
        List<Identifier> arguments = new ArrayList<>();
        ImpParser.ArgumentsContext argumentsContext = ctx.arguments();
        if (argumentsContext != null) {
            arguments = argumentsContext.accept(new ArgumentsVisitor());
        }
        addParametersAsLocalVariables(arguments);

        // Return type
        ImpParser.TypeContext typeContext = ctx.type();
        Type returnType = BuiltInType.VOID;
        if (typeContext != null) {
            // ToDo: parse multiple returns
            returnType = TypeResolver.getFromTypeName(typeContext);
        }


        // Todo: scope.addLocalVariable(new LocalVariable("this",scope.getClassType()));
        // Todo: addParametersAsLocalVariables(signature);
        FunctionSignature signature = new FunctionSignature(name, arguments, returnType);
        scope.addSignature(signature);

        return new Function(name, block, arguments, returnType);
    }

    private void addParametersAsLocalVariables(List<Identifier> parameters) {
        parameters.forEach(param -> scope.addLocalVariable(new LocalVariable(param.name, param.type)));
    }


    class ArgumentsVisitor extends ImpParserBaseVisitor<List<Identifier>> {
        @Override
        public List<Identifier> visitArguments(ImpParser.ArgumentsContext ctx) {
            var argumentsCtx = ctx.argument();

            var arguments = new ArrayList<Identifier>();
            for (var argCtx : argumentsCtx) {
                var identifier = new Identifier();
                identifier.name = argCtx.identifier().getText();
                identifier.type = TypeResolver.getFromTypeName(argCtx.type());
                arguments.add(identifier);
            }

            return arguments;
        }
    }
}


