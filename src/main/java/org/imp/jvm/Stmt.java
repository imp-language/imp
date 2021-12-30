package org.imp.jvm;

import org.imp.jvm.tokenizer.Token;
import org.imp.jvm.typechecker.Location;

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

    record Enum(Location loc, Token name, List<Token> values) implements Stmt {
        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitEnum(this);
        }

        @Override
        public Location location() {
            return loc();
        }
    }

    record Struct(Location loc, Token name, List<Parameter> fields) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitStruct(this);
        }

        @Override
        public Location location() {
            return loc();
        }
    }

    record Block(Location loc, List<Stmt> statements, Environment environment) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitBlockStmt(this);
        }

        @Override
        public Location location() {
            return loc();
        }

    }

    record Export(Location loc, Stmt stmt) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitExport(this);
        }

        @Override
        public Location location() {
            return loc();
        }
    }

    record ExpressionStmt(Location loc, Expr expr) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitExpressionStmt(this);
        }

        @Override
        public Location location() {
            return loc();
        }
    }

    record TypeAlias(Location loc, Token name, Expr.Literal literal) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitTypeAlias(this);
        }

        @Override
        public Location location() {
            return loc();
        }
    }

    record Function(Location loc, Token name, List<Parameter> parameters, Token returnType,
                    Block body) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitFunctionStmt(this);
        }

        @Override
        public Location location() {
            return loc();
        }
    }

    record Parameter(Location loc, Token name, Token type, boolean listType) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitParameterStmt(this);
        }

        @Override
        public Location location() {
            return loc();
        }
    }

    record If(Location loc, Expr condition, Stmt.Block trueBlock, Stmt falseStmt) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitIf(this);
        }

        @Override
        public Location location() {
            return loc();
        }
    }

    record Return(Location loc, Expr expr) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitReturnStmt(this);
        }

        @Override
        public Location location() {
            return loc();
        }
    }

    record Variable(Location loc, Token mutability, Token name, Expr expr) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitVariable(this);
        }

        @Override
        public Location location() {
            return loc();
        }
    }


    record For(Location loc, ForCondition condition, Block block) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitFor(this);
        }

        @Override
        public Location location() {
            return loc();
        }
    }


    interface ForCondition extends Stmt {
    }


    record ForInCondition(Location loc, Token name, Expr expr) implements ForCondition {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitForInCondition(this);
        }

        @Override
        public Location location() {
            return loc();
        }
    }

}
