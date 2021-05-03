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

import java.io.*;

public class Compiler {
    long startTime;

    public static void main(String[] args) throws IOException {
        new Compiler().compile(args[0]);
    }

    public void compile(String filename) throws IOException {

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
