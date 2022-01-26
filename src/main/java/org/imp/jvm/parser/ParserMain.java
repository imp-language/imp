package org.imp.jvm.parser;

import org.imp.jvm.tokenizer.Tokenizer;
import org.imp.jvm.tool.Timer;

import java.io.File;
import java.util.HashMap;

import static org.imp.jvm.parser.ParserMain.Method.TEST;

public class ParserMain {
    public static void test(String root) {
        var dirs = new HashMap<String, Method>();
        dirs.put("if", TEST);
    }

    public static void main(String[] args) {

//        var printer = new ASTPrinterVisitor();

        File file = new File("examples/scratch.imp");
        Timer.log("Buffer opened");
        var tokenizer = new Tokenizer(file);
        Timer.log("Lexer created");

        var parser = new Parser(tokenizer);
        var statements = parser.parse();
//        String printed = printer.print(statements);

        Timer.log("Source file parsed.");
        Timer.LOG = true;
        Timer.logTotalTime();
        System.out.println(statements.size() + " statements parsed.");

    }


    enum Method {
        TEST,
        SKIP
    }
}
