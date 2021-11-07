package org.imp.jvm.parser;

public enum Precedence {
    ASSIGNMENT(1),
    CONDITIONAL(2),
    SUM(3),
    PRODUCT(4),
    EXPONENT(5),
    PREFIX(6),
    POSTFIX(7),
    CALL(8);

    public final int precedence;

    Precedence(int precedence) {
        this.precedence = precedence;
    }
}
