package org.imp.jvm.parser;

import org.imp.jvm.Expr;
import org.imp.jvm.Stmt;
import org.imp.jvm.statement.Statement;
import org.imp.jvm.tokenizer.Token;
import org.imp.jvm.tokenizer.TokenType;

import static org.imp.jvm.tokenizer.TokenType.*;

import org.imp.jvm.tokenizer.Tokenizer;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final Tokenizer tokens;
    private final List<Token> mRead = new ArrayList<Token>();

    public Parser(Tokenizer tokens) {
        this.tokens = tokens;
    }

    List<Stmt> parse() {
        List<Stmt> stmts = new ArrayList<>();

        var stmt = statement();

        return stmts;
    }

    private Stmt statement() {
        if (match(TYPE)) return typeAlias();
        return null;
    }

    private Stmt.TypeAlias typeAlias() {
        Token name = consume(IDENTIFIER, "Expected type name.");
        consume(ASSIGN, "Expected assignment.");
        consume(EXTERN, "Expected 'extern' keyword in type alias");
        Token literal = consume(STRING, "Expected string literal.");

        return new Stmt.TypeAlias(name, new Expr.Literal(literal));
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                consume();
                return true;
            }
        }

        return false;
    }

    private boolean check(TokenType type) {
        return peek().type() == type;
    }

    public boolean match(TokenType expected) {
        Token token = lookAhead(0);
        if (token.type() != expected) {
            return false;
        }

        consume();
        return true;
    }

    public Token consume() {
        // Make sure we've read the token.
        lookAhead(0);

        return mRead.remove(0);
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) return consume();

        error(peek(), message);
        return null;
    }

    private static void report(int line, String where,
                               String message) {
        System.err.println(
                "[line " + line + "] Error" + where + ": " + message);
    }

    //< lox-error
//> Parsing Expressions token-error
    void error(Token token, String message) {
        if (token.type() == TokenType.EOF) {
            report(token.line(), " at end", message);
        } else {
            report(token.line(), " at '" + token.source() + "'", message);
        }
    }

    private Token peek() {
        return lookAhead(0);
    }

    private Token lookAhead(int distance) {
        // Read in as many as needed.
        while (distance >= mRead.size()) {
            mRead.add(tokens.next());
        }

        // Get the queued token.
        return mRead.get(distance);
    }
}
