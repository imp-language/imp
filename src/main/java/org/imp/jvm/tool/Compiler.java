package org.imp.jvm.tool;

import org.imp.jvm.compiler.BytecodeGenerator;
import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.parsing.Parser;
import picocli.CommandLine;
import picocli.CommandLine.Option;

import java.io.*;

@CommandLine.Command(name = "imp")
public class Compiler {
    @CommandLine.Parameters(index = "0", description = "Source file to compile.")
    private String filename;

    @Option(names = {"-c", "--compile"}, description = "Compile instead of running.")
    private boolean compile;

    @Option(names = {"-v", "--version"}, versionHelp = true, description = "display version info")
    boolean versionInfoRequested;

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "display this help message")
    boolean usageHelpRequested;


    long startTime;

    public static void main(String[] args) throws IOException {
        Compiler compiler = CommandLine.populateCommand(new Compiler(), args);
        if (compiler.usageHelpRequested) {
            CommandLine.usage(compiler, System.out);
            return;
        } else {
            String outputClassName = compiler.compile();
            if (!compiler.compile) {
                compiler.run(outputClassName);
            }
        }
    }

    public Process spawn(String className) throws IOException {
        String cmd = "java --enable-preview -cp .compile;target/classes " + className;
        Process proc = Runtime.getRuntime().exec(cmd);
        return proc;
    }

    public int run(String className) throws IOException {
        String cmd = "java --enable-preview -cp .compile;target/classes " + className;
        Process proc = Runtime.getRuntime().exec(cmd);

        // 3. Watch standard out
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));

        String s = null;
        String b = "some\ntext\tbitch";
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }

        System.out.println("\nErrors (if any):");
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }
        System.out.println("");

        return 0;
    }

    public String compile() throws IOException {
        File source = new File(filename);

        startTime = System.currentTimeMillis();

        // "registration" pass for custom types
        ImpFile ast = Parser.getAbstractSyntaxTree(source);


        Logger.killIfErrors("Correct parse errors before type checking and compilation can continue.");

        // "validation" pass for custom types
        ast.validate();

        Logger.killIfErrors("Correct semantic errors before compilation can continue.");

        saveByteCodeToClassFile(ast);


        Logger.killIfErrors("Errored during bytecode generation.");


        return ast.getClassName() + "/Entry";
    }

    public void saveByteCodeToClassFile(ImpFile impFile) throws IOException {
        BytecodeGenerator bytecodeGenerator = new BytecodeGenerator();
        var byteUnits = bytecodeGenerator.generate(impFile);

        long endTime = System.currentTimeMillis();

        long duration = (endTime - startTime);
        System.out.printf("Duration: %.0f ms.\n", (float) duration);


        String className = impFile.getClassName();
        for (var byteUnit : byteUnits.entrySet()) {
            String qualifiedName = className;
            if (!byteUnit.getKey().equals("main")) {
                qualifiedName = byteUnit.getKey();
            }
            String fileName = ".compile/" + qualifiedName + ".class";


            File tmp = new File(fileName);
            tmp.getParentFile().mkdirs();


            OutputStream output = new FileOutputStream(fileName);
            output.write(byteUnit.getValue());
            output.flush();
            output.close();

        }
    }
}
