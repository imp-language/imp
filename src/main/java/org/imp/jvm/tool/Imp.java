package org.imp.jvm.tool;

import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.ImpFile;
import org.jgrapht.traverse.DepthFirstIterator;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class Imp {

    public final String filename;

    public List<String> filenames;

    public Imp(String filename) {
        this.filename = filename;
    }

    public void compile() {
        try {
            long start = System.nanoTime();
            // Walk the dependency tree
            var entry = ImpAPI.createSourceFile(filename);
            var dependencyGraph = ImpAPI.dependencyGraph(entry);
            entry.validate();

            // Reduce graph dependencies to unique set of compilation units
            Iterator<ImpFile> iterator = new DepthFirstIterator<>(dependencyGraph, entry);
            Map<String, ImpFile> compilationSet = new HashMap<>();
            while (iterator.hasNext()) {
                ImpFile impFile = iterator.next();
                if (!compilationSet.containsKey(impFile.packageName)) {
                    compilationSet.put(impFile.packageName, impFile);
                }
                System.out.println(impFile);
            }
            System.out.printf("Compiling %d files...%n", compilationSet.size());


            var program = ImpAPI.createProgram(compilationSet);
            long end = System.nanoTime();
            float runtime = ((float) (end - start)) / 1000000000;
            System.out.printf("Compiled %d files in %f seconds.", compilationSet.size(), runtime);
            System.out.println("");
            int result = ImpAPI.run("examples.scratch.Entry");

            // Compile each file
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.exit(0);

        try {
            // 0. Generate ASTs for the first file
            var entryFile = ImpAPI.createSourceFile(filename);
            // 1. Recursively generate ASTs for all files imported
            var imports = ImpAPI.gatherImports(entryFile);
            imports.put("main", entryFile);

            entryFile.validate();

            if (Logger.hasErrors()) {
                Logger.getSyntaxErrors().forEach(e -> System.out.println(e));
                System.out.println("Correct semantic errors before compilation can continue.");
                System.exit(1);
            }

            var program = ImpAPI.createProgram(imports);

            System.out.println("");
            int result = ImpAPI.run("examples.scratch.Entry");

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
