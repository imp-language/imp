package org.imp.jvm;

import org.imp.jvm.tokenizer.Token;
import org.imp.jvm.typechecker.Entity;
import org.imp.jvm.types.Type;

import java.util.List;

public interface Expr {
    interface Visitor<R> {
        R visitAssignExpr(Assign expr);

        R visitBinaryExpr(Binary expr);

        R visitBad(Bad expr);

        R visitCall(Call expr);

        R visitGroupingExpr(Grouping expr);

        R visitIdentifierExpr(Identifier expr);

        R visitIndexAccess(IndexAccess expr);

        R visitLogicalExpr(Logical expr);

        R visitLiteralExpr(Literal expr);

        R visitLiteralList(LiteralList expr);

        R visitNew(New expr);

        R visitPrefix(Prefix expr);


        R visitPostfixExpr(Postfix expr);



        R visitPropertyAccess(PropertyAccess expr);

    }

    <R> R accept(Visitor<R> visitor);


    // error
    record Bad(Entity entity, Token... badTokens) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitBad(this);
        }
    }

    // expression operator expression
    record Assign(Entity entity, Expr left, Expr right) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitAssignExpr(this);
        }
    }


    // expression operator expression
    record Binary(Entity entity, Expr left, Token operator, Expr right) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitBinaryExpr(this);
        }
    }

    record Call(Entity entity, Expr item, List<Expr> arguments) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitCall(this);
        }
    }

    // LPAREN expression RPAREN
    record Grouping(Entity entity, Expr expr) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitGroupingExpr(this);
        }
    }

    // expression comparison expression
    record Logical(Entity entity, Expr left, Token comparison, Expr right) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitLogicalExpr(this);
        }
    }

    // literal number, boolean or string
    record Literal(Entity entity, Token literal) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitLiteralExpr(this);
        }
    }

    // literal number, boolean or string
    record LiteralList(Entity entity, List<Expr> entries) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitLiteralList(this);
        }
    }


    // identifier
    record Identifier(Entity entity, Token identifier) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitIdentifierExpr(this);
        }
    }

    // operator expression
    record Prefix(Entity entity, Token operator, Expr right) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitPrefix(this);
        }
    }

    // expression operator
    record Postfix(Entity entity, Expr expr, Token operator) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitPostfixExpr(this);
        }
    }


    // expression operator
    record New(Entity entity, Expr call) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitNew(this);
        }
    }

    // expression . expression
    record PropertyAccess(Entity entity, Expr left, Expr right) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitPropertyAccess(this);
        }
    }

    // expression[expression]
    record IndexAccess(Entity entity, Expr left, Expr right) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitIndexAccess(this);
        }
    }


}
