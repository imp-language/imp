package org.imp.jvm.compiler;

import org.imp.jvm.codegen.BytecodeGenerator;
import org.imp.jvm.domain.ImpFile;

import org.imp.jvm.parser.Parser;
import org.objectweb.asm.Opcodes;

import java.io.*;

public class Compiler {

    public static void main(String[] args) throws IOException {
        new Compiler().compile(args[0]);
    }

    public void compile(String filename) throws IOException {
        System.out.println("yeee");
        System.out.println(Opcodes.DUP_X1);

        File source = new File(filename);
        ImpFile impFile = Parser.getImpFile(source);


        saveByteCodeToClassFile(impFile);

    }

    public void saveByteCodeToClassFile(ImpFile impFile) throws IOException {
        BytecodeGenerator bytecodeGenerator = new BytecodeGenerator();
        var byteUnits = bytecodeGenerator.generate(impFile);

        String className = impFile.getClassName();
        for (var byteUnit : byteUnits.entrySet()) {
            String qualifiedName = className + byteUnit.getKey();
            String fileName = ".compile/" + qualifiedName + ".txt";
            OutputStream output = new FileOutputStream(fileName);
            output.write(byteUnit.getValue());
            output.flush();
            output.close();

        }
    }
}
