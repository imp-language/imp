package org.imp.jvm.tokenizer;

import java.util.HashMap;

public enum TokenType {
    // Separators
    LPAREN("("),
    RPAREN(")"),
    LBRACE("{"),
    RBRACE("}"),
    LBRACK("["),
    RBRACK("]"),
    COMMA(","),
    VARARGS("..."),
    DOT("."),


    // Binary operators
    ASSIGN("="),
    GE(">="),
    LE("<="),
    GT(">"),
    LT("<"),
    EQUAL("=="),
    NOTEQUAL("!="),
    AND("and", "&&"),
    OR("or", "||"),
    ADD("+"),
    MUL("*"),
    SUB("-"),
    DIV("/"),
    MOD("%"),
    POW("^"),

    // Prefix-only operators
    NOT("not", "!"),

    // Suffix-only operators
    INC("++"),
    DEC("--"),

    // Keywords
    FOR("for"),
    IF("if"),
    ELSE("else"),
    RETURN("return"),
    STRUCT("struct"),
    ENUM("enum"),
    FUNC("func"),
    TYPE("type"),
    VAL("val"),
    MUT("mut"),
    EXPORT("export"),
    IMPORT("import"),
    AS("as"),
    NEW("new"),
    EXTERN("extern"),
    IN("in"),

    // Literals
    TRUE("true"),
    FALSE("false"),
    STRING,
    IDENTIFIER,
    NUMBER,

    ERROR,
    EOF;


    public final String representation;
    public final String alternate;
    private static final HashMap<String, TokenType> matcher = new HashMap<>();

    static {
        for (var tokenType : TokenType.values()) {
            if (tokenType.representation != null) matcher.put(tokenType.representation, tokenType);
            if (tokenType.alternate != null) matcher.put(tokenType.alternate, tokenType);
        }
    }

    TokenType() {
        this.representation = null;
        this.alternate = null;
    }

    TokenType(String representation) {
        this.representation = representation;
        this.alternate = null;
    }

    TokenType(String representation, String alternate) {
        this.representation = representation;
        this.alternate = alternate;
    }

    public static TokenType find(String representation) {
        return matcher.get(representation);
    }
}
