package org.imp.jvm.tool;

import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.SourceFile;
import org.imp.jvm.errors.Comptime;
import org.imp.jvm.visitors.PrettyPrinterVisitor;
import org.imp.jvm.visitors.TypeCheckVisitor;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public record Compiler() {


    /**
     * @param filename
     * @param projectRoot
     * @return java class name ('.' separated) relative to the project root
     * @throws FileNotFoundException
     */
    public String compile(String projectRoot, String filename) throws FileNotFoundException {

        String relativePath = FilenameUtils.getPath(filename);
        String name = FilenameUtils.getName(filename);

        var entry = API.parse(projectRoot, relativePath, name);
        Map<String, SourceFile> compilationSet = new HashMap<>();
//        Graph<SourceFile, DefaultEdge> dependencyGraph = API.dependencyGraph(entry);
        Comptime.killIfErrors("Correct parser errors before continuing.");

        Timer.log("build dependency graph");

        // 2. TypeCheckVisitor performs more advanced type unification.
        // a) Determine function return type based on type of expression returned.
        // b) Settle types of fields on structs
        // c) Error if multiple return statements in a function return different types.
        TypeCheckVisitor typeCheckVisitor = new TypeCheckVisitor(entry.rootEnvironment, entry.file);
        entry.acceptVisitor(typeCheckVisitor);
        Timer.log("Type checking done");

        Comptime.killIfErrors("Correct type errors before compilation can continue.");

        var pretty = new PrettyPrinterVisitor(entry.rootEnvironment);
//        Util.println(pretty.print(entry.stmts));

//        var astPrint = new ASTPrinterVisitor();
//        CodegenVisitor codegenVisitor = new CodegenVisitor(staticScope);

//        Iterator<SourceFile> iterator = new DepthFirstIterator<>(dependencyGraph, entry);
//        while (iterator.hasNext()) {
//            SourceFile impFile = iterator.next();
//            if (!compilationSet.containsKey(impFile.getFullRelativePath())) {
//                compilationSet.put(impFile.getFullRelativePath(), impFile);
//            }
//        }

        for (var s : API.compilationSet) {
            if (!compilationSet.containsKey(s.getFullRelativePath())) {
                compilationSet.put(s.getFullRelativePath(), s);
            }
        }

        API.buildProgram(compilationSet, projectRoot);
        Timer.log("generate bytecode");

        Timer.LOG = true;
        Timer.logTotalTime();

        String base = entry.getFullRelativePath();

        return base.replace("/", ".");
    }


    public String compileOld() throws FileNotFoundException {
        // Walk the dependency tree
        var entry = API.createSourceFile("filename");
        Graph<ImpFile, DefaultEdge> dependencyGraph;
        dependencyGraph = API.dependencyGraph(entry);
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

        var program = API.createProgram(compilationSet);
        Timer.log("generate bytecode");
        Timer.logTotalTime();

        return entry.getClassName() + "/" + "Entry";

    }


}
