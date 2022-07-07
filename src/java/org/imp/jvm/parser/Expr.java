package org.imp.jvm.parser;

import org.imp.jvm.parser.tokenizer.Location;
import org.imp.jvm.parser.tokenizer.Token;
import org.imp.jvm.types.ImpType;
import org.imp.jvm.types.UnknownType;

import java.util.ArrayList;
import java.util.List;

public abstract class Expr implements Node {
    public final Location location;
    public ImpType realType = new UnknownType("unknown");

    protected Expr(Location location) {
        this.location = location;
    }

    public abstract <R> R accept(Visitor<R> visitor);

    @Override
    public Location location() {
        return location;
    }

    public interface Visitor<R> {
        R visitAssignExpr(Assign expr);

        R visitBad(Bad expr);

        R visitBinaryExpr(Binary expr);

        R visitCall(Call expr);

        R visitEmpty(Empty empty);

        R visitEmptyList(EmptyList emptyList);

        R visitGroupingExpr(Grouping expr);

        R visitIdentifierExpr(Identifier expr);

        R visitIndexAccess(IndexAccess expr);

        R visitLiteralExpr(Literal expr);

        R visitLiteralList(LiteralList expr);


        R visitPostfixExpr(Postfix expr);

        R visitPrefix(Prefix expr);

        R visitPropertyAccess(PropertyAccess expr);

    }

    public static final class Empty extends Expr {

        public Empty(Location location) {
            super(location);
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitEmpty(this);
        }
    }

    // error
    public static final class Bad extends Expr {

        public final Token[] badTokens;

        public Bad(Location loc, Token... badTokens) {
            super(loc);
            this.badTokens = badTokens;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitBad(this);
        }


    }

    // expression operator expression
    public static final class Assign extends Expr {

        public final Expr left;
        public final Expr right;

        public Assign(Location loc, Expr left, Expr right) {
            super(loc);
            this.left = left;
            this.right = right;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitAssignExpr(this);
        }


    }

    // expression operator expression
    public static final class Binary extends Expr {

        public final Expr left;
        public final Token operator;
        public final Expr right;

        public Binary(Location loc, Expr left, Token operator, Expr right) {
            super(loc);
            this.left = left;
            this.operator = operator;
            this.right = right;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitBinaryExpr(this);
        }


    }

    public static final class Call extends Expr {

        public final Expr item;
        public final List<Expr> arguments;

        public Call(Location loc, Expr item, List<Expr> arguments) {
            super(loc);
            this.item = item;
            this.arguments = arguments;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitCall(this);
        }


    }

    // LPAREN expression RPAREN
    public static final class Grouping extends Expr {

        public final Expr expr;

        public Grouping(Location loc, Expr expr) {
            super(loc);
            this.expr = expr;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitGroupingExpr(this);
        }


    }


    // literal number, boolean or string
    public static final class Literal extends Expr {

        public final Token literal;

        public Literal(Location loc, Token literal) {
            super(loc);
            this.literal = literal;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitLiteralExpr(this);
        }


    }

    // literal number, boolean or string
    public static final class LiteralList extends Expr {

        public final List<Expr> entries;

        public LiteralList(Location loc, List<Expr> entries) {
            super(loc);
            this.entries = entries;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitLiteralList(this);
        }


    }

    // operator expression
    public static final class Prefix extends Expr {

        public final Token operator;
        public final Expr right;

        public Prefix(Location location, Token operator, Expr right) {
            super(location);
            this.operator = operator;
            this.right = right;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitPrefix(this);
        }


    }

    // expression operator
    public static final class Postfix extends Expr {

        public final Expr expr;
        public final Token operator;
        public int localIndex = -1;

        public Postfix(Location location, Expr expr, Token operator) {
            super(location);
            this.expr = expr;
            this.operator = operator;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitPostfixExpr(this);
        }


    }

    // expression . expression
    public static final class PropertyAccess extends Expr {
        public final Expr expr;
        public final List<Identifier> identifiers;
        public List<ImpType> typeChain = new ArrayList<>();

        public PropertyAccess(Location location, Expr expr, List<Identifier> identifiers) {
            super(location);
            this.expr = expr;
            this.identifiers = identifiers;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitPropertyAccess(this);
        }
    }

    // expression[expression]
    public static class IndexAccess extends Expr {
        public final Expr left;
        public final Expr right;

        public IndexAccess(Location location, Expr left, Expr right) {
            super(location);
            this.left = left;
            this.right = right;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitIndexAccess(this);
        }
    }

    public static class EmptyList extends Expr {
        public final Token tokenType;

        public EmptyList(Location loc, Token tokenType) {
            super(loc);
            this.tokenType = tokenType;
        }

        @Override
        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitEmptyList(this);
        }

    }

    // identifier
    public static class Identifier extends Expr {
        public final Token identifier;

        public Identifier(Location location, Token identifier) {
            super(location);
            this.identifier = identifier;
        }

        public <R> R accept(Visitor<R> visitor) {
            return visitor.visitIdentifierExpr(this);
        }
    }


}
