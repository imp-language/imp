package org.imp.jvm.parser;

import org.imp.jvm.Environment;
import org.imp.jvm.parser.tokenizer.Location;
import org.imp.jvm.parser.tokenizer.Token;

import java.util.List;
import java.util.Optional;

public abstract class Stmt implements Node {
    public final Location location;
    public boolean inMain = false;

    protected Stmt(Location location) {
        this.location = location;
    }

    public abstract <R> R accept(Visitor<R> visitor);

    @Override
    public Location location() {
        return location;
    }

    public interface Visitor<R> {
        R visit(Stmt stmt);

        R visitBlockStmt(Block stmt);

        R visitEnum(Enum stmt);

        R visitExport(Export stmt);

        R visitExpressionStmt(ExpressionStmt stmt);

        R visitFor(For stmt);


        R visitFunctionStmt(Function stmt);

        R visitIf(If stmt);

        R visitImport(Import stmt);

        R visitParameterStmt(Parameter stmt);

        R visitReturnStmt(Return stmt);

        R visitStruct(Struct stmt);

        R visitType(Type stmt);

        R visitTypeAlias(TypeAlias stmt);

        R visitVariable(Variable stmt);
    }

    public interface Exportable {
        String identifier();
    }

    // Maybe remove quotes from imports?
    public static final class Import extends Stmt {
        public final Token stringLiteral;
        public final Optional<Token> identifier;

        public Import(Location loc, Token stringLiteral, Optional<Token> identifier) {
            super(loc);
            this.stringLiteral = stringLiteral;
            this.identifier = identifier;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitImport(this);
        }
    }

    public static final class Enum extends Stmt implements Exportable {
        public final Token name;
        public final List<Token> values;

        public Enum(Location loc, Token name, List<Token> values) {
            super(loc);
            this.name = name;
            this.values = values;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitEnum(this);
        }


        @Override
        public String identifier() {
            return name.source();
        }
    }

    public static final class Struct extends Stmt implements Exportable {
        public final Token name;
        public final List<Parameter> fields;

        public Struct(Location loc, Token name, List<Parameter> fields) {
            super(loc);
            this.name = name;
            this.fields = fields;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitStruct(this);
        }


        @Override
        public String identifier() {
            return name.source();
        }
    }

    public static final class Block extends Stmt {
        public final List<Stmt> statements;
        public final Environment environment;

        public Block(Location loc, List<Stmt> statements, Environment environment) {
            super(loc);
            this.statements = statements;
            this.environment = environment;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitBlockStmt(this);
        }


    }

    public static final class Export extends Stmt {
        public final Stmt stmt;

        public Export(Location loc, Stmt stmt) {
            super(loc);
            this.stmt = stmt;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitExport(this);
        }


    }

    public static final class ExpressionStmt extends Stmt {
        public final Expr expr;

        public ExpressionStmt(Location loc, Expr expr) {
            super(loc);
            this.expr = expr;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitExpressionStmt(this);
        }

    }

    public static final class TypeAlias extends Stmt implements Exportable {
        public final Token name;
        public final Expr.Literal literal;

        public TypeAlias(Location loc, Token name, Expr.Literal literal) {
            super(loc);
            this.name = name;
            this.literal = literal;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitTypeAlias(this);
        }

        @Override
        public String identifier() {
            return name.source();
        }
    }

    public static final class Function extends Stmt implements Exportable {
        public final Token name;
        public final Block body;
        public final List<Parameter> parameters;
        public final Token returnType;

        public Function(Location loc, Token name, List<Parameter> parameters, Token returnType,
                        Block body) {
            super(loc);
            this.name = name;
            this.parameters = parameters;
            this.returnType = returnType;
            this.body = body;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitFunctionStmt(this);
        }

        @Override
        public String identifier() {
            return name.source();
        }

    }

    public static final class Parameter extends Stmt {
        public final Token name;
        public final Type type;
        public final boolean listType;

        public Parameter(Location loc, Token name, Type type, boolean listType) {
            super(loc);
            this.name = name;
            this.type = type;
            this.listType = listType;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitParameterStmt(this);
        }


    }

    public static final class Type extends Stmt {
        public final Token identifier;
        public final Optional<Type> next;
        public final boolean listType;

        public Type(Location loc, Token identifier, Optional<Type> next, boolean listType) {
            super(loc);
            this.identifier = identifier;
            this.next = next;
            this.listType = listType;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitType(this);
        }

    }

    public static final class If extends Stmt {
        public final Block trueBlock;
        public final Stmt falseStmt;
        public final Expr condition;

        public If(Location loc, Expr condition, Block trueBlock, Stmt falseStmt) {
            super(loc);
            this.condition = condition;
            this.trueBlock = trueBlock;
            this.falseStmt = falseStmt;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitIf(this);
        }


    }

    public static final class Return extends Stmt {
        public final Expr expr;

        public Return(Location loc, Expr expr) {
            super(loc);
            this.expr = expr;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitReturnStmt(this);
        }


    }

    public static final class Variable extends Stmt implements Exportable {
        public final Token name;
        public final Expr expr;
        public final Token mutability;

        public int localIndex = -1;

        public Variable(Location loc, Token mutability, Token name, Expr expr) {
            super(loc);
            this.mutability = mutability;
            this.name = name;
            this.expr = expr;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitVariable(this);
        }


        @Override
        public String identifier() {
            return name.source();
        }
    }

    public static final class For extends Stmt {
        public final Block block;
        public final Token name;
        public final Expr expr;
        public int localNameIndex = -1;
        public int localExprIndex = -1;


        public For(Location loc, Token name, Expr expr, Block block) {
            super(loc);
            this.block = block;
            this.name = name;
            this.expr = expr;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitFor(this);
        }

    }


}
