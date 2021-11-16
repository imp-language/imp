package org.imp.jvm.tool;

import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.parser.Parser;
import org.imp.jvm.tokenizer.Tokenizer;
import org.imp.jvm.visitors.CodegenVisitor;
import org.imp.jvm.visitors.TypeVisitor;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.DepthFirstIterator;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

        TypeVisitor typeVisitor = new TypeVisitor();
        for (var stmt : statements) {
            var type = typeVisitor.visit(stmt);
            if (type.isPresent()) {
                // Todo: add new types to scope
            }
        }

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
