package org.imp.jvm;

import org.imp.jvm.tokenizer.Token;

import java.util.List;

public interface Stmt {
    <R> R accept(Visitor<R> visitor);

    interface Visitor<R> {

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

    record Enum(Token name, List<Token> values) implements Stmt {
        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitEnum(this);
        }
    }

    record Struct(Token name, List<Parameter> fields) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitStruct(this);
        }
    }

    record Block(List<Stmt> statements) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitBlockStmt(this);
        }
    }

    record Export(Stmt stmt) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitExport(this);
        }
    }

    record ExpressionStmt(Expr expr) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitExpressionStmt(this);
        }
    }

    record TypeAlias(Token name, Expr.Literal literal) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitTypeAlias(this);
        }
    }

    record Function(Token name, List<Parameter> parameters, Token returnType, Block body) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitFunctionStmt(this);
        }
    }

    record Parameter(Token name, Token type, boolean listType) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitParameterStmt(this);
        }
    }

    record If(Expr condition, Stmt trueStmt, Stmt falseStmt) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitIf(this);
        }
    }

    record Return(Expr expr) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitReturnStmt(this);
        }
    }

    record Variable(Token mutability, Token name, Expr expr) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitVariable(this);
        }
    }


    record For(ForCondition condition, Block block) implements Stmt {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitFor(this);
        }
    }


    interface ForCondition extends Stmt {
    }

    record ForInCondition(Token name, Expr expr) implements ForCondition {
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitForInCondition(this);
        }
    }

}
