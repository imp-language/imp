package org.imp.jvm.tool;

import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.BytecodeGenerator;
import org.imp.jvm.domain.SourceFile;
import org.imp.jvm.errors.Comptime;
import org.imp.jvm.parser.Stmt;
import org.imp.jvm.types.ImpType;
import org.imp.jvm.visitors.EnvironmentVisitor;
import org.imp.jvm.visitors.TypeCheckVisitor;
import org.jgrapht.alg.util.Triple;

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
//                    ExportTable.add(source, identifier, type);
//                    ExportTable.addSQL(source.file, identifier, type);
                            // Must figure out what data to store in the table.
                            // Is it as simple as a list of fields and their types?
                        }
                    }

                }
            }

            Comptime.killIfErrors(this, "Correct syntax errors before type checking can continue.");
        }

        // Todo(CURRENT): scope imported members so codegen works properly
        // Add imported members to scopes
        for (String filePath : compilationSet.keySet()) {
            var source = compilationSet.get(filePath);
            for (String s : source.imports.keySet()) {
                var sourceFile = source.imports.get(s);
                var exportedMembers = sourceFile.exports;
                for (String exportedName : exportedMembers.keySet()) {
                    var exportedType = exportedMembers.get(exportedName);
                    source.rootEnvironment.addVariable(exportedName, exportedType);
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

//        var pretty = new PrettyPrinterVisitor(entry.rootEnvironment);
//        Util.println(pretty.print(entry.stmts));

//        for (var s : compilationList()) {
//            if (!compilationSet.containsKey(s.getFullRelativePath())) {
//                compilationSet.put(s.getFullRelativePath(), s);
//            }
//        }

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
     * Tokenize, parse, and build environments for a file.
     *
     * @return SourceFile with exports gathered.
     */
    public SourceFile parse(String projectRoot, String relativePath, String name) throws FileNotFoundException, Comptime.CompilerError {

        var source = new SourceFile(projectRoot, relativePath, name);
        System.out.println("Parsing `" + source.file.getPath() + "`");
        source.stmts.add(0, Stmt.Import.instance);

        compilationSet.put(FilenameUtils.separatorsToUnix(source.file.getPath()), source);

        List<Triple<String, String, String>> imports = new ArrayList<>();

        // Get all qualified imports (but don't load them)
        for (Stmt stmt : source.stmts) {
            if (stmt instanceof Stmt.Import importStmt) {
                String requestedImport = importStmt.stringLiteral.source();
                System.out.println("\tFound import `" + requestedImport + "`");

                String relative = FilenameUtils.getPath(requestedImport);
                String n = FilenameUtils.getName(requestedImport);

                String filePath = Path.of(source.projectRoot, source.relativePath, relative, n + ".imp").toString();
                filePath = FilenameUtils.separatorsToUnix(filePath);

                var f = new File(filePath);
                imports.add(Triple.of(filePath, relative, n));
//                if (f.exists() && !compilationSet.containsKey(f.getPath())) {
//                    SourceFile next = null;
//                    try {
//                        next = parse(projectRoot, Path.of(source.relativePath, relative).toString(), n);
//                    } catch (FileNotFoundException | Comptime.CompilerError e) {
//                        e.printStackTrace();
//                    }
//                    source.addImport(f, next);
//                }
            }
        }

        System.out.println(imports);

        for (Triple<String, String, String> i : imports) {
            var filePath = i.getFirst();
            var relative = i.getSecond();
            var n = i.getThird();
            var f = new File(filePath);
            if (f.exists()) {
                if (!compilationSet.containsKey(filePath)) {
                    SourceFile next = null;
                    try {
                        next = parse(projectRoot, Path.of(source.relativePath, relative).toString(), n);
                    } catch (FileNotFoundException | Comptime.CompilerError e) {
                        e.printStackTrace();
                    }
                    source.addImport(f, next);
                } else {
                    source.addImport(f, compilationSet.get(filePath));
                }
            }
        }

        // EnvironmentVisitor builds scopes and assigns
        // UnknownType or Literal types to expressions.
//        EnvironmentVisitor environmentVisitor = new EnvironmentVisitor(this, source.rootEnvironment, source);
//        source.acceptVisitor(environmentVisitor);
//        Comptime.killIfErrors(this, "Correct syntax errors before type checking can continue.");

        // Process all exports in the current file
//        source.filter(Stmt.Export.class, (exportStmt) -> {
//            if (exportStmt.stmt instanceof Stmt.Exportable exportable) {
//                String identifier = exportable.identifier();
//                ImpType type = source.rootEnvironment.getVariable(identifier);
//                if (type != null) {
//                    source.exports.put(identifier, type);
////                    ExportTable.add(source, identifier, type);
////                    ExportTable.addSQL(source.file, identifier, type);
//                    // Must figure out what data to store in the table.
//                    // Is it as simple as a list of fields and their types?
//                }
//            }
//            return null;
//        });

        // Feature: need to typecheck all files

        return source;
    }


}
