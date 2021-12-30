package org.imp.jvm.typechecker;

import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.expression.Function;
import org.imp.jvm.parser.Parser;
import org.imp.jvm.statement.Enum;
import org.imp.jvm.statement.*;
import org.imp.jvm.tokenizer.Tokenizer;
import org.imp.jvm.tool.Timer;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.FunctionType;
import org.imp.jvm.types.Modifier;
import org.imp.jvm.visitors.ASTPrinterVisitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TypeCheckerMain {

    public static ImpFile getAbstractSyntaxTree2(File file) throws FileNotFoundException {
        String name = FilenameUtils.separatorsToUnix(FilenameUtils.removeExtension(file.getPath()));
        var printer = new ASTPrinterVisitor();
        Timer.log("Buffer opened");
        var tokenizer = new Tokenizer(file);
        Timer.log("Lexer created");

        var parser = new Parser(tokenizer);
        var statements = parser.parse();
//        String printed = printer.print(statements);
//        System.out.println(printed);

        Timer.log("Source file parsed.");
//        Timer.LOG = true;
//        Timer.logTotalTime();


        // get all top level statements in the file

        // Root Scope for Static Unit
        Scope staticScope = new Scope();

        // create an ImpFile node with appropriate children
        var impFile = new ImpFile(FilenameUtils.removeExtension(name));


        var mainFunctionType = new FunctionType("main", impFile, false);

        Identifier varArgs = new Identifier();
        varArgs.type = BuiltInType.STRING_ARR;
        varArgs.name = "args";
        var parameters = new ArrayList<Identifier>();
        parameters.add(varArgs);

        var main = new Function(
                Modifier.NONE,
                "main",
                parameters,
                BuiltInType.VOID,
                new Block(),
                impFile
        );
        main.block.scope = staticScope;
        impFile.functions.add(main);


        var typeChecker = new TypeChecker(staticScope, impFile);

        List<Statement> checkedStatements = new ArrayList<>();
        for (var stmt : statements) {
            var s = typeChecker.validate(stmt);
            checkedStatements.add(s);

            // Split classes out to their own files
            if (s instanceof Struct struct) {
                impFile.structTypes.add(struct.structType);
            } else if (s instanceof Enum enumStatement) {
                impFile.enumTypes.add(enumStatement.enumType);
            } else if (s instanceof Function f) {

                // add function to static class methods
                impFile.functions.add(f);

                if (f.modifier == Modifier.EXPORT) {
                    impFile.exports.add(new Export(f, staticScope));
                }

            } else {
                // All other root level nodes go in the main method
                main.block.statements.add(s);
            }
        }

        var constructorType = new FunctionType("<init>", impFile, false);
        var constructor = new Constructor(null, constructorType, Collections.emptyList(), new Block());
        constructor.name = "<init>";
        impFile.functions.add(constructor);
        return impFile;
    }


    public static void main(String[] args) {


        try {
            File file = new File("examples/scratch.imp");
            TypeCheckerMain.getAbstractSyntaxTree2(file);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
