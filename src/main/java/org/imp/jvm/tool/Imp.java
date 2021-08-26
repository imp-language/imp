package org.imp.jvm.tool;

import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.ImpFile;

import java.io.IOException;
import java.util.List;

public class Imp {

    public final String filename;

    public List<String> filenames;

    public Imp(String filename) {
        this.filename = filename;
    }

    public void compile() {
        try {
            // 0. Generate ASTs for the first file
            var entryFile = ImpAPI.createSourceFile(filename);
            // 1. Recursively generate ASTs for all files imported
            var imports = ImpAPI.gatherImports(entryFile);
            imports.put("main", entryFile);

            entryFile.validate();

            if (Logger.hasErrors()) {
                Logger.getSyntaxErrors().forEach(e -> System.out.println(e.getMessage()));
                System.out.println("Correct semantic errors before compilation can continue.");
                System.exit(1);
            }

            var program = ImpAPI.createProgram(imports);

            int result = ImpAPI.run("examples.scratch.Entry");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
