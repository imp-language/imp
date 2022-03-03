package org.imp.jvm.parser;

import org.imp.jvm.tokenizer.Token;
import org.imp.jvm.tokenizer.TokenType;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic infix parselet for a binary arithmetic operator. The only
 * difference when parsing, "+", "-", "*", "/", and "^" is precedence and
 * associativity, so we can use a single parselet class for all of those.
 */
public interface InfixParselet {
    Expr parse(Parser parser, Expr left, Token token);

    int precedence();

    record BinaryOperator(int precedence, boolean isRight) implements InfixParselet {
        public Expr parse(Parser parser, Expr left, Token token) {
            // To handle right-associative operators like "^", we allow a slightly
            // lower precedence when parsing the right-hand side. This will let a
            // parselet with the same precedence appear on the right, which will then
            // take *this* parselet's result as its left-hand argument.
            var loc = parser.lok();
            Expr right = parser.expression(
                    precedence - (isRight ? 1 : 0));
            return new Expr.Binary(loc, left, token, right);
        }
    }

    record PropertyAccess() implements InfixParselet {
        public Expr parse(Parser parser, Expr left, Token token) {
            var loc = parser.lok();
            List<Expr> exprs = new ArrayList<>();
            exprs.add(left);
            Expr right = parser.expression(precedence());
            exprs.add(right);
            while (parser.peek().type() == TokenType.DOT) {
                parser.consume();
                right = parser.expression(precedence());
                exprs.add(right);
            }
            return new Expr.PropertyAccess(loc, exprs);
        }


        @Override
        public int precedence() {
            return Precedence.PREFIX;
        }
    }

    record IndexAccess() implements InfixParselet {
        public Expr parse(Parser parser, Expr left, Token token) {
            var loc = parser.lok();
            Expr right = parser.expression(precedence());
            parser.consume(TokenType.RBRACK, "Expected ']' after index access.");
            return new Expr.IndexAccess(loc, left, right);

        }


        @Override
        public int precedence() {
            return Precedence.POSTFIX;
        }
    }

    record PostfixOperator(int precedence) implements InfixParselet {
        public Expr parse(Parser parser, Expr left, Token token) {
            var loc = parser.lok();
            return new Expr.Postfix(loc, left, token);
        }
    }

    record AssignOperator() implements InfixParselet {
        public Expr parse(Parser parser, Expr left, Token token) {
            var loc = parser.lok();
            Expr right = parser.expression(precedence() - 1);

            return new Expr.Assign(loc, left, right);
        }

        @Override
        public int precedence() {
            return Precedence.ASSIGNMENT;
        }
    }

    record Call() implements InfixParselet {
        public Expr parse(Parser parser, Expr left, Token token) {
            var loc = parser.lok();
            List<Expr> args = new ArrayList<>();

            // There may be no arguments at all.
            if (!parser.match(TokenType.RPAREN)) {
                do {
                    args.add(parser.expression());
                } while (parser.match(TokenType.COMMA));
                parser.optional(TokenType.COMMA);
                parser.consume(TokenType.RPAREN, "Expected closing ')' after function call.");
            }

            return new Expr.Call(loc, left, args);
        }

        @Override
        public int precedence() {
            return Precedence.PRIMARY;
        }
    }


}
