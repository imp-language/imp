package org.imp.jvm.tool;

import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.Stmt;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Compiler {

    public final String filename;

    public List<String> filenames;

    public Compiler(String filename) {

        this.filename = FilenameUtils.separatorsToUnix(filename);
    }

    public String compile() throws FileNotFoundException {
        File file = new File(filename);
        var entry = API.parse(file);
        Graph<SourceFile, DefaultEdge> dependencyGraph = API.dependencyGraph(entry);
        Comptime.killIfErrors("Correct dependency errors before continuing.");
        Timer.log("build dependency graph");

        // Todo: Before the TypeCheckVisitor runs, we need to
        // add all qualified imports to the root environment

        System.out.println("Imports:");
        entry.filter(Stmt.Import.class, (importStmt) -> {
            System.out.println(importStmt);
            return null;
        });
        // Todo: move this stuff (adding qualified imports to the root environment)
        // to the dependency graph shit
        for (var path : entry.imports.keySet()) {
            var moduleName = FilenameUtils.getBaseName(path);
            var imported = entry.imports.get(path);
            for (var key : imported.exports.keySet()) {
                var value = imported.exports.get(key);
                var qualifiedName = moduleName + "." + key;
                System.out.println(qualifiedName);
                entry.rootEnvironment.addVariable(qualifiedName, value);
            }
        }

        // 2. TypeCheckVisitor performs more advanced type unification.
        // a) Determine function return type based on type of expression returned.
        // b) Settle types of fields on structs
        // c) Error if multiple return statements in a function return different types.
        TypeCheckVisitor typeCheckVisitor = new TypeCheckVisitor(entry.rootEnvironment, file);
        entry.acceptVisitor(typeCheckVisitor);
        Timer.log("Type checking done");

        Comptime.killIfErrors("Correct type errors before compilation can continue.");

        var pretty = new PrettyPrinterVisitor(entry.rootEnvironment);
        System.out.println(pretty.print(entry.stmts));

//        var astPrint = new ASTPrinterVisitor();
//        System.out.println(astPrint.print(entry.stmts));
//        CodegenVisitor codegenVisitor = new CodegenVisitor(staticScope);

        return "ree";
    }


    public String compileold() throws FileNotFoundException {
        // Walk the dependency tree
        var entry = API.createSourceFile(filename);
        Graph<ImpFile, DefaultEdge> dependencyGraph = null;
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
        Timer.LOG = true;
        Timer.logTotalTime();

        return entry.getClassName() + "/" + "Entry";

    }


}
