package org.imp.jvm.typechecker;

import org.imp.jvm.parser.ASTPrinter;
import org.imp.jvm.parser.Parser;
import org.imp.jvm.tokenizer.Tokenizer;
import org.imp.jvm.tool.Timer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class TypeCheckerMain {
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
            String printed = printer.print(statements);
            System.out.println(printed);

            Timer.log("Source file parsed.");
            Timer.LOG = true;
            Timer.logTotalTime();

            var typeChecker = new TypeChecker();
            typeChecker.validate(statements.get(0));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
