package org.imp.jvm;

import org.imp.jvm.tokenizer.Token;
import org.imp.jvm.typechecker.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface Expr extends Node {
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

        R visitRange(Range range);

        R visitEmptyList(EmptyList emptyList);
    }

    <R> R accept(Visitor<R> visitor);

    default List<Node> list(Expr... exprs) {
        List<Node> list = new ArrayList<>();
        Collections.addAll(list, exprs);
        return list;
    }

    default List<Node> list(List<Expr> exprs) {
        return new ArrayList<>(exprs);
    }

    record EmptyList(Location loc, Token type) implements Expr {

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitEmptyList(this);
        }

        @Override
        public Location location() {
            return loc();
        }
    }


    // error
    record Bad(Location loc, Token... badTokens) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitBad(this);
        }

        @Override
        public Location location() {
            return loc();
        }
    }

    // expression operator expression
    record Assign(Location loc, Expr left, Expr right) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitAssignExpr(this);
        }

        @Override
        public Location location() {
            return loc();
        }
    }


    // expression operator expression
    record Binary(Location loc, Expr left, Token operator, Expr right) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitBinaryExpr(this);
        }

        @Override
        public Location location() {
            return loc();
        }
    }

    record Call(Location loc, Expr item, List<Expr> arguments) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitCall(this);
        }

        @Override
        public Location location() {
            return loc();
        }
    }

    // LPAREN expression RPAREN
    record Grouping(Location loc, Expr expr) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitGroupingExpr(this);
        }

        @Override
        public Location location() {
            return loc();
        }
    }

    // expression comparison expression
    record Logical(Location loc, Expr left, Token comparison, Expr right) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitLogicalExpr(this);
        }

        @Override
        public Location location() {
            return loc();
        }
    }

    // literal number, boolean or string
    record Literal(Location loc, Token literal) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitLiteralExpr(this);
        }

        @Override
        public Location location() {
            return loc();
        }
    }

    // literal number, boolean or string
    record LiteralList(Location loc, List<Expr> entries) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitLiteralList(this);
        }

        @Override
        public Location location() {
            return loc();
        }

    }


    // identifier
    record Identifier(Location location, Token identifier) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitIdentifierExpr(this);
        }

    }

    // operator expression
    record Prefix(Location location, Token operator, Expr right) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitPrefix(this);
        }

    }

    // expression operator
    record Postfix(Location location, Expr expr, Token operator) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitPostfixExpr(this);
        }

    }


    // expression operator
    record New(Location location, Expr call) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitNew(this);
        }

    }

    // expression . expression
    record PropertyAccess(Location location, Expr left, Expr right) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitPropertyAccess(this);
        }

    }

    // expression[expression]
    record IndexAccess(Location location, Expr left, Expr right) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitIndexAccess(this);
        }

    }

    // expression ... expression
    record Range(Location location, Expr left, Expr right) implements Expr {

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitRange(this);
        }

    }


}
