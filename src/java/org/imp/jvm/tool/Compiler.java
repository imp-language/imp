package org.imp.jvm.tool;

import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.Util;
import org.imp.jvm.domain.SourceFile;
import org.imp.jvm.errors.Comptime;
import org.imp.jvm.errors.MyError;
import org.imp.jvm.visitors.PrettyPrinterVisitor;
import org.imp.jvm.visitors.TypeCheckVisitor;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public record Compiler() {


    /**
     * @return java class name ('.' separated) relative to the project root
     */
    public String compile(String projectRoot, String filename) throws FileNotFoundException, MyError {
        Comptime.errors.clear(); // Todo: this should not be static

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
        TypeCheckVisitor typeCheckVisitor = new TypeCheckVisitor(entry.rootEnvironment, entry);
        entry.acceptVisitor(typeCheckVisitor);
        Timer.log("Type checking done");

        Comptime.killIfErrors("Correct type errors before compilation can continue.");

        var pretty = new PrettyPrinterVisitor(entry.rootEnvironment);
        Util.println(pretty.print(entry.stmts));

        for (var s : API.compilationSet) {
            if (!compilationSet.containsKey(s.getFullRelativePath())) {
                compilationSet.put(s.getFullRelativePath(), s);
            }
        }

        API.buildProgram(compilationSet);
        Timer.log("generate bytecode");

        Timer.LOG = true;
        Timer.logTotalTime();

        String base = entry.getFullRelativePath();

        return base.replace("/", ".");
    }


}
