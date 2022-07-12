package org.imp.jvm.parser;

import org.imp.jvm.parser.tokenizer.Token;
import org.imp.jvm.parser.tokenizer.TokenType;

import java.util.ArrayList;
import java.util.List;

public interface PrefixParselet {
    Expr parse(Parser parser, Token token);

    record PrefixOperator(int precedence) implements PrefixParselet {
        public Expr parse(Parser parser, Token token) {
            var loc = parser.lok();
            // To handle right-associative operators like "^", we allow a slightly
            // lower precedence when parsing the right-hand side. This will let a
            // parselet with the same precedence appear on the right, which will then
            // take *this* parselet's result as its left-hand argument.
            Expr right = parser.expression(precedence());

            return new Expr.Prefix(loc, token, right);
        }
    }

    record Identifier() implements PrefixParselet {
        public Expr parse(Parser parser, Token token) {
            var loc = parser.lok();

            // add module stuff
            if (parser.match(TokenType.MODULE)) {
                var nextToken = parser.consume(TokenType.IDENTIFIER, "Expected identifier after module separator `::`.");
                token = new Token(TokenType.IDENTIFIER, loc.line(), loc.col(), token.source() + "::" + nextToken.source());
            }

            if (parser.peek().type() == TokenType.LBRACK) {
                parser.consume();
                parser.consume(TokenType.RBRACK, "Expect ']' to close list constructor.");
                return new Expr.EmptyList(loc, token);
            }/* else if (parser.match(TokenType.MODULE)) {
                Expr foreign = parser.expression();
                return new Expr.ModuleAccess(loc, new Expr.Identifier(loc, token), foreign);
            }*/
            return new Expr.Identifier(loc, token);

        }
    }

    record Literal(boolean isList) implements PrefixParselet {
        public Expr parse(Parser parser, Token token) {
            var loc = parser.lok();
            if (token.type() == TokenType.LBRACK) {
                List<Expr> args = new ArrayList<>();
                if (!parser.match(TokenType.RBRACK)) {
                    do {
                        args.add(parser.expression());
                    } while (parser.match(TokenType.COMMA));
                    parser.optional(TokenType.COMMA);
                    parser.consume(TokenType.RBRACK, "Expected closing ']' after list literal.");
                }
                return new Expr.LiteralList(loc, args);
            }

            return new Expr.Literal(loc, token);
        }
    }


    record Grouping() implements PrefixParselet {
        public Expr parse(Parser parser, Token token) {
            var loc = parser.lok();
            Expr expression = parser.expression();
            parser.consume(TokenType.RPAREN, "Expected opening parentheses.");
            return new Expr.Grouping(loc, expression);
        }
    }
}
