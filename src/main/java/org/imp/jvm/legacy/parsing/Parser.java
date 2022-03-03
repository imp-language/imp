package org.imp.jvm.legacy.parsing;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.ImpLexer;
import org.imp.jvm.ImpParser;
import org.imp.jvm.legacy.ImpFile;
import org.imp.jvm.legacy.exception.ThrowingErrorListener;
import org.imp.jvm.legacy.parsing.visitor.ImpFileVisitor;
import org.imp.jvm.tool.Timer;

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
        Timer.log("ANTLR lexing done");

        ImpParser parser = new ImpParser(tokenStream);
        Timer.log("ANTLR parser created");

        parser.removeErrorListeners();
        parser.addErrorListener(ThrowingErrorListener.INSTANCE);

//        parser.getInterpreter().setPredictionMode(PredictionMode.SLL);
        String name = FilenameUtils.separatorsToUnix(FilenameUtils.removeExtension(file.getPath()));
        ParseTree parseTree;
        parseTree = parser.program();
        Timer.log("ANTLR parsing complete");
//        Timer.LOG = true;
//        Timer.logTotalTime();

//        System.exit(98);
        return parseTree.accept(new ImpFileVisitor(FilenameUtils.removeExtension(name)));

//        try {
//            parser.getInterpreter().setPredictionMode(PredictionMode.LL);
//            parseTree = parser.program();
//            return parseTree.accept(new ImpFileVisitor(FilenameUtils.removeExtension(name)));
//        } catch (Exception ex) {
//            tokenStream.seek(0); // rewind input stream
//            parser.reset();
//            parser.getInterpreter().setPredictionMode(PredictionMode.LL_EXACT_AMBIG_DETECTION);
//            parseTree = parser.program();  // STAGE 2
//            return parseTree.accept(new ImpFileVisitor(FilenameUtils.removeExtension(name)));
//            // if we parse ok, it's LL not SLL
//        }

//        ParseTree parseTree = parser.program();

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
