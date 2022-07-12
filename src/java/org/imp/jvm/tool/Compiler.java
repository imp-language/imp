package org.imp.jvm.tool;

import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.BytecodeGenerator;
import org.imp.jvm.Util;
import org.imp.jvm.domain.SourceFile;
import org.imp.jvm.errors.Comptime;
import org.imp.jvm.parser.Stmt;
import org.imp.jvm.types.FuncType;
import org.imp.jvm.types.ImpType;
import org.imp.jvm.visitors.EnvironmentVisitor;
import org.imp.jvm.visitors.PrettyPrinterVisitor;
import org.imp.jvm.visitors.TypeCheckVisitor;
import org.javatuples.Pair;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record Compiler(List<Comptime.Data> errorData, Map<String, SourceFile> compilationSet, boolean developmentMode) {


    public Compiler() {
        this(new ArrayList<>(), new HashMap<>(), true);
    }

    /**
     * @return java class name ('.' separated) relative to the project root
     */
    public String compile(String projectRoot, String filename) throws FileNotFoundException, Comptime.CompilerError {

        Timer.log("start compiler");
        String relativePath = FilenameUtils.getPath(filename);
        String name = FilenameUtils.getName(filename);

        var entry = parse(projectRoot, relativePath, name);
        Comptime.killIfErrors(this, "Correct parser errors before continuing.");
        Timer.log("build dependency graph");

        // We've built the parse tree, now we can run environment visitor on each.
        for (String filePath : compilationSet.keySet()) {
            var source = compilationSet.get(filePath);
            Timer.log("Build environment for source file `" + source.name + "`");
            EnvironmentVisitor environmentVisitor = new EnvironmentVisitor(this, source.rootEnvironment, source);
            source.acceptVisitor(environmentVisitor);

            for (Stmt stmt : source.stmts) {
                if (stmt instanceof Stmt.Export exportStmt) {
                    if (exportStmt.stmt instanceof Stmt.Exportable exportable) {
                        String identifier = exportable.identifier();
                        ImpType type = source.rootEnvironment.getVariable(identifier);
                        if (type != null) {
                            source.exports.put(identifier, type);
                        }
                    }

                }
            }

            Comptime.killIfErrors(this, "Correct syntax errors before type checking can continue.");
        }

        // Add imported members to scopes
        // Todo: add a "ResolveImports" pass to allow for better errors on imported members.
        //  It'd basically be a stripped-down EnvironmentVisitor.
        for (String filePath : compilationSet.keySet()) {
            var source = compilationSet.get(filePath);
            for (String s : source.imports.keySet()) {
                var sourceFile = source.imports.get(s);
                var exportedMembers = sourceFile.exports;
                for (String exportedName : exportedMembers.keySet()) {
                    var exportedType = exportedMembers.get(exportedName);
                    var qualifiedName = sourceFile.name + "::" + exportedName;
                    if (exportedType instanceof FuncType ft) {
                        ft.external = sourceFile;
                    }
                    source.rootEnvironment.addVariable(qualifiedName, exportedType);
                }
            }

        }

        // Typecheck all members
        for (String filePath : compilationSet.keySet()) {
            var source = compilationSet.get(filePath);
            Timer.log("Type checking source file `" + source.name + "`");
            TypeCheckVisitor typeCheckVisitor = new TypeCheckVisitor(this, source.rootEnvironment, source);
            source.acceptVisitor(typeCheckVisitor);

            Comptime.killIfErrors(this, "Correct type errors before compilation can continue.");

        }

        Timer.log("Type checking done");

        if (Util.DEBUG) {
            var pretty = new PrettyPrinterVisitor(entry.rootEnvironment);
            Util.println(pretty.print(entry.stmts));
        }

        output(compilationSet);
        Timer.log("generate bytecode");

        Timer.LOG = true;
        Timer.logTotalTime();

        String base = entry.getFullRelativePath();

        return base.replace("/", ".");
    }

    public void output(Map<String, ? extends SourceFile> compilationSet) throws Comptime.CompilerError {

        BytecodeGenerator bytecodeGenerator = new BytecodeGenerator();
        for (var key : compilationSet.keySet()) {
            var source = compilationSet.get(key);
            var allByteUnits = bytecodeGenerator.generate(this, source);
            Comptime.killIfErrors(this, "Correct build errors before compilation can complete.");

            // Generate outer class
            var byteUnit = allByteUnits.getValue0().toByteArray();
            String base = FilenameUtils.removeExtension(source.getFullRelativePath());
            String fileName = Path.of(source.projectRoot, ".compile", base + ".class").toString();
            File tmp = new File(fileName);
            //noinspection ResultOfMethodCallIgnored
            tmp.getParentFile().mkdirs();
            OutputStream output;
            try {
                output = new FileOutputStream(fileName);
                output.write(byteUnit);
                output.close();
            } catch (IOException e) {
                System.err.println("The above call to mkdirs() should have worked.");
                System.exit(9);
            }

            // Generate inner classes
            for (var p : allByteUnits.getValue1().entrySet()) {

                var st = p.getKey();
                var innerCw = p.getValue();

                String innerName = source.getFullRelativePath() + "$" + st.name;

                fileName = Path.of(source.projectRoot, ".compile", innerName + ".class").toString();
                tmp = new File(fileName);
                //noinspection ResultOfMethodCallIgnored
                tmp.getParentFile().mkdirs();
                try {
                    output = new FileOutputStream(fileName);
                    output.write(innerCw.toByteArray());
                    output.close();
                } catch (IOException e) {
                    System.err.println("The above call to mkdirs() should have worked.");
                    System.exit(9);
                }
            }

        }

    }

    /**
     * Tokenize, parse, and collect imports for a file.
     * <b>Each file should only be parsed once</b>.
     *
     * @return SourceFile with exports gathered.
     */
    public SourceFile parse(String projectRoot, String relativePath, String name) throws FileNotFoundException {

        var source = new SourceFile(projectRoot, relativePath, name);
        Util.debug("Parsing `" + source.file.getName() + "`");
        source.stmts.add(0, Stmt.Import.instance);

        compilationSet.put(FilenameUtils.separatorsToUnix(source.file.getPath()), source);

        List<Pair<String, String>> imports = new ArrayList<>();

        // Get all qualified imports (but don't load them)
        for (Stmt stmt : source.stmts) {
            if (stmt instanceof Stmt.Import importStmt) {
                String requestedImport = importStmt.stringLiteral.source();
                Util.debug("\tFound import `" + requestedImport + "`");

                String relative = FilenameUtils.getPath(requestedImport);
                String n = FilenameUtils.getName(requestedImport);
                String filePath = Path.of(source.projectRoot, source.relativePath, relative, n + ".imp").toString();
                var normalizedPath = FilenameUtils.separatorsToUnix(Path.of(filePath).normalize().toString());
                if (new File(normalizedPath).exists()) {
                    imports.add(Pair.with(relative, normalizedPath));

                } else if (!n.equals("batteries")) {
                    Comptime.ModuleNotFound.submit(this, source.file, stmt, n);

                }

            }
        }

        // Link imported SourceFiles to current SourceFile
        for (Pair<String, String> i : imports) {
            var relative = i.getValue0();
            var normalizedPath = i.getValue1();
            var n = FilenameUtils.removeExtension(FilenameUtils.getName(normalizedPath));
            if (!compilationSet.containsKey(normalizedPath)) {
                Util.debug("triggered parse, no key exists `" + normalizedPath + "`");
                SourceFile next;
                try {
                    next = parse(projectRoot, Path.of(source.relativePath, relative).normalize().toString(), n);
                    source.addImport(normalizedPath, next);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Util.exit("Issues with building import tree.", 67);
                }
            } else {
                source.addImport(normalizedPath, compilationSet.get(normalizedPath));
            }

        }

        return source;
    }


}
