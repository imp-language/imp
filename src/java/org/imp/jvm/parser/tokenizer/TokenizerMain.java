package org.imp.jvm.parser.tokenizer;

import org.imp.jvm.tool.Timer;
import org.openjdk.jmh.annotations.Benchmark;

import java.io.File;
import java.io.FileNotFoundException;

public class TokenizerMain {

	public static void main(String[] args) throws FileNotFoundException {

		new TokenizerMain().lexerBenchMark();

		Timer.LOG = true;
		Timer.logTotalTime();
	}

	@Benchmark
	public void lexerBenchMark() throws FileNotFoundException {
		//            File file = new File("examples/long.imp");
		File file = new File("examples/scratch.imp");
		Timer.log("Buffer opened");
		var lexer = new Tokenizer(file);
		Timer.log("Lexer created");
		Token tok;
		do {
			tok = lexer.next();
		} while (tok.type() != TokenType.EOF);

		Timer.log("Source file tokenized.");

	}
}
