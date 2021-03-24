package main.java.imp.compiler;

import main.java.imp.domain.CompilationUnit;

public class Compiler {

    public static void main(String[] args) {
        new Compiler().compile(args);
    }

    public void compile(String[] args) {
        CompilationUnit compilationUnit = null;
        saveByteCodeToClassFile(compilationUnit);
    }

    public void saveByteCodeToClassFile(CompilationUnit compilationUnit) {

    }
}
