package org.imp.jvm.tool;

import picocli.CommandLine;

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

        if (cli.usageHelpRequested || cli.filename == null) {
            CommandLine.usage(cli, System.out);
            return;
        }

        if (cli.compile) {
            System.out.println("Compiling.");
        } else if (cli.bundle) {
            System.out.println("Bundling.");
        } else {
            var imp = new Imp(cli.filename);
            imp.compile();
            ImpAPI.run("examples.scratch.Entry");
        }
    }
}
