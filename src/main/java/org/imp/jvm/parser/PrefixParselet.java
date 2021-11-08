package org.imp.jvm.parser;

import org.imp.jvm.Expr;
import org.imp.jvm.tokenizer.Token;
import org.imp.jvm.tokenizer.TokenType;

public interface PrefixParselet {
    Expr parse(Parser parser, Token token);

    record PrefixOperator(int precedence) implements PrefixParselet {
        public Expr parse(Parser parser, Token token) {
            // To handle right-associative operators like "^", we allow a slightly
            // lower precedence when parsing the right-hand side. This will let a
            // parselet with the same precedence appear on the right, which will then
            // take *this* parselet's result as its left-hand argument.
            Expr right = parser.expression(precedence());

            return new Expr.Prefix(token, right);
        }
    }

    record Identifier() implements PrefixParselet {
        public Expr parse(Parser parser, Token token) {
            return new Expr.Identifier(token);
        }
    }

    record Literal() implements PrefixParselet {
        public Expr parse(Parser parser, Token token) {
            return new Expr.Literal(token);
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
