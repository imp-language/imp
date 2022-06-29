package org.imp.test;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class SimpleConstructsTest extends BaseTest {

	@Test
	void Arithmetic() throws IOException, InterruptedException {
		test("simple/Arithmetic.txt", "simple/Arithmetic");
	}


	@Test
	void Function() throws IOException, InterruptedException {
		testLiteral("simple/Function", """
				4.0
				res: 4.0
				""");
	}

	@Test
	void StdMath() throws IOException, InterruptedException {
		testLiteral("stdlib/MathLibTest", """
				MathLibTest
				5
				""");
	}

	@Test
	void assignment() throws IOException, InterruptedException {
		testLiteral("simple/assignment", """
				53
				""");
	}

	@Test
	void empty() throws IOException, InterruptedException {
		testLiteral("simple/empty", """
				""");
	}

	@Test
	void emptyWithComment() throws IOException, InterruptedException {
		testLiteral("simple/emptyWithComment", """
				""");
	}

	@Test
	void forLoop() throws IOException, InterruptedException {
		test("simple/for.txt", "simple/for");
	}

	@Test
	void helloWorld() throws IOException, InterruptedException {
		testLiteral("simple/helloWorld", """
				Hello, World!
				""");
	}

	@Test
	void ifElseLogical() throws IOException, InterruptedException {
		testLiteral("simple/ifElseLogical", """
				here
				here
				here
				here
				here
				here
				here
				here
				here
				""");
	}

	@Test
	void postfix() throws IOException, InterruptedException {
		testLiteral("simple/postfix", """
				1
				0
				100.0
				99.0
				-741.0
				-742.0
				""");
	}

	@Test
	void relational() throws IOException, InterruptedException {
		test("simple/relational.txt", "simple/relational");
	}


}
