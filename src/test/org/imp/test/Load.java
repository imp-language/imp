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

// Todo: move to BaseTest class an delete this
public class Load {

    static final Compiler compiler = new Compiler();

    public static String run(String testPath, String projectRoot) throws IOException, InterruptedException {
        String className = null;
        try {
            className = compiler.compile(projectRoot, testPath + ".imp");
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
        processBuilder.directory(new File(projectRoot));

//        processBuilder.inheritIO();
        Process process = processBuilder.start();

        String stdout = new String(process.getInputStream().readAllBytes());
        System.out.println(stdout);
        String stderr = new String(process.getErrorStream().readAllBytes());
        System.err.println(stderr);

        int status = process.waitFor();
        if (status != 0) System.err.println("Process finished with exit code " + status);

        return stdout.replaceAll("\\r\\n?", "\n");
    }

    public static Map<Integer, Long> checkForErrors(String testPath, String projectRoot) throws IOException {
        try {
            new Compiler().compile(projectRoot, testPath + ".imp");
        } catch (Comptime.CompilerError e) {
            var errorSet = e.errorData.stream().map(Comptime.Data::code).collect(Collectors.groupingBy(s -> s,
                    Collectors.counting()));
            System.out.println(errorSet);
            return errorSet;
        }

        return null;
    }

    public static String gold(String goldPath) {
        try {
            return Files.readString(Path.of(goldPath)).replaceAll("\\r\\n?", "\n");
        } catch (IOException e) {
            Util.exit("File at " + Path.of(goldPath) + " does not exist.", 1);
        }
        return null;
    }
}
