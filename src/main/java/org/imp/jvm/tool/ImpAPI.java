package org.imp.jvm.tool;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.ImpLexer;
import org.imp.jvm.ImpParser;
import org.imp.jvm.compiler.BytecodeGenerator;
import org.imp.jvm.compiler.ClassGenerator;
import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.Program;
import org.imp.jvm.parsing.Parser;
import org.imp.jvm.parsing.visitor.ImpFileVisitor;

import java.io.*;
import java.util.*;

public class ImpAPI {
    public static ImpFile createSourceFile(String filename) throws IOException {
        ImpFile ast = Parser.getAbstractSyntaxTree(filename);

        if (Logger.hasErrors()) {
            Logger.getSyntaxErrors().forEach(e -> System.out.println(e.getMessage()));
            System.out.println("Correct parse errors before type checking and compilation can continue.");
            System.exit(1);
        }

        return ast;
    }

    // Todo: recursion
    public static Map<String, ImpFile> gatherImports(ImpFile entry) throws IOException {
        System.out.println("Gathering imports for " + entry.name + ".imp");
        String basePath = FilenameUtils.getPath(entry.name);

        Map<String, ImpFile> impFileMap = new HashMap<>();
        for (var i : entry.imports) {
            String relativeFileName = i.getValue();
            String filePath = FilenameUtils.concat(basePath, relativeFileName + ".imp");
            filePath = FilenameUtils.separatorsToUnix(filePath);

//            System.out.println("Found file " + filePath);
            ImpFile ast = createSourceFile(filePath);
            ast.validate();
            if (Logger.hasErrors()) {
                Logger.getSyntaxErrors().forEach(e -> System.out.println(e.getMessage()));
                System.out.println("Correct semantic errors before compilation can continue.");
                System.exit(1);
            }

            impFileMap.put(filePath, ast);
            entry.qualifiedImports.add(ast);


            var children = gatherImports(ast);

        }

        return impFileMap;
    }

    public static Program createProgram(Map<String, ImpFile> files) throws IOException {
        BytecodeGenerator bytecodeGenerator = new BytecodeGenerator();
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


                OutputStream output = new FileOutputStream(fileName);
                output.write(byteUnit.getValue());
                output.flush();
                output.close();

            }
        }

        if (Logger.hasErrors()) {
            Logger.getSyntaxErrors().forEach(e -> System.out.println(e.getMessage()));
            System.out.println("Errored during bytecode generation.");
            System.exit(1);
        }

        return null;
    }

    public static int run(String className) throws IOException {
        String cmd = "java --enable-preview -cp .compile;target/classes " + className;
        Process proc = Runtime.getRuntime().exec(cmd);

        // 3. Watch standard out
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));

        String s = null;
        String b = "some\ntext\tbitch";
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }

        System.out.println("\nErrors (if any):");
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }
        System.out.println("");

        return 0;
    }

}
