package org.imp.jvm.parser;

import org.imp.jvm.Expr;

public class ASTPrinter implements Expr.Visitor<String> {
    String print(Expr expr) {
        return expr.accept(this);
    }

    private String parenthesize(String name, Expr... exprs) {
        StringBuilder builder = new StringBuilder();

        builder.append("(").append(name);
        for (Expr expr : exprs) {
            builder.append(" ");
            builder.append(expr.accept(this));
        }
        builder.append(")");

        return builder.toString();
    }


    @Override
    public String visitAssignExpr(Expr.Assign expr) {
        return parenthesize("=", expr.left(), expr.right());
    }

    @Override
    public String visitBinaryExpr(Expr.Binary expr) {
        return parenthesize(expr.operator().representation(), expr.left(), expr.right());
    }

    @Override
    public String visitGroupingExpr(Expr.Grouping expr) {
        return parenthesize("group", expr.expr());
    }

    @Override
    public String visitIdentifierExpr(Expr.Identifier expr) {
        return null;
    }

    @Override
    public String visitLogicalExpr(Expr.Logical expr) {
        return null;
    }

    @Override
    public String visitLiteralExpr(Expr.Literal expr) {
        return expr.literal().source();
    }

    @Override
    public String visitUnaryExpr(Expr.Unary expr) {
        return null;
    }

    @Override
    public String visitPostfixExpr(Expr.Postfix expr) {
        return null;
    }
}
