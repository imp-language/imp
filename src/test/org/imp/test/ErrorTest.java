package org.imp.test;

import org.imp.jvm.Util;
import org.imp.jvm.errors.Comptime;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorTest {
    static final String pwd = System.getProperty("user.dir");
    static final String moduleLocation = Path.of(pwd, "verification").toString();


    @Test
    void ExpectError() throws IOException {
        assertEquals(
                Util.set(Comptime.ParameterTypeMismatch.code),
                Load.checkForErrors("errors/unions", moduleLocation)
        );
    }


}
