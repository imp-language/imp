package org.imp.jvm.tool.cli;

import org.imp.jvm.tool.Compiler;
import org.imp.jvm.tool.ExportTable;
import org.imp.jvm.tool.Timer;
import org.imp.jvm.tool.manifest.Manifest;
import picocli.CommandLine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

@CommandLine.Command(name = "imp", subcommands = {
        NewCommand.class,
        BuildCommand.class,
}, description = "Compile and run an imp program." +
        ""
)
public class CLI implements Runnable {

    //    @CommandLine.Parameters(description = "Source file to run.", arity = "0..1")
//    private String filename;
    //
//    @CommandLine.Option(names = {"-c", "--compile"}, description = "Compile to Java class files.")
//    private boolean compile;
//
//    @CommandLine.Option(names = {"-b", "--bundle"}, description = "Bundle to JAR.")
//    private boolean bundle;
//
//    @CommandLine.Option(names = {"-s", "--silent"}, description = "Don't print any debug messages.")
//    private boolean silent;
//
    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help message")
    boolean usageHelpRequested;


    public static void main(String[] args) {
        int result = new CommandLine(new CLI()).execute(args);
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
                ".compile" + System.getProperty("path.separator") + "../target/classes",
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

        Manifest manifest = null;
        try {
            manifest = Manifest.get();
            assert manifest != null;
        } catch (FileNotFoundException e) {
            System.err.println("Manifest not found. Switch directories to an imp project, or run `imp new`.");
            System.exit(UnixErrors.ENOENT);
        }
        String pwd = System.getProperty("user.dir");
        ExportTable.initDB(Path.of(pwd, ".compile", "imp.db"));

        // Connect to db
        ExportTable.connectDB(Path.of(pwd, ".compile", "imp.db"));

        String moduleLocation = Path.of(pwd).toString();

        var imp = new Compiler();
        Timer.LOG = true;
        String classPath = null;
        try {
            classPath = imp.compile(moduleLocation, manifest.entry());
        } catch (FileNotFoundException e) {
            System.err.println("Manifest.entry points to a file that does not exist.");
            System.exit(UnixErrors.ENOENT);
        }

        try {
            execute(classPath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

