package org.imp.test;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompilerTest {
    @Test
    void helloWorld() throws IOException, InterruptedException {
        Load.run("simple/helloWorld");
        assertEquals(4, 4);
        // Todo(Current): finish testing framework
    }
}