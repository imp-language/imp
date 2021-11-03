package org.imp.jvm;

import org.imp.jvm.statement.TypeAlias;
import org.imp.jvm.tokenizer.Token;

import java.util.List;

public interface Stmt {
    <R> R accept(Visitor<R> visitor);

    interface Visitor<R> {

        R visitBlockStmt(Block stmt);


        R visitExpressionStmt(Expression stmt);

        R visitFunctionStmt(Function stmt);

        R visitIfStmt(If stmt);

        R visitReturnStmt(Return stmt);

        R visitDeclarationStmt(Declaration stmt);

        R visitParameterStmt(Parameter stmt);

        R visitForInLoop(ForInLoop stmt);

        R visitForLoop(ForLoop stmt);

        R visitTypeAlias(TypeAlias stmt);
    }

    record Block(List<Stmt> statements) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitBlockStmt(this);
        }
    }

    record Expression(Expr expr) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitExpressionStmt(this);
        }
    }

    record TypeAlias(Token name, Expr.Literal literal) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitTypeAlias(this);
        }
    }

    record Function(Token modifier, Token name, List<Parameter> parameters) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitFunctionStmt(this);
        }
    }

    record Parameter(Token name, Token type) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitParameterStmt(this);
        }
    }

    record If(Expr condition, Stmt trueStmt, Stmt falseStmt) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitIfStmt(this);
        }
    }

    record Return(Expr expr) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitReturnStmt(this);
        }
    }

    record Declaration(Token mutability, Token name, Expr expr) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitDeclarationStmt(this);
        }
    }


    record ForInLoop(Token iteratorName, Expression iteratorSource, Block body) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitForInLoop(this);
        }
    }

    record ForLoop(Declaration declaration, Expression condition, Expr incrementer, Block body) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitForLoop(this);
        }
    }


}
