package org.imp.jvm;

import org.imp.jvm.tokenizer.Token;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public interface Stmt extends Node {
    <R> R accept(Visitor<R> visitor);


    interface Visitor<R> {
        R visit(Stmt stmt);

        R visitBlockStmt(Block stmt);

        R visitExport(Export stmt);

        R visitEnum(Enum stmt);

        R visitStruct(Struct stmt);

        R visitExpressionStmt(ExpressionStmt stmt);

        R visitFunctionStmt(Function stmt);

        R visitIf(If stmt);

        R visitReturnStmt(Return stmt);

        R visitVariable(Variable stmt);

        R visitParameterStmt(Parameter stmt);

        R visitFor(For stmt);

        R visitForInCondition(ForInCondition stmt);

        R visitTypeAlias(TypeAlias stmt);
    }

    default List<Node> list(Stmt... stmts) {
        List<Node> list = new ArrayList<>();
        Collections.addAll(list, stmts);
        return list;
    }

    default List<Node> list(List<Stmt> stmts) {
        return new ArrayList<>(stmts);
    }

    record Enum(Token name, List<Token> values) implements Stmt {
        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitEnum(this);
        }

        @Override
        public List<Node> children() {
            return null;
        }
    }

    record Struct(Token name, List<Parameter> fields) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitStruct(this);
        }

        @Override
        public List<Node> children() {
            return list();
        }
    }

    record Block(List<Stmt> statements, Environment environment) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitBlockStmt(this);
        }


        @Override
        public List<Node> children() {
            return list(statements);
        }
    }

    record Export(Stmt stmt) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitExport(this);
        }

        @Override
        public List<Node> children() {
            return list(stmt);
        }
    }

    record ExpressionStmt(Expr expr) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitExpressionStmt(this);
        }

        @Override
        public List<Node> children() {
            return list();
        }
    }

    record TypeAlias(Token name, Expr.Literal literal) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitTypeAlias(this);
        }

        @Override
        public List<Node> children() {
            return list();
        }
    }

    record Function(Token name, List<Parameter> parameters, Token returnType, Block body) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitFunctionStmt(this);
        }

        @Override
        public List<Node> children() {
            return list();
        }
    }

    record Parameter(Token name, Token type, boolean listType) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitParameterStmt(this);
        }

        @Override
        public List<Node> children() {
            return list();
        }
    }

    record If(Expr condition, Stmt.Block trueBlock, Stmt falseStmt) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitIf(this);
        }

        @Override
        public List<Node> children() {
            return list(trueBlock, falseStmt);
        }
    }

    record Return(Expr expr) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitReturnStmt(this);
        }

        @Override
        public List<Node> children() {
            return list();
        }
    }

    record Variable(Token mutability, Token name, Expr expr) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitVariable(this);
        }

        @Override
        public List<Node> children() {
            return list();
        }
    }


    record For(ForCondition condition, Block block) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitFor(this);
        }

        @Override
        public List<Node> children() {
            return list(block);
        }
    }


    interface ForCondition extends Stmt {
    }


    record ForInCondition(Token name, Expr expr) implements ForCondition {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitForInCondition(this);
        }

        @Override
        public List<Node> children() {
            return list();
        }
    }

}
