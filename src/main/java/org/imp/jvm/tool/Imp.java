package org.imp.jvm.tool;

import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.domain.ImpFile;
import org.jgrapht.traverse.DepthFirstIterator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Imp {

    public final String filename;

    public List<String> filenames;

    public Imp(String filename) {

        this.filename = FilenameUtils.separatorsToUnix(filename);
    }


    public String compile() {
        try {
            long start = System.nanoTime();
            // Walk the dependency tree
            var entry = ImpAPI.createSourceFile(filename);
            var dependencyGraph = ImpAPI.dependencyGraph(entry);
            ImpAPI.log("build dependency graph");
            entry.validate();
            ImpAPI.log("validate entry file");


            // Reduce graph dependencies to unique set of compilation units
            Iterator<ImpFile> iterator = new DepthFirstIterator<>(dependencyGraph, entry);
            Map<String, ImpFile> compilationSet = new HashMap<>();
            while (iterator.hasNext()) {
                ImpFile impFile = iterator.next();
                if (!compilationSet.containsKey(impFile.packageName)) {
                    compilationSet.put(impFile.packageName, impFile);
                }
            }

            ImpAPI.log("create compilation set");


            var program = ImpAPI.createProgram(compilationSet);
            ImpAPI.logTotalTime();

            return entry.getClassName() + "/" + "Entry";

            // Compile each file
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.exit(0);
//
//        try {
//            // 0. Generate ASTs for the first file
//            var entryFile = ImpAPI.createSourceFile(filename);
//            // 1. Recursively generate ASTs for all files imported
//            var imports = ImpAPI.gatherImports(entryFile);
//            imports.put("main", entryFile);
//
//            entryFile.validate();
//
//            if (Logger.hasErrors()) {
//                Logger.getSyntaxErrors().forEach(e -> System.out.println(e));
//                System.out.println("Correct semantic errors before compilation can continue.");
//                System.exit(1);
//            }
//
//            var program = ImpAPI.createProgram(imports);
//
//            System.out.println("");
//            int result = ImpAPI.run("examples.scratch.Entry");
//
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
        return "nope";

    }


}
