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
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.Program;
import org.imp.jvm.parsing.visitor.ImpFileVisitor;

import java.io.*;
import java.util.*;

public class ImpAPI {
    public static ImpFile createSourceFile(String filename) throws IOException {
        CharStream charStream = CharStreams.fromFileName(filename);
        ImpLexer impLexer = new ImpLexer(charStream);
        CommonTokenStream tokenStream = new CommonTokenStream(impLexer);

        ImpParser parser = new ImpParser(tokenStream);
        parser.setBuildParseTree(true);

        ParseTree parseTree = parser.program();
        ImpFile ast = parseTree.accept(new ImpFileVisitor(FilenameUtils.removeExtension(filename)));
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

            System.out.println("Found file " + filePath);
            ImpFile ast = createSourceFile(filePath);
            ast.validate();
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

                System.out.println("Writing: " + fileName);

                File tmp = new File(fileName);
                tmp.getParentFile().mkdirs();


                OutputStream output = new FileOutputStream(fileName);
                output.write(byteUnit.getValue());
                output.flush();
                output.close();

            }
        }

        return null;
    }

}
