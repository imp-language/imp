package org.imp.jvm.tool.cli;

import org.imp.jvm.tool.Compiler;
import org.imp.jvm.tool.ExportTable;
import org.imp.jvm.tool.Timer;
import org.imp.jvm.tool.manifest.Manifest;
import picocli.CommandLine;

import java.io.FileNotFoundException;
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


    @Override
    public void run() {
        if (usageHelpRequested) {
            CommandLine.usage(this, System.out);
            return;
        }

        Manifest manifest;
        try {
            manifest = Manifest.get();
            assert manifest != null;

            String pwd = System.getProperty("user.dir");
            ExportTable.initDB(Path.of(pwd, ".compile", "imp.db"));

            // Connect to db
            ExportTable.connectDB(Path.of(pwd, ".compile", "imp.db"));

            var imp = new Compiler(manifest.entry());
            Timer.LOG = true;
            var out = imp.compile();
        } catch (FileNotFoundException e) {
            System.err.println("Manifest not found. Switch directories to an imp project, or run `imp new`.");
        }
    }

}

