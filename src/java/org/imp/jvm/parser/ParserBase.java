package org.imp.jvm.parser;

import org.imp.jvm.parser.tokenizer.Location;
import org.imp.jvm.parser.tokenizer.Token;
import org.imp.jvm.parser.tokenizer.TokenType;
import org.imp.jvm.parser.tokenizer.Tokenizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.imp.jvm.parser.tokenizer.TokenType.EOF;

public class ParserBase {


    final Map<TokenType, PrefixParselet> prefixParselets = new HashMap<>();
    final Map<TokenType, InfixParselet> infixParselets = new HashMap<>();
    private final Tokenizer tokens;
    private final List<Token> mRead = new ArrayList<>();

    public ParserBase(Tokenizer tokens) {
        this.tokens = tokens;
    }

    private static void report(int line, String where,
                               String message) {
        System.err.println(
                "[line " + line + "] Error" + where + ": " + message);
        System.exit(1);
    }

    public Token consume() {
        // Make sure we've read the token.
        lookAhead(0);

        return mRead.remove(0);
    }

    public Token consume(TokenType type, String message) {
        if (check(type)) return consume();

        error(peek(), message);
        return null;
    }

    /**
     * Registers a left-associative binary operator parselet for the given token
     * and precedence.
     */
    public void infixLeft(TokenType token, int precedence) {
        register(token, new InfixParselet.BinaryOperator(precedence, false));
    }

    /**
     * Registers a right-associative binary operator parselet for the given token
     * and precedence.
     */
    public void infixRight(TokenType token, int precedence) {
        register(token, new InfixParselet.BinaryOperator(precedence, true));
    }

    public Location lok() {
        return new Location(tokens.line, tokens.col);
    }

    /**
     * Registers a postfix unary operator parselet for the given token and
     * precedence.
     */
    public void postfix(TokenType token, int precedence) {
        register(token, new InfixParselet.PostfixOperator(precedence));
    }

    /**
     * Registers a prefix unary operator parselet for the given token and
     * precedence.
     */
    public void prefix(TokenType token, int precedence) {
        register(token, new PrefixParselet.PrefixOperator(precedence));
    }

    boolean check(TokenType type) {
        return peek().type() == type;
    }

    void error(Token token, String message) {
        if (token.type() == EOF) {
            report(token.line(), " at end", message);
        } else {
            report(token.line(), " at '" + token.source() + "'", message);
        }
    }

    int getPrecedence() {
        InfixParselet parser = infixParselets.get(lookAhead(0).type());
        if (parser != null) return parser.precedence();

        return 0;
    }

    Token lookAhead(int distance) {
        // Read in as many as needed.
        while (distance >= mRead.size()) {
            mRead.add(tokens.next());
        }

        // Get the queued token.
        return mRead.get(distance);
    }

    boolean notAtEnd() {
        return peek().type() != EOF;
    }

    /**
     * Some tokens exist in the syntax for user convenience.
     * Think trailing commas in a list.
     *
     * @param type the token type to consume, if it exists.
     */
    void optional(TokenType type) {
        if (check(type)) consume();
    }

    Token peek() {
        return lookAhead(0);
    }

    void register(TokenType token, InfixParselet parselet) {
        infixParselets.put(token, parselet);
    }

    void register(TokenType token, PrefixParselet parselet) {
        prefixParselets.put(token, parselet);
    }

    /**
     * check then consume
     *
     * @return true if consumed
     */
    protected boolean match(TokenType expected) {
        Token token = lookAhead(0);
        if (token.type() != expected) {
            return false;
        }

        consume();
        return true;
    }

}
