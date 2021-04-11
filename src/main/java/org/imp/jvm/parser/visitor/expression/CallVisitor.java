package org.imp.jvm.parser.visitor.expression;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.domain.expression.Call;
import org.imp.jvm.domain.expression.Expression;
import org.imp.jvm.domain.expression.FunctionCall;
import org.imp.jvm.domain.expression.Literal;
import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.types.Type;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CallVisitor extends ImpParserBaseVisitor<Call> {
    private final Scope scope;
    private final ExpressionVisitor expressionVisitor;

    public CallVisitor(Scope scope, ExpressionVisitor expressionVisitor) {
        this.scope = scope;
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public Call visitCallStatementExpression(ImpParser.CallStatementExpressionContext ctx) {
        ImpParser.CallStatementContext callCtx = ctx.callStatement();
        String functionName = callCtx.identifier().getText();
        var arguments = callCtx.expressionList().expression();
        // Todo: error handling for nonexistent function signatures

        var argTypes = getArgumentsForCall(arguments);

//        var signature = new FunctionSignature(functionName, parameters)
        FunctionSignature signature = scope.getSignature(functionName, argTypes);

        return new FunctionCall(signature, null);
    }

    private List<Identifier> getArgumentsForCall(List<ImpParser.ExpressionContext> argumentsListCtx) {
        if (argumentsListCtx != null) {
            return argumentsListCtx.stream()
                    .map(arg -> arg.accept(expressionVisitor).getType())
                    .map(argType -> {
                        var ident = new Identifier();
                        ident.type = argType;
                        return ident;
                    })
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
