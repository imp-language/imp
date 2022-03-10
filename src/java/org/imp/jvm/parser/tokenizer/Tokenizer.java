package org.imp.jvm.parser.tokenizer;

import java.io.*;
import java.util.Iterator;

public class Tokenizer implements Iterator<Token> {
    private final StringBuilder sb = new StringBuilder();
    private final PushbackReader reader;
    public int line = 1;
    public int col = 1;
    private Status status = Status.Whole;

    public Tokenizer(File file) throws FileNotFoundException {
        this.reader = new PushbackReader(new FileReader(file), 5);
    }

    public Status getStatus() {
        return status;
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
            tok = new Token(TokenType.EOF, startLine, startCol, null);
        } else if (isAlpha(c)) {
            String identifier = consumeIdentifier();
            var tokenType = TokenType.IDENTIFIER;
            var keyword = TokenType.find(identifier);
            if (keyword != null) tokenType = keyword;
            tok = new Token(tokenType, startLine, startCol, identifier);
        } else if (isDigit(c)) {
            tok = consumeNumber();
        } else if (c == '"') {
            String stringLiteral = consumeString();

            var stringLiteralWithEscapes = stringLiteral.translateEscapes();
            tok = new Token(TokenType.STRING, startLine, startCol, stringLiteralWithEscapes);
        } else {
            var shortToken = TokenType.find(String.valueOf(c));
//            if (shortToken == null) {
            char next = peekNext();
            String content = String.valueOf(new char[]{c, next});
            var longToken = TokenType.find(content);

            if (longToken == null && shortToken == null) {
                advance();
                advance();
                status = Status.Partial;
                return new Token(TokenType.ERROR, startLine, startCol, content);
            } else if (longToken != null) {
                advance();
                advance();
                tok = new Token(longToken, startLine, startCol, content);
            } else {
                advance();
                tok = new Token(shortToken, startLine, startCol, String.valueOf(c));
            }
        }

        return tok;

    }

    /**
     * Call advance() on all tokens considered whitespace or comments
     */
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
                        char cc = ' ';
                        while (peek() != '\n' && cc != '\0') {
                            cc = advance();
                        }
                    } else if (peekNext() == '*') {
                        advance();
                        advance();
                        String nextTwo;
                        do {
                            advance();
                            nextTwo = String.valueOf(peek()) + peekNext();
                        } while (!nextTwo.equals("*/"));
                        advance();
                        advance();
                    } else {
                        return;
                    }
                    break;
                default:
                    return;
            }
        }
    }

    /**
     * Advance along the input stream.
     *
     * @return char
     */
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

    private Token consumeNumber() {
        TokenType type = TokenType.INT;
        // whole number part
        while (isDigit(peek())) {
            sb.append(advance());
        }
        // decimal point
        if (peek() == '.') {
            type = TokenType.FLOAT;
            sb.append(advance());
            // decimal part
            while (isDigit(peek())) {
                sb.append(advance());
            }
            // optional float suffix
            if (peek() == 'f') {
                sb.append(advance());
            }
        }

        // float suffix
        if (peek() == 'f') {
            type = TokenType.FLOAT;
            sb.append(advance());
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
        if (peek() == 'd') {
            type = TokenType.DOUBLE;
            sb.append(advance());
        }

        String value = clearStringBuilder();
        return new Token(type, line, col, value);
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

    private boolean isAlpha(char c) {
        return Character.isLetter(c) || c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    /**
     * Peek one ahead.
     *
     * @return char
     */
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

    /**
     * Peek two ahead.
     *
     * @return char
     */
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

    enum Status {
        Whole, // do codegen, all input is correct
        Partial, // attempt to parse, some errors
        Fatal // bad, exit
    }
}
