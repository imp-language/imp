package org.imp.test;

import org.imp.jvm.Util;
import org.imp.jvm.errors.Comptime;
import org.imp.jvm.tool.Compiler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BaseTest {
    static final String pwd = System.getProperty("user.dir");
    static final String moduleLocation = Path.of(pwd, "verification").toString();

    public Map<Integer, Long> checkForErrors(String testPath, String projectRoot) throws IOException {
        try {
            new Compiler().compile(projectRoot, testPath + ".imp");
        } catch (Comptime.CompilerError e) {
            var errorSet = e.errorData.stream().map(Comptime.Data::code).collect(Collectors.groupingBy(s -> s,
                    Collectors.counting()));
            System.err.println(errorSet);
            return errorSet;
        }

        return null;
    }

    protected String gold(String path) {
        String goldPath = Path.of(moduleLocation, path).toString();
        try {
            return Files.readString(Path.of(goldPath)).replaceAll("\\r\\n?", "\n");
        } catch (IOException e) {
            Util.exit("File at " + Path.of(goldPath) + " does not exist.", 1);
        }
        return null;
    }

    protected String run(String path) throws IOException, InterruptedException {
        Compiler compiler = new Compiler();
        String className = null;
        try {
            className = compiler.compile(moduleLocation, path + ".imp");
        } catch (Comptime.CompilerError e) {
            e.printStackTrace();
        }

        ProcessBuilder processBuilder = new ProcessBuilder(
                "java",
                "-cp",
                ".compile" + System.getProperty("path.separator") + "../target/classes",
                className
        );

        // cd into the verification project folder
        processBuilder.directory(new File(moduleLocation));

//        processBuilder.inheritIO();
        Process process = processBuilder.start();

        String stdout = new String(process.getInputStream().readAllBytes());
        Util.println(stdout);
        String stderr = new String(process.getErrorStream().readAllBytes());
        System.err.println(stderr);

        int status = process.waitFor();
        if (status != 0) System.err.println("Process finished with exit code " + status);

        return stdout.replaceAll("\\r\\n?", "\n");

    }

    protected void test(String goldPath, String runPath) throws IOException, InterruptedException {
        assertEquals(gold(goldPath), run(runPath));
    }

    protected void testLiteral(String runPath, String goldContent) {
        assertDoesNotThrow(() -> assertEquals(goldContent, run(runPath)));

    }
}
