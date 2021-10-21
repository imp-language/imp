package org.imp.jvm.parsing;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.ImpLexer;
import org.imp.jvm.ImpParser;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.exception.ThrowingErrorListener;
import org.imp.jvm.parsing.visitor.ImpFileVisitor;

import java.io.File;
import java.io.IOException;


public class Parser {

    /**
     * Requires an input file that exists.
     *
     * @param file source
     * @return ImpFile
     */
    public static ImpFile getAbstractSyntaxTree(File file) {
        CharStream charStream = null;
        try {
            charStream = CharStreams.fromFileName(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("getAbstractSyntaxTree requires a file that exists.");
            System.exit(98);
        }
        ImpLexer impLexer = new ImpLexer(charStream);
        CommonTokenStream tokenStream = new CommonTokenStream(impLexer);

        ImpParser parser = new ImpParser(tokenStream);
        parser.setBuildParseTree(true);

        ParseTree parseTree = parser.program();
        String name = FilenameUtils.separatorsToUnix(FilenameUtils.removeExtension(file.getPath()));


        return parseTree.accept(new ImpFileVisitor(FilenameUtils.removeExtension(name)));
    }

    public static ImpFile getAbstractSyntaxTree(String content) {
        CharStream charStream = CharStreams.fromString(content);
        ImpLexer impLexer = new ImpLexer(charStream);
        CommonTokenStream tokenStream = new CommonTokenStream(impLexer);

        ImpParser parser = new ImpParser(tokenStream);
        parser.setBuildParseTree(true);
        parser.removeErrorListeners();
        parser.addErrorListener(ThrowingErrorListener.INSTANCE);

        ParseTree parseTree = parser.program();
        return parseTree.accept(new ImpFileVisitor("repl"));
    }


}
