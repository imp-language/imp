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
        ImpFile ast = parseTree.accept(new ImpFileVisitor(FilenameUtils.removeExtension(file.getName())));


        return ast;
    }

    public static ImpFile getAbstractSyntaxTree(String content) throws IOException {
        CharStream charStream = CharStreams.fromString(content);
        ImpLexer impLexer = new ImpLexer(charStream);
        CommonTokenStream tokenStream = new CommonTokenStream(impLexer);

        ImpParser parser = new ImpParser(tokenStream);
        parser.setBuildParseTree(true);
        parser.removeErrorListeners();
        parser.addErrorListener(ThrowingErrorListener.INSTANCE);

        ParseTree parseTree = parser.program();
        ImpFile ast = parseTree.accept(new ImpFileVisitor("repl"));
        return ast;
    }


}
