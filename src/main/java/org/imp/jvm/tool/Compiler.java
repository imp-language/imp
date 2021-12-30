package org.imp.jvm.tool;

import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.Environment;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.errors.Comptime;
import org.imp.jvm.types.Type;
import org.imp.jvm.visitors.EnvironmentVisitor;
import org.imp.jvm.visitors.PrettyPrinterVisitor;
import org.imp.jvm.visitors.TypeCheckVisitor;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Compiler {

    public final String filename;

    public List<String> filenames;

    public Compiler(String filename) {

        this.filename = FilenameUtils.separatorsToUnix(filename);
    }

    public String compile() throws FileNotFoundException {
        File file = new File(filename);
        var sourceFile = API.parse(file);
        Timer.log("build dependency graph");
        // Todo: dependency graph stuff


        // 1. EnvironmentVisitor builds scopes and assigns
        // UnknownType or Literal types to expressions.
        var rootEnvironment = new Environment();
        List<Type> types = new ArrayList<>();
        EnvironmentVisitor environmentVisitor = new EnvironmentVisitor(rootEnvironment, file);
        for (var stmt : sourceFile.stmts) {
//            StandardTraversal.traverse(stmt, environmentVisitor, null);
            stmt.accept(environmentVisitor);
        }
        Timer.log("Environments created");
        Comptime.killIfErrors("Correct syntax errors before type checking can continue.");

        // 2. TypeCheckVisitor performs more advanced type unification.
        // a) Determine function return type based on type of expression returned.
        // b) Settle types of fields on structs
        // c) Error if multiple return statements in a function return different types.
        TypeCheckVisitor typeCheckVisitor = new TypeCheckVisitor(rootEnvironment, file);
        for (var stmt : sourceFile.stmts) {
            stmt.accept(typeCheckVisitor);
        }
        Timer.log("Type checking done");

        Comptime.killIfErrors("Correct type errors before compilation can continue.");

        var pretty = new PrettyPrinterVisitor(rootEnvironment);
        System.out.println(pretty.print(sourceFile.stmts));
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
