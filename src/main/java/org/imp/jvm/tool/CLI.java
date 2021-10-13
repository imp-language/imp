package org.imp.jvm.tool;

import picocli.CommandLine;

import java.io.File;
import java.io.IOException;

@CommandLine.Command(name = "imp")
public class CLI {

    @CommandLine.Parameters(description = "Source file to run.", arity = "0..1")
    private String filename;

    @CommandLine.Option(names = {"-c", "--compile"}, description = "Compile to Java class files.")
    private boolean compile;

    @CommandLine.Option(names = {"-b", "--bundle"}, description = "Bundle to JAR.")
    private boolean bundle;

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help message")
    boolean usageHelpRequested;


    public static void main(String[] args) throws IOException, InterruptedException {
        CLI cli = CommandLine.populateCommand(new CLI(), args);

        if (cli.usageHelpRequested) {
            CommandLine.usage(cli, System.out);
            return;
        } else if (cli.filename == null) {
            // Go into REPL mode
            REPL repl = new REPL();
            repl.start();
            return;
        }

        if (cli.compile) {
            System.out.println("Compiling.");
        } else if (cli.bundle) {
            System.out.println("Bundling.");
        } else {
            var imp = new Imp(cli.filename);
            Timer.LOG = true;
            var out = imp.compile();
            var entry = out.replace(File.separatorChar, '.');
            ImpAPI.run(entry);
        }
    }
}
