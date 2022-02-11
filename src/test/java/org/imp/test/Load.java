package org.imp.test;

import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.tool.Compiler;

import java.io.IOException;

public class Load {
    public static final String verificationPath = "verification/";

    static Compiler compiler = new Compiler();

    public static String run(String testPath) throws IOException, InterruptedException {
        String sourcePath = FilenameUtils.concat(verificationPath, testPath + ".imp");
        System.out.println(sourcePath);

        String className = compiler.compile(sourcePath);

        ProcessBuilder processBuilder = new ProcessBuilder(
                "java",
//                "-verbose:class",
                "--enable-preview",
                "-cp",
                ".compile" + System.getProperty("path.separator") + "../target/classes",
                className
        );

        processBuilder.inheritIO();
        Process process = processBuilder.start();

        int status = process.waitFor();
        if (status != 0) System.err.println("Process finished with exit code " + status);

        return "";
    }
}
