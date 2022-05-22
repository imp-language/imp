package org.imp.test;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseTest {
    static final String pwd = System.getProperty("user.dir");
    static final String moduleLocation = Path.of(pwd, "verification").toString();

    protected String gold(String path) {
        return Load.gold(Path.of(moduleLocation, path).toString());
    }

    protected String run(String path) throws IOException, InterruptedException {
        return Load.run(path, moduleLocation);
    }

    protected void test(String goldPath, String runPath) throws IOException, InterruptedException {
        assertEquals(gold(goldPath), run(runPath));
    }

    protected void testLiteral(String runPath, String goldContent) {
        assertDoesNotThrow(() -> {
            assertEquals(goldContent, run(runPath));
        });

    }
}
