package org.imp.test;

import org.junit.jupiter.api.Test;

// Tests on `import` and `export` functionality
public class ModulesTest extends BaseTest {
    @Test
    void BST() {
        testLiteral("modules/main.imp", """
                3
                reee
                hello from nested
                184
                1024
                """);
    }

}
