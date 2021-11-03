package org.imp.jvm;

import org.imp.jvm.tokenizer.Token;

import java.util.List;
import java.util.Set;

public interface Expr {
    interface Visitor<R> {
        R visitAssignExpr(Assign expr);

        R visitBinaryExpr(Binary expr);

        R visitGroupingExpr(Grouping expr);

        R visitIdentifierExpr(Identifier expr);

        R visitLogicalExpr(Logical expr);

        R visitLiteralExpr(Literal expr);

        R visitUnaryExpr(Unary expr);

        R visitPostfixExpr(Postfix expr);
    }

    abstract <R> R accept(Visitor<R> visitor);

    // expression operator expression
    record Assign(Expr left, Expr right) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitAssignExpr(this);
        }
    }

    // expression operator expression
    record Binary(Expr left, Token operator, Expr right) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitBinaryExpr(this);
        }
    }

    record Call(String name, Token lparen, List<Expr> arguments, Token rparan) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return null;
        }
    }

    // LPAREN expression RPAREN
    record Grouping(Expr expr) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitGroupingExpr(this);
        }
    }

    // expression comparison expression
    record Logical(Expr left, Token comparison, Expr right) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitLogicalExpr(this);
        }
    }

    // literal number, boolean or string
    record Literal(Token literal) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitLiteralExpr(this);
        }
    }


    // identifier
    record Identifier(Token identifier) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitIdentifierExpr(this);
        }
    }

    // operator expression
    record Unary(Token operator, Expr right) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitUnaryExpr(this);
        }
    }

    // expression operator
    record Postfix(Expr expr, Token operator) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitPostfixExpr(this);
        }
    }


}
