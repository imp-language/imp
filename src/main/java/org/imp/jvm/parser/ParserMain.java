package org.imp.jvm.parser;

import org.imp.jvm.Expr;
import org.imp.jvm.tokenizer.Token;
import org.imp.jvm.tokenizer.TokenType;
import org.imp.jvm.tokenizer.Tokenizer;
import org.imp.jvm.tool.Timer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.imp.jvm.parser.ParserMain.Method.TEST;

public class ParserMain {
    enum Method {
        TEST,
        SKIP
    }

    public static void test(String root) {
        var dirs = new HashMap<String, Method>();
        dirs.put("if", TEST);
    }


    public static void main(String[] args) {

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
            System.out.println(printer.print(statements));
//            printer.print(statements);
            Timer.log("Source file parsed.");
            Timer.LOG = true;
            Timer.logTotalTime();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
