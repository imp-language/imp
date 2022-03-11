package org.imp.test;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompilerTest {
    static String pwd = System.getProperty("user.dir");
    static String moduleLocation = Path.of(pwd, "verification").toString();

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
    void SecretTest() throws IOException, InterruptedException {
        assertEquals(Load.run("stdlib/SecretStuff", moduleLocation), """
                MICROSOFT!
                MICROSOFT!
                MICROSOFT!
                MICROSOFT!
                MICROSOFT!
                THE KILLER POKE!
                I wonder what this does?
                """);
    }
                     
    @Test
    void StdMath() throws IOException, InterruptedException {
        assertEquals(Load.run("stdlib/MathLibTest", moduleLocation), """
                MathLibTest
                0
                1
                0
                -1
                0
                1
                0
                -1
                0
                1
                0
                -1        
                """);
    }
                     
    @Test
    void ConstfTest() throws IOException, InterruptedException {
        assertEquals(Load.run("stdlib/Constants", moduleLocation), """
                2147483647
                -2147483648
                0
                1
                3.14159
                1.61803
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


}
