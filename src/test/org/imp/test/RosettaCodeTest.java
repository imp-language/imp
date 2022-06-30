package org.imp.test;

import org.junit.jupiter.api.Test;

// Tests that may be from Rosetta Code or a similar aggregator
public class RosettaCodeTest extends BaseTest {
    @Test
    void BST() {
        testLiteral("rosetta/binaryTree.imp", """
                Tree[data=0, left=Tree[data=-2, left=Tree[data=-3, left=Empty[], right=Empty[]], right=Tree[data=-1, left=Empty[], right=Empty[]]], right=Tree[data=1, left=Empty[], right=Empty[]]]
                """);
    }

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
    void fibonacci() {
        testLiteral("rosetta/fibonacci", """
                0
                0
                1
                1
                2
                2
                3
                3
                5
                5
                8
                8
                13
                13
                21
                21
                34
                34
                55
                55
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
