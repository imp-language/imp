package org.imp.jvm.parser;

import org.imp.jvm.Environment;
import org.imp.jvm.Expr;
import org.imp.jvm.Stmt;
import org.imp.jvm.tokenizer.Token;
import org.imp.jvm.tokenizer.Tokenizer;
import org.imp.jvm.typechecker.Annotation;

import java.util.ArrayList;
import java.util.List;

import static org.imp.jvm.tokenizer.TokenType.*;

public class Parser extends ParserBase {

    public Parser(Tokenizer tokens) {
        super(tokens);

        // Register the ones that need special parselets.
        register(IDENTIFIER, new PrefixParselet.Identifier());
        register(INT, new PrefixParselet.Literal(false));
        register(FLOAT, new PrefixParselet.Literal(false));
        register(DOUBLE, new PrefixParselet.Literal(false));
        register(TRUE, new PrefixParselet.Literal(false));
        register(FALSE, new PrefixParselet.Literal(false));
        register(STRING, new PrefixParselet.Literal(false));
        register(LBRACK, new PrefixParselet.Literal(true));

        register(ASSIGN, new InfixParselet.AssignOperator());
        register(NEW, new PrefixParselet.New());
        register(DOT, new InfixParselet.PropertyAccess());
        register(LBRACK, new InfixParselet.IndexAccess());

        register(LPAREN, new PrefixParselet.Grouping());
        register(LPAREN, new InfixParselet.Call());

        // Register the simple operator parselets.
        prefix(ADD, Precedence.PREFIX);
        prefix(SUB, Precedence.PREFIX);
        prefix(NOT, Precedence.PREFIX);

        infixLeft(ADD, Precedence.SUM);
        infixLeft(SUB, Precedence.SUM);
        infixLeft(MUL, Precedence.PRODUCT);
        infixLeft(DIV, Precedence.PRODUCT);
        infixRight(POW, Precedence.EXPONENT);

        infixLeft(RANGE, Precedence.COMPARISON);


        // Comparisons
        infixLeft(EQUAL, Precedence.COMPARISON);
        infixLeft(LT, Precedence.COMPARISON);
        infixLeft(GT, Precedence.COMPARISON);
        infixLeft(LE, Precedence.COMPARISON);
        infixLeft(GE, Precedence.COMPARISON);
        infixLeft(AND, Precedence.AND);
        infixLeft(OR, Precedence.OR);

        // Postfix operators
        postfix(INC, Precedence.POSTFIX);
        postfix(DEC, Precedence.POSTFIX);
    }

    public List<Stmt> parse() {
        List<Stmt> stmts = new ArrayList<>();

        while (!isAtEnd()) {

            // If a statement is poorly formed, we mark it
            // as such and continue to attempt to parse the
            // other statements in the file.
            var stmt = statement();
            if (stmt == null) break;
            stmts.add(stmt);
        }
        return stmts;
    }

    private Stmt statement() {
        if (check(ERROR)) {
            System.out.println();
        }

        if (match(EXPORT)) return export();

        if (match(TYPE)) return typeAlias();
        if (match(STRUCT)) return struct();
        if (match(FUNC)) return function();
        if (match(ENUM)) return parseEnum();
        if (match(IF)) return parseIf();
        if (check(MUT) || check(VAL)) return variable();
        if (match(FOR)) return parseFor();
        if (match(RETURN)) return parseReturn();
        if (check(LBRACE)) return block();
        else return new Stmt.ExpressionStmt(expression());
    }

    Stmt.Return parseReturn() {
        Expr expr = expression();
        return new Stmt.Return(expr);
    }

    private Stmt.If parseIf() {
        Expr condition = expression();
        Stmt.Block trueStmt = block();
        Stmt falseStmt = null;
        if (match(ELSE)) {
            if (match(IF)) {
                falseStmt = parseIf();
            } else if (check(LBRACE)) {
                falseStmt = block();
            } else {
                error(peek(), "Invalid end to if-else statement.");
            }
        }
        return new Stmt.If(condition, trueStmt, falseStmt);
    }

    private Stmt.For parseFor() {
        Token name = consume(IDENTIFIER, "Need iterator variable name.");
        consume(IN, "Expected 'in' keyword.");
        Expr condition = expression();
        Stmt.Block block = block();

        return new Stmt.For(new Stmt.ForInCondition(name, condition), block);
    }

    private Stmt.Variable variable() {
        Token mutability = consume();
        Token name = consume(IDENTIFIER, "Expected variable name.");
        consume(ASSIGN, "Expected assignment operator.");

        Expr expr = expression();

        return new Stmt.Variable(mutability, name, expr);
    }

    Expr expression() {
        return expression(0);
    }

    Expr expression(int precedence) {

        Token token = consume();
        PrefixParselet prefix = prefixParselets.get(token.type());

        if (prefix == null) {
            error(token, "Could not parse.");
            return new Expr.Bad(new Annotation(), token);
        }


        Expr left = prefix.parse(this, token);

        while (precedence < getPrecedence()) {
            token = consume();

            InfixParselet infix = infixParselets.get(token.type());
            left = infix.parse(this, left, token);
        }

        return left;
    }

    private Stmt.Block block() {
        List<Stmt> statements = new ArrayList<>();
        consume(LBRACE, "Expect '{' before block.");

        while (!check(RBRACE) && !isAtEnd()) {
            statements.add(statement());
        }

        consume(RBRACE, "Expect '}' after block.");
        return new Stmt.Block(statements, new Environment());
    }

    private Stmt.Export export() {
        return new Stmt.Export(statement());
    }

    private Stmt.TypeAlias typeAlias() {
        Token name = consume(IDENTIFIER, "Expected type name.");
        consume(ASSIGN, "Expected assignment.");
        consume(EXTERN, "Expected 'extern' keyword in type alias");
        Token literal = consume(STRING, "Expected string literal.");

        return new Stmt.TypeAlias(name, new Expr.Literal(new Annotation(), literal));
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


        Stmt.Block block = block();

        return new Stmt.Function(name, parameters, returnType, block);
    }

    private Stmt.Parameter parameter() {
        Token name = consume(IDENTIFIER, "Expected field name.");
        Token type = consume(IDENTIFIER, "Expected field type.");
        boolean listType = false;
        if (match(LBRACK)) {
            listType = true;
            consume(RBRACK, "Expected ']' in list type.");
            // Todo: better type expressions
        }
        return new Stmt.Parameter(name, type, listType);
    }


}
