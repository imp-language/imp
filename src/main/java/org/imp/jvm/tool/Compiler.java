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

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
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
    public String compile(String filename, String projectRoot) throws FileNotFoundException {
        String fullPath = Path.of(projectRoot, filename).toString();
        fullPath = FilenameUtils.separatorsToUnix(fullPath);
//        filename = FilenameUtils.separatorsToUnix(filename);
//        Timer.LOG = false;
        String pwd = System.getProperty("user.dir");

        File file = new File(fullPath);
        var entry = API.parse(file, filename, projectRoot);
        Graph<SourceFile, DefaultEdge> dependencyGraph = API.dependencyGraph(entry);
        Comptime.killIfErrors("Correct dependency errors before continuing.");

        Timer.log("build dependency graph");

        // 2. TypeCheckVisitor performs more advanced type unification.
        // a) Determine function return type based on type of expression returned.
        // b) Settle types of fields on structs
        // c) Error if multiple return statements in a function return different types.
        TypeCheckVisitor typeCheckVisitor = new TypeCheckVisitor(entry.rootEnvironment, file);
        entry.acceptVisitor(typeCheckVisitor);
        Timer.log("Type checking done");

        Comptime.killIfErrors("Correct type errors before compilation can continue.");

        var pretty = new PrettyPrinterVisitor(entry.rootEnvironment);
//        Util.println(pretty.print(entry.stmts));

//        var astPrint = new ASTPrinterVisitor();
//        CodegenVisitor codegenVisitor = new CodegenVisitor(staticScope);

        Iterator<SourceFile> iterator = new DepthFirstIterator<>(dependencyGraph, entry);
        Map<String, SourceFile> compilationSet = new HashMap<>();
        while (iterator.hasNext()) {
            SourceFile impFile = iterator.next();
            if (!compilationSet.containsKey(impFile.path())) {
                compilationSet.put(impFile.path(), impFile);
            }
        }

        API.buildProgram(compilationSet, projectRoot);
        Timer.log("generate bytecode");

        Timer.LOG = true;
        Timer.logTotalTime();

        String base = FilenameUtils.separatorsToUnix(FilenameUtils.removeExtension(entry.base()));

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
