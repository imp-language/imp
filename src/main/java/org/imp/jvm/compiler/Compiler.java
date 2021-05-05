package org.imp.jvm.compiler;

import org.imp.jvm.codegen.BytecodeGenerator;
import org.imp.jvm.codegen.BytecodeGenerator2;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.ImpFile2;

import org.imp.jvm.parsing.Parser;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.util.CheckClassAdapter;
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
            String outputClass = compiler.compile();
            if (!compiler.compile) {
                compiler.run(".compile Testmain");
            }
        }
    }

    public int run(String className) {
        int result = 0;

        try {
            System.out.println("command output:");
            Process proc = Runtime.getRuntime().exec("java -cp " + className);

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
        ImpFile2 impFile = Parser.getImpFile(source);


        saveByteCodeToClassFile(impFile);


        InputStream inputStream = new FileInputStream(".compile/Testmain.class");
        ClassReader classReader = new ClassReader(inputStream);
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS);
        ClassVisitor classVisitor = new CheckClassAdapter(classWriter, true);
        classReader.accept(classVisitor, 0);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        CheckClassAdapter.verify(new ClassReader(classWriter.toByteArray()), false, printWriter);
//        assertTrue(stringWriter.toString().isEmpty());

        return ".compile/Testmain.class";
    }

    public void saveByteCodeToClassFile(ImpFile2 impFile) throws IOException {
        BytecodeGenerator2 bytecodeGenerator = new BytecodeGenerator2();
        var byteUnits = bytecodeGenerator.generate(impFile);

        long endTime = System.currentTimeMillis();

        long duration = (endTime - startTime);
        System.out.printf("Duration: %.0f ms.", (float) duration);


        String className = impFile.getClassName();
        for (var byteUnit : byteUnits.entrySet()) {
            String qualifiedName = className + byteUnit.getKey();
            String fileName = ".compile/" + qualifiedName + ".class";
            OutputStream output = new FileOutputStream(fileName);
            output.write(byteUnit.getValue());
            output.flush();
            output.close();

        }
    }
}
