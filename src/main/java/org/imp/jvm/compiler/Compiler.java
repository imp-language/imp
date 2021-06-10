package org.imp.jvm.compiler;

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
                compiler.run(".compile " + outputClassName);
            }
        }
    }

    public int run(String className) {
        int result = 0;

        try {
            System.out.println("command output:");
            String cmd = "java -cp .compile " + className;
            Process proc = Runtime.getRuntime().exec(cmd);

            InputStream errin = proc.getErrorStream();
            InputStream in = proc.getInputStream();
            BufferedReader errorOutput = new BufferedReader(new InputStreamReader(errin));
            BufferedReader output = new BufferedReader(new InputStreamReader(in));
            String line1 = null;
            String line2 = null;
            try {
                while ((line1 = errorOutput.readLine()) != null ||
                        (line2 = output.readLine()) != null) {
                    if (line1 != null) System.out.println(line1);
                    if (line2 != null) System.out.println(line2);
                }//end while
                errorOutput.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }//end catc
            result = proc.waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("IOException raised: " + e.getMessage());
        }
        return result;
    }

    public String compile() throws IOException {
        File source = new File(filename);

        startTime = System.currentTimeMillis();

        // "registration" pass for custom types
        ImpFile ast = Parser.getAbstractSyntaxTree(source);

        if (Logger.getSyntaxErrors().size() > 0) {
            Logger.getSyntaxErrors().forEach(e -> System.out.println(e.getMessage()));
            System.out.println("Correct parse errors before type checking and compilation can continue.");
            System.exit(1);
        }

        // "validation" pass for custom types
        ast.validate();

        if (Logger.getSyntaxErrors().size() > 0) {
            Logger.getSyntaxErrors().forEach(e -> System.out.println(e.getMessage()));
            System.out.println("Correct semantic errors before compilation can continue.");
            System.exit(1);
        }

        saveByteCodeToClassFile(ast);


//        InputStream inputStream = new FileInputStream(".compile/" + impFile.name + ".class");
//        ClassReader classReader = new ClassReader(inputStream);
//        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
//        ClassVisitor classVisitor = new CheckClassAdapter(classWriter, true);
//        classReader.accept(classVisitor, 0);
//
//        StringWriter stringWriter = new StringWriter();
//        PrintWriter printWriter = new PrintWriter(stringWriter);
//        CheckClassAdapter.verify(new ClassReader(classWriter.toByteArray()), false, printWriter);
//        assertTrue(stringWriter.toString().isEmpty());

        return ast.getClassName() + "/Entry";
    }

    public void saveByteCodeToClassFile(ImpFile impFile) throws IOException {
        BytecodeGenerator bytecodeGenerator = new BytecodeGenerator();
        var byteUnits = bytecodeGenerator.generate(impFile);

        long endTime = System.currentTimeMillis();

        long duration = (endTime - startTime);
        System.out.printf("Duration: %.0f ms.", (float) duration);


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
