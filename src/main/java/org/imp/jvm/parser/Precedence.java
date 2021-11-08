package org.imp.jvm.parser;

public enum Precedence {
    PRIMARY(10),
    POSTFIX(9),
    PREFIX(8),
    EXPONENT(7),
    PRODUCT(6),
    SUM(5),
    COMPARISON(4),
    AND(3),
    OR(2),
    ASSIGNMENT(1);

    public final int precedence;

    Precedence(int precedence) {
        this.precedence = precedence;
    }
}
