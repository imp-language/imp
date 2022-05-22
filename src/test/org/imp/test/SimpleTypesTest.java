package org.imp.test;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class SimpleTypesTest extends BaseTest {

    @Test
    void lists() throws IOException, InterruptedException {
        test("stdlib/lists.txt", "stdlib/lists");
    }

    @Test
    void match() throws IOException, InterruptedException {
        test("simple/match.txt",
                "simple/match");

    }

    @Test
    void structInit() throws IOException, InterruptedException {
        testLiteral("simple/structInit", """
                Bottom[data=2]
                Bottom[data=9]
                Middle[value=Bottom[data=2], other=Bottom[data=9], union=9]
                Top[middle=Middle[value=Bottom[data=2], other=Bottom[data=9], union=9]]
                Top[middle=Middle[value=Bottom[data=4], other=Bottom[data=-427], union=aaa]]
                """);

    }

    @Test
    void unions() throws IOException, InterruptedException {
        test("simple/unions.txt", "simple/unions");

    }


}
