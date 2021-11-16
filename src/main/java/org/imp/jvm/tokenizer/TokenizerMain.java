package org.imp.jvm.tokenizer;

import org.imp.jvm.tool.Timer;
import org.openjdk.jmh.annotations.Benchmark;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TokenizerMain {

    @Benchmark
    public void lexerBenchMark() {
        try {
//            File file = new File("examples/long.imp");
            File file = new File("examples/scratch.imp");
            BufferedReader reader =
                    new BufferedReader(new FileReader(file));
            Timer.log("Buffer opened");
            var lexer = new Tokenizer(reader);
            Timer.log("Lexer created");
            Token tok;
            List<Token> tokens = new ArrayList<>();
            do {
                tok = lexer.next();
//                tokens.add(tok);
                System.out.println(tok);
            } while (tok.type() != TokenType.EOF);

            Timer.log("Source file tokenized.");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        new TokenizerMain().lexerBenchMark();

        Timer.LOG = true;
        Timer.logTotalTime();
    }
}
