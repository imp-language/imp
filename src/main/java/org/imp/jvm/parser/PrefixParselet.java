package org.imp.jvm.parser;

import org.imp.jvm.Expr;
import org.imp.jvm.tokenizer.Token;
import org.imp.jvm.tokenizer.TokenType;
import org.imp.jvm.typechecker.Annotation;

import java.util.ArrayList;
import java.util.List;

public interface PrefixParselet {
    Expr parse(Parser parser, Token token);

    record PrefixOperator(int precedence) implements PrefixParselet {
        public Expr parse(Parser parser, Token token) {
            // To handle right-associative operators like "^", we allow a slightly
            // lower precedence when parsing the right-hand side. This will let a
            // parselet with the same precedence appear on the right, which will then
            // take *this* parselet's result as its left-hand argument.
            Expr right = parser.expression(precedence());

            return new Expr.Prefix(new Annotation(), token, right);
        }
    }

    record Identifier() implements PrefixParselet {
        public Expr parse(Parser parser, Token token) {
            return new Expr.Identifier(new Annotation(), token);
        }
    }

    record Literal(boolean isList) implements PrefixParselet {
        public Expr parse(Parser parser, Token token) {
            if (token.type() == TokenType.LBRACK) {
                List<Expr> args = new ArrayList<>();
                if (!parser.match(TokenType.RBRACK)) {
                    do {
                        args.add(parser.expression());
                    } while (parser.match(TokenType.COMMA));
                    parser.optional(TokenType.COMMA);
                    parser.consume(TokenType.RBRACK, "Expected closing ']' after list literal.");
                }
                return new Expr.LiteralList(new Annotation(), args);
            }

            return new Expr.Literal(new Annotation(), token);
        }
    }

    record New() implements PrefixParselet {
        public Expr parse(Parser parser, Token token) {
            Expr expr = parser.expression();


            return new Expr.New(new Annotation(), expr);
        }
    }


    record Grouping() implements PrefixParselet {
        public Expr parse(Parser parser, Token token) {
            Expr expression = parser.expression();
            parser.consume(TokenType.RPAREN, "Expected opening parentheses.");
            return expression;
        }
    }
}
