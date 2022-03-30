package org.imp.jvm.parser;

import org.imp.jvm.Util;
import org.imp.jvm.parser.tokenizer.Tokenizer;
import org.imp.jvm.tool.Timer;

import java.io.File;
import java.io.FileNotFoundException;

public class ParserMain {

    public static void main(String[] args) throws FileNotFoundException {

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
        Util.println(statements.size() + " statements parsed.");

    }


}
