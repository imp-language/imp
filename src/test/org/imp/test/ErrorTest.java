package org.imp.test;

import org.imp.jvm.Util;
import org.imp.jvm.errors.Comptime;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ErrorTest extends BaseTest {

    @Test
    void MatchCoverage() throws IOException {
        assertEquals(
                Util.countedSet(Comptime.MatchCoverage.code),
                checkForErrors("errors/matchCoverage", moduleLocation)
        );
    }

    @Test
    void MutabilityError() throws IOException {
        assertEquals(
                Util.countedSet(Comptime.MutabilityError.code),
                checkForErrors("errors/mutabilityError", moduleLocation)
        );
    }

    @Test
    void ParameterTypeMismatch() throws IOException {
        assertEquals(
                Util.countedSet(
                        Comptime.ParameterTypeMismatch.code,
                        Comptime.ParameterTypeMismatch.code,
                        Comptime.ParameterTypeMismatch.code,
                        Comptime.ParameterTypeMismatch.code
                ),
                checkForErrors("errors/parameterTypeMismatch", moduleLocation)
        );
    }

    @Test
    void Unreachable() throws IOException {
        assertEquals(
                Util.countedSet(Comptime.Unreachable.code, Comptime.Unreachable.code, Comptime.Unreachable.code),
                checkForErrors("errors/unreachable", moduleLocation)
        );
    }

    @Test
    void VoidUsage() throws IOException {
        assertEquals(
                Util.countedSet(Comptime.VoidUsage.code),
                checkForErrors("errors/voidUsage", moduleLocation)
        );
    }


}
