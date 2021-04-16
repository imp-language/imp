package org.imp.jvm.parsing.visitor.expression;

import org.imp.jvm.ImpParser;
import org.imp.jvm.ImpParserBaseVisitor;
import org.imp.jvm.domain.CompareSign;
import org.imp.jvm.domain.expression.Call;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.expression.*;
import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.LocalVariable;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.types.BuiltInType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ExpressionVisitor extends ImpParserBaseVisitor<Expression> {
    //    private final IdentifierReferenceVisitor identifierReferenceVisitor;
    private final LiteralVisitor literalVisitor;
    //    private final UnaryNotVisitor unaryNotVisitor;
//
//    private final UnaryAdditiveVisitor unaryAdditiveVisitor;
//    private final PowerVisitor powerVisitor;
//    private final MultiplicativeVisitor multiplicativeVisitor;
    private final ArithmeticVisitor arithmeticVisitor;
//    private final RelationalVisitor relationalVisitor;
//    private final LogicalVisitor logicalVisitor;
//    private final PropertyAccessVisitor propertyAccessVisitor;
//    private final MethodCallVisitor methodCallVisitor;
//    private final PostIncrementVisitor postIncrementVisitor;
//    private final CallVisitor callStatementVisitor;
//    private final NewObjectVisitor newObjectVisitor;
//    private final MemberIndexVisitor memberIndexVisitor;

    private final Scope scope;


    public ExpressionVisitor(Scope scope) {
        this.scope = scope;
//        identifierReferenceVisitor = new IdentifierReferenceVisitor(scope);
        literalVisitor = new LiteralVisitor(this);
//        unaryNotVisitor = new UnaryNotVisitor();
//        unaryAdditiveVisitor = new UnaryAdditiveVisitor();
//        powerVisitor = new PowerVisitor();
//        multiplicativeVisitor = new MultiplicativeVisitor();
        arithmeticVisitor = new ArithmeticVisitor(this);
//        relationalVisitor = new RelationalVisitor(this);
//        logicalVisitor = new LogicalVisitor();
//        propertyAccessVisitor = new PropertyAccessVisitor();
//        methodCallVisitor = new MethodCallVisitor();
//        postIncrementVisitor = new PostIncrementVisitor();
//        callStatementVisitor = new CallVisitor(scope, this);
//        newObjectVisitor = new NewObjectVisitor();
//        memberIndexVisitor = new MemberIndexVisitor();
    }

    @Override
    public IdentifierReference visitIdentifierReferenceExpression(ImpParser.IdentifierReferenceExpressionContext ctx) {
        String name = ctx.getText();
        if (scope.variableExists(name)) {

        }
        // ToDo: reference type parsing
        LocalVariable localVariable = new LocalVariable(name, BuiltInType.INT);
        return new IdentifierReference(localVariable);
    }


    @Override
    public Literal visitLiteral(ImpParser.LiteralContext ctx) {
        return literalVisitor.visitLiteral(ctx);
    }

    @Override
    public Expression visitUnaryNotExpression(ImpParser.UnaryNotExpressionContext ctx) {
        return super.visitUnaryNotExpression(ctx);
    }


    @Override
    public Relational visitRelationalExpression(ImpParser.RelationalExpressionContext ctx) {
        Expression left = ctx.expression(0).accept(this);
        Expression right = ctx.expression(1).accept(this);
        // ToDo: operator overloading for comparison
        CompareSign compareSign = CompareSign.fromString(ctx.cmp.getText());
        return new Relational(left, right, compareSign);
    }


    @Override
    public FunctionCall visitCallStatementExpression(ImpParser.CallStatementExpressionContext ctx) {
        ImpParser.CallStatementContext callCtx = ctx.callStatement();

        // Identifier
        String functionName = callCtx.identifier().getText();

        // Todo: error handling for nonexistent function signatures
        // Function Signature
        var arguments = callCtx.expressionList().expression();
        var argTypes = getArgumentsForCall(arguments);
        FunctionSignature signature;
        if (functionName.equals("call")) {
            signature = new FunctionSignature("log", null, null);
        } else {
            signature = scope.getSignature(functionName, argTypes);
        }

        // Visit Arguments
        List<Expression> visited = new ArrayList<>();
        for (var arg : arguments) {
            visited.add(arg.accept(this));
        }

        return new FunctionCall(signature, visited);
    }

    private List<Identifier> getArgumentsForCall(List<ImpParser.ExpressionContext> argumentsListCtx) {
        if (argumentsListCtx != null) {
            return argumentsListCtx.stream()
                    .map(arg -> arg.accept(this).type)
                    .map(argType -> {
                        var ident = new Identifier();
                        ident.type = argType;
                        return ident;
                    })
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }


    @Override
    public Arithmetic visitAdditiveExpression(ImpParser.AdditiveExpressionContext ctx) {
        return arithmeticVisitor.visitAdditiveExpression(ctx);
    }
}
