package org.imp.test;

import org.imp.jvm.tool.Compiler;

import java.io.File;
import java.io.IOException;

public class Load {
    public static final String verificationPath = "verification/";

    static Compiler compiler = new Compiler();

    public static String run(String testPath, String projectRoot) throws IOException, InterruptedException {
        System.out.println("projectRoot: " + projectRoot);
//        String pwd = System.getProperty("user.dir");

        String className = compiler.compile(testPath + ".imp", projectRoot);
        System.out.println("className: " + className);

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
}
