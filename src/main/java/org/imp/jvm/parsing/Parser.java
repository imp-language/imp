package org.imp.jvm.parsing;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.imp.jvm.ImpLexer;
import org.imp.jvm.ImpParser;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.parsing.visitor.ImpFileVisitor;

import java.io.File;
import java.io.IOException;


public class Parser {
    public static ImpFile getImpFile(File file) throws IOException {

        CharStream charStream = CharStreams.fromFileName(file.getAbsolutePath());
        ImpLexer impLexer = new ImpLexer(charStream);
        CommonTokenStream tokenStream = new CommonTokenStream(impLexer);

        ImpParser parser = new ImpParser(tokenStream);
        parser.setBuildParseTree(true);

        ParseTree parseTree = parser.program();
        ImpFile impFile = parseTree.accept(new ImpFileVisitor());


//        parser.addErrorListener(null);


        // ToDo: figure out how to split parsed file into StaticUnit and ClassUnits
//        impParser.addErrorListener(new ImpFile);
//        parser.

        return impFile;
    }
}
