package org.imp.jvm.tool;

import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.SourceFile;
import org.imp.jvm.codegen.BytecodeGenerator;
import org.imp.jvm.errors.Comptime;
import org.imp.jvm.errors.MyError;
import org.imp.jvm.parser.Stmt;
import org.imp.jvm.types.ImpType;
import org.imp.jvm.visitors.EnvironmentVisitor;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class API {

    static final List<SourceFile> compilationSet = new ArrayList<>();

    /**
     * Tokenize, parse, and build environments for a file.
     *
     * @return SourceFile with exports gathered.
     */
    public static SourceFile parse(String projectRoot, String relativePath, String name) throws FileNotFoundException, MyError {

        var source = new SourceFile(projectRoot, relativePath, name);
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
                } catch (FileNotFoundException | MyError e) {
                    e.printStackTrace();
                }
                source.addImport(f, next);
            }
            return null;
        });

        // EnvironmentVisitor builds scopes and assigns
        // UnknownType or Literal types to expressions.
        EnvironmentVisitor environmentVisitor = new EnvironmentVisitor(source.rootEnvironment, source);
        source.acceptVisitor(environmentVisitor);
        Comptime.killIfErrors("Correct syntax errors before type checking can continue.");

        // Process all exports in the current file
        source.filter(Stmt.Export.class, (exportStmt) -> {
            if (exportStmt.stmt instanceof Stmt.Exportable exportable) {
                String identifier = exportable.identifier();
                ImpType type = source.rootEnvironment.getVariable(identifier);
                if (type != null) {
                    source.exports.put(identifier, type);
                    ExportTable.add(source, identifier, type);
                    ExportTable.addSQL(source.file, identifier, type);
                    // Todo: figure out what data to store in the table.
                    // Is it as simple as a list of fields and their types?
                }
            }
            return null;
        });

        // Todo(CURRENT): need to typecheck all files

        return source;
    }


    public static void buildProgram(Map<String, ? extends SourceFile> compilationSet) {
        BytecodeGenerator bytecodeGenerator = new BytecodeGenerator();
        for (var key : compilationSet.keySet()) {
            var source = compilationSet.get(key);
            var allByteUnits = bytecodeGenerator.generate(source);

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


}
