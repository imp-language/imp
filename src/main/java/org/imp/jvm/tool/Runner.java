package org.imp.jvm.tool;

import java.io.IOException;

public class Runner {

    /**
     * Run the compiled JVM class, including the Imp runtime
     * files.
     *
     * @param className filename
     * @throws IOException          if process cannot be started
     * @throws InterruptedException if process is interrupted
     */
    public static void run(String className) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(
                "java",
//                "-verbose:class",
                "--enable-preview",
                "-cp",
                ".compile" + System.getProperty("path.separator") + "target/classes",
                className
        );
        processBuilder.inheritIO();
        Process process = processBuilder.start();

        process.waitFor();
    }

    // Todo: migrate Verifier to use Runner.run() instead of Runner.spawn()
    public static Process spawn(String className) throws IOException {
        String cmd = "java --enable-preview -cp .compile" + System.getProperty("path.separator") + "target/classes " + className;
        Process proc = Runtime.getRuntime().exec(cmd);
        return proc;
    }
}
