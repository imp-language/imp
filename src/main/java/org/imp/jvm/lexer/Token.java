package org.imp.jvm.lexer;

public record Token(TokenType type, int line, int col, String source) {

    @Override
    public String toString() {
        return type.name() + "{" + source + "}@" + line + ":" + col;
    }

}