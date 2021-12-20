package org.imp.jvm.tool;

import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.Environment;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.parser.Parser;
import org.imp.jvm.tokenizer.Tokenizer;
import org.imp.jvm.types.Type;
import org.imp.jvm.visitors.ASTPrinterVisitor;
import org.imp.jvm.visitors.EnvironmentVisitor;
import org.imp.jvm.visitors.PrettyPrinterVisitor;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Compiler {

    public final String filename;

    public List<String> filenames;

    public Compiler(String filename) {

        this.filename = FilenameUtils.separatorsToUnix(filename);
    }

    public String compile() throws FileNotFoundException {
        File file = new File(filename);
        Tokenizer tokenizer = new Tokenizer(file);
        var parser = new Parser(tokenizer);
        var statements = parser.parse();

        /**
         * Plan:
         * 0. Share all exported statements.
         *      Any type or variable may be exported, including
         *      functions, enums, structs, typedefs, etc.
         *
         * 1. Gather all types defined in the file.
         *      Store structs, functions and type aliases in the
         *      current scope, marking them as Unknown or not
         *      solidified yet.
         *
         * 2. Anywhere a type is referenced, match to a real type.
         *      Look through the types we've just defined as well
         *      as all types exported from other files.
         *
         * 3. Validate all expressions and statements.
         *      Now that type information has settled, visit
         *      each expression and statements and determine type
         *      info, then perform actions needed before codegen.
         *
         * 4. Codegen.
         *      Visit each statement and expression, generating code.
         */

        var printer = new ASTPrinterVisitor();
        System.out.println(printer.print(statements));
        var pretty = new PrettyPrinterVisitor();
        System.out.println(pretty.print(statements));

        var rootEnvironment = new Environment();


        // 1. Gather all types defined in the file
        List<Type> types = new ArrayList<>();
        EnvironmentVisitor environmentVisitor = new EnvironmentVisitor(rootEnvironment);
        for (var stmt : statements) {
//            StandardTraversal.traverse(stmt, environmentVisitor, null);
            stmt.accept(environmentVisitor);

//            var type = typeVisitor.visit(stmt);
//            if (type.isPresent()) {
//                types.add(type.get());
//            }
        }
        System.out.println(types);

//        Scope staticScope = new Scope();
//        MethodVisitor mv = new Me
//        CodegenVisitor codegenVisitor = new CodegenVisitor(staticScope);

        return "ree";
    }


    public String compileold() throws FileNotFoundException {
        // Walk the dependency tree
        var entry = API.createSourceFile(filename);
        Graph<ImpFile, DefaultEdge> dependencyGraph = null;
        dependencyGraph = API.dependencyGraph(entry);
        Timer.log("build dependency graph");
        entry.validate();
        Timer.log("validate entry file");


        // Reduce graph dependencies to unique set of compilation units
        Iterator<ImpFile> iterator = new DepthFirstIterator<>(dependencyGraph, entry);
        Map<String, ImpFile> compilationSet = new HashMap<>();
        while (iterator.hasNext()) {
            ImpFile impFile = iterator.next();
            if (!compilationSet.containsKey(impFile.packageName)) {
                compilationSet.put(impFile.packageName, impFile);
            }
        }

        Timer.log("create compilation set");


        var program = API.createProgram(compilationSet);
        Timer.log("generate bytecode");
        Timer.LOG = true;
        Timer.logTotalTime();

        return entry.getClassName() + "/" + "Entry";

    }


}
