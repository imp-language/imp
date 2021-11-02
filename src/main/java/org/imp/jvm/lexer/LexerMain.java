package org.imp.jvm.lexer;

import org.imp.jvm.tool.Timer;
import org.openjdk.jmh.annotations.Benchmark;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LexerMain {

    @Benchmark
    public void lexerBenchMark() {
        try {
            File file = new File("examples/long.imp");
            PushbackReader reader =
                    new PushbackReader(new FileReader(file));
            Timer.log("PushbackReader opened");
            var lexer = new org.imp.jvm.lexer.Lexer(reader);
            org.imp.jvm.lexer.Token tok;
            List<Token> tokens = new ArrayList<>();
            do {
                tok = lexer.next();
//                tokens.add(tok);
//                System.out.println(tok);
            } while (tok.type() != TokenType.EOF);

            Timer.log("Source file tokenized.");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Timer.LOG = true;
        new LexerMain().lexerBenchMark();
//        org.openjdk.jmh.Main.main(args);
    }
}
