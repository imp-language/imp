package org.imp.test;

import org.imp.jvm.tool.Compiler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Load {

    static final Compiler compiler = new Compiler();

    public static String run(String testPath, String projectRoot) throws IOException, InterruptedException {
        String className = compiler.compile(projectRoot, testPath + ".imp");

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

    public static String gold(String goldPath) {
        try {
            return Files.readString(Path.of(goldPath)).replaceAll("\\r\\n?", "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
