package org.imp.jvm.parser;

import org.imp.jvm.Expr;
import org.imp.jvm.tokenizer.Token;

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
            Expr right = parser.expression(
                    precedence - (isRight ? 1 : 0));
            return new Expr.Binary(left, token, right);
        }
    }

    record PostfixOperator(int precedence) implements InfixParselet {
        public Expr parse(Parser parser, Expr left, Token token) {
            return new Expr.Postfix(left, token);
        }
    }
}
