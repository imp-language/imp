package org.imp.jvm.parser;

import org.imp.jvm.Expr;
import org.imp.jvm.tokenizer.Token;
import org.imp.jvm.tokenizer.TokenType;
import org.imp.jvm.tokenizer.Tokenizer;
import org.imp.jvm.tool.Timer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ParserMain {
    public static void main(String[] args) {
//        var mul = new Expr.Binary(
//                new Expr.Binary(
//                        new Expr.Literal(new Token(TokenType.NUMBER, 0, 0, "1")),
//                        new Token(TokenType.ADD, 0, 0, "+"),
//                        new Expr.Binary(
//                                new Expr.Literal(new Token(TokenType.NUMBER, 0, 0, "2")),
//                                new Token(TokenType.MUL, 0, 0, ""),
//                                new Expr.Literal(new Token(TokenType.NUMBER, 0, 0, "3"))
//                        )),
//                new Token(TokenType.SUB, 0, 0, "-"),
//                new Expr.Literal(new Token(TokenType.NUMBER, 0, 0, "4"))
//        );
        var printer = new ASTPrinter();

        try {
            File file = new File("examples/scratch.imp");
            BufferedReader reader =
                    new BufferedReader(new FileReader(file));
            Timer.log("Buffer opened");
            var tokenizer = new Tokenizer(reader);
            Timer.log("Lexer created");

            var parser = new Parser(tokenizer);
            var statements = parser.parse();
//            System.out.println(printer.print(statements));
            Timer.log("Source file parsed.");
            Timer.LOG = true;
            Timer.logTotalTime();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
