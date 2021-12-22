package org.imp.jvm;

import org.imp.jvm.tokenizer.Token;
import org.imp.jvm.typechecker.Annotation;

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

    record EmptyList(Token type) implements Expr {

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitEmptyList(this);
        }

        @Override
        public List<Node> children() {
            return null;
        }
    }


    // error
    record Bad(Annotation annotation, Token... badTokens) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitBad(this);
        }

        @Override
        public List<Node> children() {
            return list();
        }
    }

    // expression operator expression
    record Assign(Annotation annotation, Expr left, Expr right) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitAssignExpr(this);
        }

        @Override
        public List<Node> children() {
            return list(left, right);
        }
    }


    // expression operator expression
    record Binary(Annotation annotation, Expr left, Token operator, Expr right) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitBinaryExpr(this);
        }

        @Override
        public List<Node> children() {
            return list();
        }
    }

    record Call(Annotation annotation, Expr item, List<Expr> arguments) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitCall(this);
        }

        @Override
        public List<Node> children() {
            return list();
        }
    }

    // LPAREN expression RPAREN
    record Grouping(Annotation annotation, Expr expr) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitGroupingExpr(this);
        }

        @Override
        public List<Node> children() {
            return list();
        }
    }

    // expression comparison expression
    record Logical(Annotation annotation, Expr left, Token comparison, Expr right) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitLogicalExpr(this);
        }

        @Override
        public List<Node> children() {
            return list();
        }
    }

    // literal number, boolean or string
    record Literal(Annotation annotation, Token literal) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitLiteralExpr(this);
        }

        @Override
        public List<Node> children() {
            return list();
        }
    }

    // literal number, boolean or string
    record LiteralList(Annotation annotation, List<Expr> entries) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitLiteralList(this);
        }


        @Override
        public List<Node> children() {
            return list();
        }
    }


    // identifier
    record Identifier(Annotation annotation, Token identifier) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitIdentifierExpr(this);
        }

        @Override
        public List<Node> children() {
            return list();
        }
    }

    // operator expression
    record Prefix(Annotation annotation, Token operator, Expr right) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitPrefix(this);
        }

        @Override
        public List<Node> children() {
            return list();
        }
    }

    // expression operator
    record Postfix(Annotation annotation, Expr expr, Token operator) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitPostfixExpr(this);
        }

        @Override
        public List<Node> children() {
            return list();
        }
    }


    // expression operator
    record New(Annotation annotation, Expr call) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitNew(this);
        }

        @Override
        public List<Node> children() {
            return list();
        }
    }

    // expression . expression
    record PropertyAccess(Annotation annotation, Expr left, Expr right) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitPropertyAccess(this);
        }

        @Override
        public List<Node> children() {
            return list();
        }
    }

    // expression[expression]
    record IndexAccess(Annotation annotation, Expr left, Expr right) implements Expr {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitIndexAccess(this);
        }

        @Override
        public List<Node> children() {
            return list();
        }
    }

    // expression ... expression
    record Range(Annotation annotation, Expr left, Expr right) implements Expr {

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitRange(this);
        }

        @Override
        public List<Node> children() {
            return list(left, right);
        }
    }


}
