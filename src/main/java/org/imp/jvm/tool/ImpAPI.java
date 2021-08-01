package org.imp.jvm.tool;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.ImpLexer;
import org.imp.jvm.ImpParser;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.Program;
import org.imp.jvm.parsing.visitor.ImpFileVisitor;

import java.io.File;
import java.io.IOException;
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
            impFileMap.put(filePath, ast);

            var children = gatherImports(ast);

        }

        return impFileMap;
    }

    public static Program createProgram(List<ImpFile> files) {
        return null;
    }

}
