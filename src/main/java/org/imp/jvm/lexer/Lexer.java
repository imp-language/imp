package org.imp.jvm.lexer;

import java.io.IOException;
import java.io.PushbackReader;
import java.util.Iterator;

import static org.imp.jvm.lexer.TokenType.*;

public class Lexer implements Iterator<Token> {
    private final PushbackReader reader;
    private int line = 1;
    private int col = 1;

    private final StringBuilder sb = new StringBuilder();

    public Lexer(PushbackReader reader) {
        this.reader = reader;
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Token next() {
        skipWhitespace();
        int startLine = line;
        int startCol = col;
        char c = peek();
        Token tok;
        if (c == '\0') {
            tok = new Token(EOF, startLine, startCol, null);
        } else if (isAlpha(c)) {
            String identifier = consumeIdentifier();
            tok = new Token(IDENTIFIER, startLine, startCol, identifier);
        } else if (isDigit(c)) {
            String number = consumeNumber();
            tok = new Token(NUMBER, startLine, startCol, number);
        } else if (c == '"') {
            String stringLiteral = consumeString();
            tok = new Token(STRING, startLine, startCol, stringLiteral);
        } else {
            var shortToken = TokenType.find(c + "");
            if (shortToken == null) {
                char next = peekNext();
                var longToken = TokenType.find(Character.toString(c) + next);
                advance();
                advance();
                // if null error
                tok = new Token(longToken, startLine, startCol, Character.toString(c) + next);

            } else {
                advance();
                tok = new Token(shortToken, startLine, startCol, c + "");
            }
        }

        return tok;

    }

    private String consumeString() {
        advance();
        char p = peek();
        while (p != '"' && p != '\0') {
            sb.append(advance());
            p = peek();
        }
        // consume the closing quotation marks
        advance();
        return clearStringBuilder();
    }

    private String clearStringBuilder() {
        String result = sb.toString();
        sb.setLength(0);
        return result;
    }

    private String consumeIdentifier() {
        while (isAlphaNumeric(peek())) {
            sb.append(advance());
        }

        return clearStringBuilder();
    }

    private String consumeNumber() {
        // whole number part
        while (isDigit(peek())) {
            sb.append(advance());
        }
        // decimal point
        if (peek() == '.') {
            sb.append(advance());
            // decimal part
            while (isDigit(peek())) {
                sb.append(advance());
            }
        }

        // exponent part
        char p = peek();
        if (p == 'e' || p == 'E') {
            sb.append(advance());
            p = peek();
            if (p == '-' || p == '+') sb.append(advance());
            // whole number exponent
            while (isDigit(peek())) {
                sb.append(advance());
            }
        }

        // double suffix
        if (peek() == 'd') sb.append(advance());

        return clearStringBuilder();
    }


    private char advance() {
        try {
            int i = reader.read();
            if (i == -1) return '\0';
            if (i == '\n') {
                line++;
                col = 1;
            } else {
                col++;
            }
            return (char) i;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return '\0';
    }

    private char peek() {
        try {
            int i = reader.read();
            if (i == -1) return '\0';
            reader.unread(i);
            return (char) i;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return '\0';
    }

    private char peekNext() {
        try {
            int i = reader.read();
            int j = reader.read();
            if (j == -1) return '\0';
            reader.unread(j);
            reader.unread(i);
            return (char) j;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return '\0';
    }

    private boolean isAlpha(char c) {
        return Character.isLetter(c) || c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    void skipWhitespace() {
        while (true) {
            char c = peek();
            switch (c) {
                case ' ':
                case '\r':
                case '\t':
                case '\n':
                    advance();
                    break;
                case '/':
                    if (peekNext() == '/') {
                        // A comment goes until the end of the line.
                        while (peek() != '\n'/* && !isAtEnd()*/) advance();
                    } else {
                        return;
                    }
                    break;
                default:
                    return;
            }
        }
    }
}
