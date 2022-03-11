package org.imp.jvm.parser;

import org.imp.jvm.Environment;
import org.imp.jvm.Util;
import org.imp.jvm.parser.tokenizer.Token;
import org.imp.jvm.parser.tokenizer.Tokenizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.imp.jvm.parser.tokenizer.TokenType.*;

public class Parser extends ParserBase {

    public Parser(Tokenizer tokens) {
        super(tokens);

        // Register the ones that need special parselets.
        register(INT, new PrefixParselet.Literal(false));
        register(FLOAT, new PrefixParselet.Literal(false));
        register(DOUBLE, new PrefixParselet.Literal(false));
        register(TRUE, new PrefixParselet.Literal(false));
        register(FALSE, new PrefixParselet.Literal(false));
        register(STRING, new PrefixParselet.Literal(false));
        register(LBRACK, new PrefixParselet.Literal(true));
        register(IDENTIFIER, new PrefixParselet.Identifier());

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
        infixLeft(NOTEQUAL, Precedence.COMPARISON);
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
        var loc = lok();

        var type = new Stmt.Type(loc, new Token(TYPE, 0, 0, "string"), Optional.empty(), false);
        Stmt.Parameter varArgs = new Stmt.Parameter(loc, new Token(IDENTIFIER, 0, 0, "args"), type);
//        varArgs.type = BuiltInType.STRING_ARR;
//        varArgs.name = "args";
        var args = new ArrayList<Stmt.Parameter>();
        args.add(varArgs);

        var main = new Stmt.Function(
                loc,
                new Token(IDENTIFIER, loc.line(), loc.col(), "main"),
                args,
                new Token(TYPE, loc.line(), loc.col(), "void"),
                new Stmt.Block(loc, new ArrayList<>(), new Environment())
        );

        while (notAtEnd()) {
            var stmt = statement();
            if (stmt == null) break;

            if (stmt instanceof Stmt.Import) {
                stmts.add(stmt);
            } else if (stmt instanceof Stmt.Function) {
                stmts.add(stmt);
            } else if (stmt instanceof Stmt.Export exportStmt) {
                var subStmt = exportStmt.stmt;
                if (subStmt instanceof Stmt.Enum || subStmt instanceof Stmt.Struct || subStmt instanceof Stmt.Function) {
                    stmts.add(exportStmt);
                }
            } else {
                main.body.statements.add(stmt);
            }
        }
        stmts.add(main);
        return stmts;
    }

    Expr expression() {
        return expression(0);
    }

    Expr expression(int precedence) {

        Token token = consume();
        PrefixParselet prefix = prefixParselets.get(token.type());

        if (prefix == null) {
            error(token, "Could not parse.");
            return new Expr.Bad(lok(), token);
        }

        Expr left = prefix.parse(this, token);

        while (precedence < getPrecedence()) {
            token = consume();

            InfixParselet infix = infixParselets.get(token.type());
            left = infix.parse(this, left, token);
        }

        return left;
    }

    Stmt.Return parseReturn() {
        var loc = lok();
        Expr expr = expression();
        return new Stmt.Return(loc, expr);
    }

    private Stmt.Block block() {
        var loc = lok();
        List<Stmt> statements = new ArrayList<>();
        consume(LBRACE, "Expect '{' before block.");

        while (!check(RBRACE) && notAtEnd()) {
            statements.add(statement());
        }

        consume(RBRACE, "Expect '}' after block.");
        return new Stmt.Block(loc, statements, new Environment());
    }

    private Stmt.Export export() {
        var loc = lok();
        var stmt = statement();
        if (Util.instanceOfOne(stmt,
                Stmt.Struct.class,
                Stmt.TypeAlias.class,
                Stmt.Variable.class,
                Stmt.Enum.class,
                Stmt.Function.class
        )) {
            return new Stmt.Export(loc, stmt);
        } else {
            System.err.println("Can only export struct, type alias, variable, enum or function.");
            System.exit(65);
            return null;
        }
    }

    private Stmt.Function function() {
        var loc = lok();
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

        return new Stmt.Function(loc, name, parameters, returnType, block);
    }

    private Stmt.Import importStmt() {
        var loc = lok();
        var str = consume(STRING, "Import must be followed by a string.");

        Optional<Token> id = Optional.empty();
        if (match(AS)) {
            id = Optional.of(consume());
        }
        return new Stmt.Import(loc, str, id);
    }

    private Stmt.Parameter parameter() {
        var loc = lok();
        Token name = consume(IDENTIFIER, "Expected field name.");
        var type = type();

        return new Stmt.Parameter(loc, name, type);
    }

    private Stmt.Enum parseEnum() {
        var loc = lok();
        Token name = consume(IDENTIFIER, "Expected enum name.");
        consume(LBRACE, "Expected opening curly braces before struct body.");

        List<Token> values = new ArrayList<>();
        while (!check(RBRACE)) {
            Token value = consume(IDENTIFIER, "Expected enum value.");
            optional(COMMA);
            values.add(value);
        }

        consume(RBRACE, "Expected opening curly braces before struct body.");

        return new Stmt.Enum(loc, name, values);
    }

    private Stmt.For parseFor() {
        var loc = lok();
        Token name = consume(IDENTIFIER, "Need iterator variable name.");
        consume(IN, "Expected 'in' keyword.");
        Expr condition = expression();
        Stmt.Block block = block();

        return new Stmt.For(loc, new Stmt.ForInCondition(loc, name, condition), block);
    }

    private Stmt.If parseIf() {
        var loc = lok();
        Expr condition = expression();
        Stmt.Block trueBlock = block();
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
        return new Stmt.If(loc, condition, trueBlock, falseStmt);
    }

    private Stmt statement() {
        var loc = lok();
        if (check(ERROR)) {
            throw new Error("wut");
        }

        if (match(IMPORT)) return importStmt();
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
        else return new Stmt.ExpressionStmt(loc, expression());
    }

    private Stmt.Struct struct() {
        var loc = lok();
        Token name = consume(IDENTIFIER, "Expected struct name.");
        consume(LBRACE, "Expected opening curly braces before struct body.");

        List<Stmt.Parameter> parameters = new ArrayList<>();
        while (!check(RBRACE)) {
            Stmt.Parameter parameter = parameter();
            optional(COMMA);
            parameters.add(parameter);
        }

        consume(RBRACE, "Expected opening curly braces before struct body.");

        return new Stmt.Struct(loc, name, parameters);
    }

    private Stmt.Type type() {
        var loc = lok();

        // get the identifier first
        Token identifier = consume(IDENTIFIER, "Expected type name.");

        // Check for [] denoting a list type
        boolean listType = false;
        if (match(LBRACK)) {
            listType = true;
            consume(RBRACK, "Expected ']' in list type.");
        }

        Optional<Stmt.Type> t = Optional.empty();
        // Check for . denoting qualified type
        if (match(DOT)) {
            t = Optional.of(type());
        }

        return new Stmt.Type(loc, identifier, t, listType);
    }

    private Stmt.TypeAlias typeAlias() {
        var loc = lok();
        Token name = consume(IDENTIFIER, "Expected type name.");
        consume(ASSIGN, "Expected assignment.");
        consume(EXTERN, "Expected 'extern' keyword in type alias");
        Token literal = consume(STRING, "Expected string literal.");

        return new Stmt.TypeAlias(loc, name, new Expr.Literal(loc, literal));
    }

    private Stmt.Variable variable() {
        var loc = lok();
        Token mutability = consume();
        Token name = consume(IDENTIFIER, "Expected variable name.");
        consume(ASSIGN, "Expected assignment operator.");

        Expr expr = expression();

        return new Stmt.Variable(loc, mutability, name, expr);
    }


}