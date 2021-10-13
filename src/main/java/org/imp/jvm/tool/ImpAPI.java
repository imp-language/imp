package org.imp.jvm.tool;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.imp.jvm.compiler.BytecodeGenerator;
import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.Program;
import org.imp.jvm.exception.Errors;
import org.imp.jvm.parsing.Parser;
import org.imp.jvm.runtime.Glue;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.diogonunes.jcolor.Ansi.*;
import static com.diogonunes.jcolor.Attribute.*;


public class ImpAPI {
    /**
     * Parse a source file.
     *
     * @param filename source
     * @return ImpFile
     */
    public static ImpFile createSourceFile(String filename) {
        ImpFile ast = null;
        File file = new File(filename);
        if (file.exists()) {
            Timer.log("entry file found");
            ast = Parser.getAbstractSyntaxTree(file);
            Timer.log("ANTLR parsing complete");
        }
        return ast;
    }

    public static Graph<ImpFile, DefaultEdge> dependencyGraph(ImpFile entry) {
        var walker = new DependencyWalker();
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


    public static ImpFile createReplFile(String content) throws IOException {
        ImpFile ast = Parser.getAbstractSyntaxTree(content);
        return ast;
    }


    // Todo: recursion
    public static Map<String, ImpFile> gatherImports(ImpFile entry) {
        String basePath = FilenameUtils.getPath(entry.name);

        Map<String, ImpFile> impFileMap = new HashMap<>();
        for (var i : entry.imports) {
            String relativeFileName = i.getValue();
            String filePath = FilenameUtils.concat(basePath, relativeFileName + ".imp");
            filePath = FilenameUtils.separatorsToUnix(filePath);


            if (Glue.coreModules.containsKey(relativeFileName)) {
                entry.stdlibImports.add(relativeFileName);
            } else {
                ImpFile ast = createSourceFile(filePath);
                if (ast == null) {
                    Logger.syntaxError(Errors.ModuleNotFound, entry.name, i.getCtx(), filePath);
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

    public static Program createProgram(Map<String, ImpFile> files) {
        BytecodeGenerator bytecodeGenerator = new BytecodeGenerator();
        Logger.killIfErrors("Errored during bytecode generation.");

        for (var key : files.keySet()) {
            var value = files.get(key);
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
                tmp.getParentFile().mkdirs();


                OutputStream output = null;
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

    public static void run(String className) throws IOException, InterruptedException {
        String cmd = "java --enable-preview -cp .compile;target/classes " + className;
        cmd = "java --version";

        ProcessBuilder processBuilder = new ProcessBuilder(
                "java",
                "--enable-preview",
                "-cp",
                ".compile" + System.getProperty("path.separator") + "target/classes",
                className
        );
        processBuilder.inheritIO();
        Process process = processBuilder.start();

        process.waitFor();
    }

    public static Process spawn(String className) throws IOException {
        String cmd = "java --enable-preview -cp .compile" + System.getProperty("path.separator") + "target/classes " + className;
        Process proc = Runtime.getRuntime().exec(cmd);
        return proc;
    }


}
