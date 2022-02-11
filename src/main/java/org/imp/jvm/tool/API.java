package org.imp.jvm.tool;

import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.Stmt;
import org.imp.jvm.compiler.BytecodeGenerator;
import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.Program;
import org.imp.jvm.domain.SourceFile;
import org.imp.jvm.errors.Comptime;
import org.imp.jvm.exception.Errors;
import org.imp.jvm.parsing.Parser;
import org.imp.jvm.runtime.GlueOld;
import org.imp.jvm.tokenizer.Tokenizer;
import org.imp.jvm.types.Type;
import org.imp.jvm.visitors.EnvironmentVisitor;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public class API {

    /**
     * Tokenize, parse, and build environments for a file.
     *
     * @return SourceFile with exports gathered.
     */
    public static SourceFile parse(File file) throws FileNotFoundException {
        Tokenizer tokenizer = new Tokenizer(file);
        var parser = new org.imp.jvm.parser.Parser(tokenizer);
        var statements = parser.parse();

        var source = new SourceFile(file, statements);
        String basePath = source.basePath();

        // Get all qualified imports (but don't load them)
        source.filter(Stmt.Import.class, (importStmt) -> {
            String relativePath = importStmt.stringLiteral.source();
            String filePath = FilenameUtils.concat(basePath, relativePath + ".imp");
            filePath = FilenameUtils.separatorsToUnix(filePath);
            var f = new File(filePath);
            if (f.exists()) {
                SourceFile next = null;
                try {
                    next = parse(f);
                } catch (FileNotFoundException e) {
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
                Type type = source.rootEnvironment.getVariable(identifier);
                if (type != null) {
                    source.exports.put(identifier, type);
                    ExportTable.add(source, identifier, type);
                    ExportTable.addSQL(source.path(), identifier, type);
                    // Todo: figure out what data to store in the table.
                    // Is it as simple as a list of fields and their types?
                }
            }
            return null;
        });

        return source;
    }

    public static ImpFile createSourceFile(String filename) throws FileNotFoundException {
        File file = new File(filename);

        Timer.log("Buffer opened");
        var tokenizer = new Tokenizer(file);
        Timer.log("Lexer created");

        var parser = new org.imp.jvm.parser.Parser(tokenizer);
        var statements = parser.parse();

        ImpFile ast = null;
        if (file.exists()) {
            Timer.log("entry file found");
//            ast = Parser.getAbstractSyntaxTree(file);
            //                ast = TypeCheckerMain.getAbstractSyntaxTree2(file);
            Timer.log("ANTLR visitor complete");
        }
        return null;
    }

    /**
     * Parse text content, from a REPL likely.
     *
     * @param content text
     * @return ImpFile
     */
    public static ImpFile createReplFile(String content) {
        return Parser.getAbstractSyntaxTree(content);
    }

    public static Graph<SourceFile, DefaultEdge> dependencyGraph(SourceFile entry) throws FileNotFoundException {
        DependencyWalker walker = new DependencyWalker();
        var dependencies = walker.walkDependencies(entry);
        DOTExporter<SourceFile, DefaultEdge> exporter =
                new DOTExporter<>(v ->
                        v.file.getName()
                                .replace('.', '_')
                                .replace('/', '_')
                                .replace('\\', '_')
                                .replace('-', '_')
                                .replace(':', '_')

                );
        exporter.setVertexAttributeProvider((v) -> {
            Map<String, Attribute> map = new LinkedHashMap<>();
            map.put("label", DefaultAttribute.createAttribute(v.toString()));
            return map;
        });
        Writer writer = new StringWriter();
        exporter.exportGraph(dependencies, writer);
//        System.out.println(writer);

        return dependencies;
    }

    public static Graph<ImpFile, DefaultEdge> dependencyGraph(ImpFile entry) throws FileNotFoundException {
        DependencyWalker walker = new DependencyWalker();
        var dependencies = walker.walkDependencies(entry);

        DOTExporter<ImpFile, DefaultEdge> exporter =
                new DOTExporter<>(v ->
                        v.name
                                .replace('.', '_')
                                .replace('/', '_')
                                .replace('\\', '_')
                                .replace('-', '_')
                                .replace(':', '_')

                );
        exporter.setVertexAttributeProvider((v) -> {
            Map<String, Attribute> map = new LinkedHashMap<>();
            map.put("label", DefaultAttribute.createAttribute(v.toString()));
            return map;
        });
        Writer writer = new StringWriter();
        exporter.exportGraph(dependencies, writer);

        return dependencies;
    }

    public static Map<String, SourceFile> gatherImports(SourceFile current) {
        String basePath = FilenameUtils.getPath(current.file.getPath());
        Map<String, SourceFile> fileMap = new HashMap<>();

        current.filter(Stmt.Import.class, (importStmt) -> {
            String relativePath = importStmt.stringLiteral.source();
            String filePath = FilenameUtils.concat(basePath, relativePath + ".imp");
            filePath = FilenameUtils.separatorsToUnix(filePath);
            var file = new File(filePath);
            if (file.exists()) {
                SourceFile next = null;
                try {
                    next = parse(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                fileMap.put(filePath, next);
//                System.out.println(filePath);
                current.addImport(file, next);
            }
            return null;
        });

        return fileMap;
    }

    public static Map<String, ImpFile> gatherImports(ImpFile entry) {
        String basePath = FilenameUtils.getPath(entry.name);

        Map<String, ImpFile> impFileMap = new HashMap<>();
        for (var i : entry.imports) {
            String relativeFileName = i.getValue();
            String filePath = FilenameUtils.concat(basePath, relativeFileName + ".imp");
            filePath = FilenameUtils.separatorsToUnix(filePath);

            if (GlueOld.coreModules.containsKey(relativeFileName)) {
                entry.stdlibImports.add(relativeFileName);
            } else {
                ImpFile ast = null;
                try {
                    ast = createSourceFile(filePath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if (ast == null) {
                    Logger.syntaxError(Errors.ModuleNotFound, i, filePath);
                    Logger.killIfErrors("Correct parse errors before type checking and compilation can continue.");
                }
                assert ast != null;
                ast.validate();
                Logger.killIfErrors("Correct semantic errors before compilation can continue.");
                impFileMap.put(filePath, ast);
                entry.qualifiedImports.add(ast);
            }

        }

        return impFileMap;
    }


    public static void buildProgram(Map<String, SourceFile> compilationSet) {
        BytecodeGenerator bytecodeGenerator = new BytecodeGenerator();
        // Todo: bytecode generation visitor!
        for (var key : compilationSet.keySet()) {
            var source = compilationSet.get(key);
            var byteUnit = bytecodeGenerator.generate(source);

            String qualifiedName = source.name();
            String fileName = ".compile/" + qualifiedName + "/Class_" + qualifiedName + ".class";

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

        }
        Logger.killIfErrors("Errored during bytecode generation.");

    }

    public static Program createProgram(Map<String, ImpFile> files) {
        BytecodeGenerator bytecodeGenerator = new BytecodeGenerator();
        Logger.killIfErrors("Errored during bytecode generation.");

        for (var key : files.keySet()) {
            ImpFile value = files.get(key);
            var byteUnits = bytecodeGenerator.generate(value);
            String className = value.getClassName();
            for (var byteUnit : byteUnits.entrySet()) {
                String qualifiedName = className;
                if (!byteUnit.getKey().equals("main")) {
                    qualifiedName = byteUnit.getKey();
                }
                String fileName = ".compile/" + qualifiedName + ".class";

//                System.out.println("Writing: " + fileName);

                File tmp = new File(fileName);
                //noinspection ResultOfMethodCallIgnored
                tmp.getParentFile().mkdirs();

                OutputStream output;
                try {
                    output = new FileOutputStream(fileName);
                    output.write(byteUnit.getValue());
                    output.close();
                } catch (IOException e) {
                    System.err.println("The above call to mkdirs() should have worked.");
                    System.exit(9);
                }

            }
        }

        Logger.killIfErrors("Errored during bytecode generation.");

        return null;
    }


}
