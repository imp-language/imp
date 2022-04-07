package org.imp.test;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompilerTest {
    static final String pwd = System.getProperty("user.dir");
    static final String moduleLocation = Path.of(pwd, "verification").toString();

    @Test
    void Arithmetic() throws IOException, InterruptedException {
        assertEquals(
                Load.gold(Path.of(moduleLocation, "simple/Arithmetic.txt").toString()),
                Load.run("simple/Arithmetic", moduleLocation)
        );
    }

    @Test
    void Function() throws IOException, InterruptedException {
        assertEquals(Load.run("simple/Function", moduleLocation), """
                4.0
                res: 4.0
                """);
    }

    @Test
    void StdMath() throws IOException, InterruptedException {
        assertEquals(Load.run("stdlib/MathLibTest", moduleLocation), """
                MathLibTest
                5
                """);
    }

    @Test
    void assignment() throws IOException, InterruptedException {
        assertEquals("""
                53
                """, Load.run("simple/assignment", moduleLocation));
    }

    @Test
    void empty() throws IOException, InterruptedException {
        assertEquals(Load.run("simple/empty", moduleLocation), """
                """);
    }

    @Test
    void emptyWithComment() throws IOException, InterruptedException {
        assertEquals(Load.run("simple/emptyWithComment", moduleLocation), """
                """);
    }

    @Test
    void forLoop() throws IOException, InterruptedException {
        assertEquals(
                Load.gold(Path.of(moduleLocation, "simple/for.txt").toString()),
                Load.run("simple/for", moduleLocation)
        );
    }

    @Test
    void helloWorld() throws IOException, InterruptedException {
        assertEquals(Load.run("simple/helloWorld", moduleLocation), """
                Hello, World!
                """);
    }

    @Test
    void ifElseLogical() throws IOException, InterruptedException {
        assertEquals("""
                here
                here
                here
                here
                here
                here
                here
                here
                here
                """, Load.run("simple/ifElseLogical", moduleLocation));
    }

    @Test
    void lists() throws IOException, InterruptedException {
        assertEquals(
                Load.gold(Path.of(moduleLocation, "stdlib/lists.txt").toString()),
                Load.run("stdlib/lists", moduleLocation)
        );
    }

    @Test
    void postfix() throws IOException, InterruptedException {
        assertEquals("""
                1
                0
                100.0
                99.0
                -741.0
                -742.0
                """, Load.run("simple/postfix", moduleLocation));
    }

    @Test
    void relational() throws IOException, InterruptedException {
        assertEquals(
                Load.gold(Path.of(moduleLocation, "simple/relational.txt").toString()),
                Load.run("simple/relational", moduleLocation)
        );
    }

    @Test
    void rosettaTemperatureConversion() throws IOException, InterruptedException {
        assertEquals(Load.run("simple/rosettaTemperatureConversion", moduleLocation), """
                K: 21.0
                C: -252.15
                F: -421.87003
                R: 37.8
                """);
    }

    @Test
    void unions() throws IOException, InterruptedException {
        assertEquals(
                Load.gold(Path.of(moduleLocation, "simple/unions.txt").toString()),
                Load.run("simple/unions", moduleLocation)
        );
    }


}
