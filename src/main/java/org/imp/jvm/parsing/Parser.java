package org.imp.jvm.parsing;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.ImpLexer;
import org.imp.jvm.ImpParser;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.parsing.visitor.ImpFileVisitor;

import java.io.File;
import java.io.IOException;


public class Parser {
    public static ImpFile getAbstractSyntaxTree(File file) throws IOException {

        CharStream charStream = CharStreams.fromFileName(file.getAbsolutePath());
        ImpLexer impLexer = new ImpLexer(charStream);
        CommonTokenStream tokenStream = new CommonTokenStream(impLexer);

        ImpParser parser = new ImpParser(tokenStream);
        parser.setBuildParseTree(true);

        ParseTree parseTree = parser.program();
        ImpFile ast = parseTree.accept(new ImpFileVisitor(FilenameUtils.removeExtension(file.getName())));


//        parser.addErrorListener(new SyntaxErrorListener());


//        impParser.addErrorListener(new ImpFile);
//        parser.

        return ast;
    }


}
