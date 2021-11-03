package org.imp.jvm.tokenizer;

public record Token(TokenType type, int line, int col, String source) {

    public String representation() {
        return type.representation;
    }

    @Override
    public String toString() {
        return type.name() + "{" + source + "}@" + line + ":" + col;
    }

}