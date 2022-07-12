package org.imp.jvm.tool.cli;

import org.imp.jvm.Constants;
import org.imp.jvm.Util;
import org.imp.jvm.errors.Comptime;
import org.imp.jvm.tool.Compiler;
import picocli.CommandLine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

@CommandLine.Command(name = "imp", subcommands = {
        BuildCommand.class,
}, description = "Compile and run an imp program." +
        ""
)
public class CLI implements Runnable {

    @SuppressWarnings("unused")
    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help message")
    boolean usageHelpRequested;

    @CommandLine.Parameters(index = "0", description = "Entry file to compile and run.")
    String entry;


    public static void main(String[] args) {
        new CommandLine(new CLI()).execute(args);
    }

    /**
     * Run the compiled JVM class, including the Imp runtime
     * files.
     *
     * @param className filename
     * @throws IOException          if process cannot be started
     * @throws InterruptedException if process is interrupted
     */
    public void execute(String className) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(
                "java",
//                "-verbose:class",
                "--enable-preview",
                "-cp",
                ".compile" + System.getProperty("path.separator") + "target/classes", // Todo: runtime needs to go away
                className
        );
        processBuilder.inheritIO();
        Process process = processBuilder.start();

        int status = process.waitFor();
        if (status != 0) System.err.println("Process finished with exit code " + status);
    }

    @Override
    public void run() {
        if (usageHelpRequested) {
            CommandLine.usage(this, System.out);
            return;
        }
//        Timer.LOG = true;
        var imp = new Compiler();
        String pwd = System.getProperty("user.dir");

        String moduleLocation = Path.of(pwd).toString();

        String classPath = null;
        try {
            classPath = imp.compile(moduleLocation, entry);
        } catch (FileNotFoundException e) {
            System.err.println("Manifest.entry points to a file that does not exist.");
            System.exit(Constants.ENOENT);
        } catch (Comptime.CompilerError e) {
            Util.exit(e.getMessage(), 1);
        }

        try {
            execute(classPath);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

