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

public class Parser extends ParserBase {

    public Parser(Tokenizer tokens) {
        super(tokens);
    }

    List<Stmt> parse() {
        List<Stmt> stmts = new ArrayList<>();

        while (!isAtEnd()) {

            // If a statement is poorly formed, we mark it
            // as such and continue to attempt to parse the
            // other statements in the file.
            var stmt = statement();
            stmts.add(stmt);
        }
        return stmts;
    }

    private Stmt statement() {
        if (match(EXPORT)) return export();

        if (match(TYPE)) return typeAlias();
        if (match(STRUCT)) return struct();
        if (match(FUNC)) return function();
        if (match(ENUM)) return parseEnum();
        if (match(LBRACE)) return block();
        return null;
    }

    private Stmt.Block block() {
        List<Stmt> statements = new ArrayList<>();

        while (!check(RBRACE) && !isAtEnd()) {
            statements.add(statement());
        }

        consume(RBRACE, "Expect '}' after block.");
        return new Stmt.Block(statements);
    }

    private Stmt.Export export() {
        return new Stmt.Export(statement());
    }

    private Stmt.TypeAlias typeAlias() {
        Token name = consume(IDENTIFIER, "Expected type name.");
        consume(ASSIGN, "Expected assignment.");
        consume(EXTERN, "Expected 'extern' keyword in type alias");
        Token literal = consume(STRING, "Expected string literal.");

        return new Stmt.TypeAlias(name, new Expr.Literal(literal));
    }

    private Stmt.Struct struct() {
        Token name = consume(IDENTIFIER, "Expected struct name.");
        consume(LBRACE, "Expected opening curly braces before struct body.");

        List<Stmt.Parameter> parameters = new ArrayList<>();
        while (!check(RBRACE)) {
            Stmt.Parameter parameter = parameter();
            optional(COMMA);
            parameters.add(parameter);
        }

        consume(RBRACE, "Expected opening curly braces before struct body.");


        return new Stmt.Struct(name, parameters);
    }

    private Stmt.Enum parseEnum() {
        Token name = consume(IDENTIFIER, "Expected struct name.");
        consume(LBRACE, "Expected opening curly braces before struct body.");

        List<Token> values = new ArrayList<>();
        while (!check(RBRACE)) {
            Token value = consume(IDENTIFIER, "Expected enum value.");
            optional(COMMA);
            values.add(value);
        }

        consume(RBRACE, "Expected opening curly braces before struct body.");


        return new Stmt.Enum(name, values);
    }

    private Stmt.Function function() {
        Token name = consume(IDENTIFIER, "Expected function name.");
        consume(LPAREN, "Expected opening parentheses after function name.");

        List<Stmt.Parameter> parameters = new ArrayList<>();
        if (!check(RPAREN)) {
            do {
                parameters.add(parameter());
            } while (match(COMMA));
        }

        consume(RPAREN, "Expected closing parentheses after function parameters.");

        Token returnType = null;
        if (!check(LBRACE)) {
            returnType = consume();
        }
        consume(LBRACE, "Expected opening curly braces before function body.");

        // Todo parse block
        consume(RBRACE, "Expected opening curly braces before function body.");

        return new Stmt.Function(name, parameters, returnType, null);
    }

    private Stmt.Parameter parameter() {
        Token name = consume(IDENTIFIER, "Expected field name.");
        Token type = consume(IDENTIFIER, "Expected field type.");
        return new Stmt.Parameter(name, type);
    }


}
