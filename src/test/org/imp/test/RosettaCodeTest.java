package org.imp.test;

import org.junit.jupiter.api.Test;

public class RosettaCodeTest extends BaseTest {
    @Test
    void Factorial() {
        testLiteral("rosetta/factorial.imp", """
                1
                1
                2
                2
                6
                6
                24
                24
                120
                120
                720
                720
                5040
                5040
                40320
                40320
                362880
                362880
                """);
    }

    @Test
    void rosettaTemperatureConversion() {
        testLiteral("rosetta/rosettaTemperatureConversion", """
                K: 21.0
                C: -252.15
                F: -421.87003
                R: 37.8
                """);
    }
}
