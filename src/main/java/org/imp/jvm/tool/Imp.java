package org.imp.jvm.tool;

import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.domain.ImpFile;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
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
        long start = System.nanoTime();
        // Walk the dependency tree
        var entry = ImpAPI.createSourceFile(filename);
        Graph<ImpFile, DefaultEdge> dependencyGraph = null;
        dependencyGraph = ImpAPI.dependencyGraph(entry);
        Timer.log("build dependency graph");
        entry.validate();
        Timer.log("validate entry file");


        // Reduce graph dependencies to unique set of compilation units
        Iterator<ImpFile> iterator = new DepthFirstIterator<>(dependencyGraph, entry);
        Map<String, ImpFile> compilationSet = new HashMap<>();
        while (iterator.hasNext()) {
            ImpFile impFile = iterator.next();
            if (!compilationSet.containsKey(impFile.packageName)) {
                compilationSet.put(impFile.packageName, impFile);
            }
        }

        Timer.log("create compilation set");


        var program = ImpAPI.createProgram(compilationSet);
        Timer.logTotalTime();

        return entry.getClassName() + "/" + "Entry";

    }


}
