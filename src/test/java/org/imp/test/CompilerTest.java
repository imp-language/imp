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
    void helloWorld() throws IOException, InterruptedException {
        assertEquals(Load.run("simple/HelloWorld", moduleLocation), """
                Hello, World!
                """);
    }

    @Test
    void lowercase() throws IOException, InterruptedException {
        assertEquals(Load.run("simple/lowercase", moduleLocation), """
                reeee
                """);
    }


}