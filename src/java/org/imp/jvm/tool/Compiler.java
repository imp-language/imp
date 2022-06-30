package org.imp.jvm.tool;

import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.BytecodeGenerator;
import org.imp.jvm.Util;
import org.imp.jvm.domain.SourceFile;
import org.imp.jvm.errors.Comptime;
import org.imp.jvm.parser.Stmt;
import org.imp.jvm.types.ImpType;
import org.imp.jvm.visitors.EnvironmentVisitor;
import org.imp.jvm.visitors.PrettyPrinterVisitor;
import org.imp.jvm.visitors.TypeCheckVisitor;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public record Compiler(List<Comptime.Data> errorData, List<SourceFile> compilationSet, boolean developmentMode) {


    public Compiler() {
        this(new ArrayList<>(), new ArrayList<>(), true);
    }

    /**
     * @return java class name ('.' separated) relative to the project root
     */
    public String compile(String projectRoot, String filename) throws FileNotFoundException, Comptime.CompilerError {

        AtomicInteger closure = new AtomicInteger(42);

        Consumer<Integer> addToClosure = (i) -> {
            closure.set(closure.get() + i);
        };

        String relativePath = FilenameUtils.getPath(filename);
        String name = FilenameUtils.getName(filename);

        var entry = parse(projectRoot, relativePath, name);
        Map<String, SourceFile> compilationSet = new HashMap<>();
        Comptime.killIfErrors(this, "Correct parser errors before continuing.");

        Timer.log("build dependency graph");

        TypeCheckVisitor typeCheckVisitor = new TypeCheckVisitor(this, entry.rootEnvironment, entry);
        entry.acceptVisitor(typeCheckVisitor);
        Timer.log("Type checking done");

        Comptime.killIfErrors(this, "Correct type errors before compilation can continue.");

        var pretty = new PrettyPrinterVisitor(entry.rootEnvironment);
        Util.println(pretty.print(entry.stmts));

        for (var s : compilationSet()) {
            if (!compilationSet.containsKey(s.getFullRelativePath())) {
                compilationSet.put(s.getFullRelativePath(), s);
            }
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
     * Tokenize, parse, and build environments for a file.
     *
     * @return SourceFile with exports gathered.
     */
    public SourceFile parse(String projectRoot, String relativePath, String name) throws FileNotFoundException, Comptime.CompilerError {

        var source = new SourceFile(projectRoot, relativePath, name);
        source.stmts.add(0, Stmt.Import.instance);

        compilationSet.add(source);

        // Get all qualified imports (but don't load them)
        source.filter(Stmt.Import.class, (importStmt) -> {
            String requestedImport = importStmt.stringLiteral.source();

            String relative = FilenameUtils.getPath(requestedImport);
            String n = FilenameUtils.getName(requestedImport);

            String filePath = Path.of(source.projectRoot, source.relativePath, relative, n + ".imp").toString();
            filePath = FilenameUtils.separatorsToUnix(filePath);

            var f = new File(filePath);
            if (f.exists()) {
                SourceFile next = null;
                try {
                    next = parse(projectRoot, Path.of(source.relativePath, relative).toString(), n);
                } catch (FileNotFoundException | Comptime.CompilerError e) {
                    e.printStackTrace();
                }
                source.addImport(f, next);
            }
            return null;
        });

        // EnvironmentVisitor builds scopes and assigns
        // UnknownType or Literal types to expressions.
        EnvironmentVisitor environmentVisitor = new EnvironmentVisitor(this, source.rootEnvironment, source);
        source.acceptVisitor(environmentVisitor);
        Comptime.killIfErrors(this, "Correct syntax errors before type checking can continue.");

        // Process all exports in the current file
        source.filter(Stmt.Export.class, (exportStmt) -> {
            if (exportStmt.stmt instanceof Stmt.Exportable exportable) {
                String identifier = exportable.identifier();
                ImpType type = source.rootEnvironment.getVariable(identifier);
                if (type != null) {
                    source.exports.put(identifier, type);
                    ExportTable.add(source, identifier, type);
                    ExportTable.addSQL(source.file, identifier, type);
                    // Feature: figure out what data to store in the table.
                    // Is it as simple as a list of fields and their types?
                }
            }
            return null;
        });

        // Feature: need to typecheck all files

        return source;
    }


}
