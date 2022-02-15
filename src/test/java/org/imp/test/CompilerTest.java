package org.imp.test;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {
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
        assertEquals(Load.run("simple/HelloWorld", moduleLocation), """
                Hello, World!
                """);
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