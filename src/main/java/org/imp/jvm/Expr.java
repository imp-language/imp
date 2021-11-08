package org.imp.jvm;

import org.imp.jvm.tokenizer.Token;

import java.util.List;

public interface Expr {
    interface Visitor<R> {
        R visitAssignExpr(Assign expr);

        R visitBinaryExpr(Binary expr);

        R visitGroupingExpr(Grouping expr);

        R visitIdentifierExpr(Identifier expr);

        R visitLogicalExpr(Logical expr);

        R visitLiteralExpr(Literal expr);

        R visitLiteralList(LiteralList expr);

        R visitPrefix(Prefix expr);

        R visitCall(Call expr);

        R visitPostfixExpr(Postfix expr);

        R visitBad(Bad expr);

        R visitNew(New expr);

        R visitPropertyAccess(PropertyAccess expr);

        R visitIndexAccess(IndexAccess expr);
    }

    <R> R accept(Visitor<R> visitor);

    // error
    record Bad(Token... badTokens) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitBad(this);
        }
    }

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

    record Call(Expr item, List<Expr> arguments) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitCall(this);
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

    // literal number, boolean or string
    record LiteralList(List<Expr> entries) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitLiteralList(this);
        }
    }


    // identifier
    record Identifier(Token identifier) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitIdentifierExpr(this);
        }
    }

    // operator expression
    record Prefix(Token operator, Expr right) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitPrefix(this);
        }
    }

    // expression operator
    record Postfix(Expr expr, Token operator) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitPostfixExpr(this);
        }
    }


    // expression operator
    record New(Expr call) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitNew(this);
        }
    }

    // expression . expression
    record PropertyAccess(Expr left, Expr right) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitPropertyAccess(this);
        }
    }

    // expression[expression]
    record IndexAccess(Expr left, Expr right) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitIndexAccess(this);
        }
    }


}
