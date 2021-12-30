package org.imp.jvm.tokenizer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.imp.jvm.tokenizer.TokenType.*;

public class Tokenizer implements Iterator<Token> {
    private PushbackReader reader;
    public int line = 1;
    public int col = 1;

    private List<String> lines = new ArrayList<>();

    public Status getStatus() {
        return status;
    }

    private Status status = Status.Whole;

    enum Status {
        Whole, // do codegen, all input is correct
        Partial, // attempt to parse, some errors
        Fatal // bad, exit
    }


    private final StringBuilder sb = new StringBuilder();

    public Tokenizer(File file) {
        try {
            this.reader = new PushbackReader(new FileReader(file), 5);
            lines = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            var tokenType = IDENTIFIER;
            var keyword = TokenType.find(identifier);
            if (keyword != null) tokenType = keyword;
            tok = new Token(tokenType, startLine, startCol, identifier);
        } else if (isDigit(c)) {
            tok = consumeNumber();
        } else if (c == '"') {
            String stringLiteral = consumeString();
            tok = new Token(STRING, startLine, startCol, stringLiteral);
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
                return new Token(ERROR, startLine, startCol, content);
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

    private Token consumeNumber() {
        TokenType type = INT;
        // whole number part
        while (isDigit(peek())) {
            sb.append(advance());
        }
        // decimal point
        if (peek() == '.') {
            type = FLOAT;
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
        if (peek() == 'd') {
            type = DOUBLE;
            sb.append(advance());
        }

        String value = clearStringBuilder();
        return new Token(type, line, col, value);
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
                        var b = 0;
                        while (peek() != '\n'/* && !isAtEnd()*/) advance();
                    } else if (peekNext() == '*') {
                        advance();
                        advance();
                        String nextTwo = "";
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
}
