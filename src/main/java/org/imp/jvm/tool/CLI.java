package org.imp.jvm.tool;

import picocli.CommandLine;

import java.io.FileNotFoundException;
import java.io.IOException;

@CommandLine.Command(name = "imp")
public class CLI {

    @CommandLine.Parameters(description = "Source file to run.", arity = "0..1")
    private String filename;

    @CommandLine.Option(names = {"-c", "--compile"}, description = "Compile to Java class files.")
    private boolean compile;

    @CommandLine.Option(names = {"-b", "--bundle"}, description = "Bundle to JAR.")
    private boolean bundle;

    @CommandLine.Option(names = {"-s", "--silent"}, description = "Don't print any debug messages.")
    private boolean silent;

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help message")
    boolean usageHelpRequested;


    public static void main(String[] args) {
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
            cli.build();
        } else if (cli.bundle) {
            System.out.println("Bundling.");
        } else {

            if ("init".equals(cli.filename)) {
                cli.initNewProject();
            } else {
                cli.run();
            }
        }


    }

    private void initNewProject() {
        System.out.println("reee");
    }


    private void run() {
        var imp = new Compiler(this.filename);
        if (!this.silent) {
//            Timer.LOG = true;
        }
        String out = null;
        try {
            out = imp.compile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Runner.run(out);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void build() {
        var imp = new Compiler(this.filename);
        Timer.LOG = true;
        try {
            var out = imp.compile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
