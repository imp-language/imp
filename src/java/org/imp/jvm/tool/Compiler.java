package org.imp.jvm.tool;

import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.Util;
import org.imp.jvm.domain.SourceFile;
import org.imp.jvm.errors.Comptime;
import org.imp.jvm.visitors.PrettyPrinterVisitor;
import org.imp.jvm.visitors.TypeCheckVisitor;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record Compiler(List<Comptime.Data> errorData) {

    public Compiler() {
        this(new ArrayList<>());
    }

    /**
     * @return java class name ('.' separated) relative to the project root
     */
    public String compile(String projectRoot, String filename) throws FileNotFoundException, Comptime.MyError {

        String relativePath = FilenameUtils.getPath(filename);
        String name = FilenameUtils.getName(filename);

        var entry = API.parse(this, projectRoot, relativePath, name);
        Map<String, SourceFile> compilationSet = new HashMap<>();
        Comptime.killIfErrors(this, "Correct parser errors before continuing.");

        Timer.log("build dependency graph");

        TypeCheckVisitor typeCheckVisitor = new TypeCheckVisitor(this, entry.rootEnvironment, entry);
        entry.acceptVisitor(typeCheckVisitor);
        Timer.log("Type checking done");

        Comptime.killIfErrors(this, "Correct type errors before compilation can continue.");

        var pretty = new PrettyPrinterVisitor(entry.rootEnvironment);
        Util.println(pretty.print(entry.stmts));

        for (var s : API.compilationSet) {
            if (!compilationSet.containsKey(s.getFullRelativePath())) {
                compilationSet.put(s.getFullRelativePath(), s);
            }
        }

        API.buildProgram(this, compilationSet);
        Timer.log("generate bytecode");

        Timer.LOG = true;
        Timer.logTotalTime();

        String base = entry.getFullRelativePath();

        return base.replace("/", ".");
    }


}
