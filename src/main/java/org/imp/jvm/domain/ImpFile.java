package org.imp.jvm.domain;


import org.imp.jvm.compiler.Logger;
import org.imp.jvm.exception.SemanticErrors;
import org.imp.jvm.parsing.Node;
import org.imp.jvm.statement.Block;
import org.imp.jvm.statement.Export;
import org.imp.jvm.expression.Function;
import org.imp.jvm.statement.Import;
import org.imp.jvm.types.StructType;
import org.imp.jvm.types.Type;
import org.imp.jvm.types.TypeResolver;

import java.util.ArrayList;
import java.util.List;

public class ImpFile {
    // Filename
    public final String name;

    public String packageName = "";

    // All first-class functions defined in the source file.
    public final List<Function> functions = new ArrayList<>();

    // All structs defined in the source file.
    public final List<StructType> structTypes = new ArrayList<>();

    public final List<Import> imports = new ArrayList<>();
    public final List<ImpFile> qualifiedImports = new ArrayList<>();
    public final List<Export> exports = new ArrayList<>();


    public ImpFile(String name) {
        this.name = name;
        this.packageName = name;
    }

    public String getClassName() {
        return name;
    }


    public void validate() {
        // WIP: convert to AST
        Function main = functions.get(0);

        Node<Block> block = new Node<>(main.block);


        // 0. Export validation
        for (var e : exports) {
            // Add a structure to the scope of this Imp file
            // that is the imported other file.
//            System.out.println(e);
            e.validate(e.scope);
        }

        // 1. Ensure all struct fields have valid types
        for (var s : structTypes) {
            for (var f : s.fields) {
                Type t = TypeResolver.getFromName(f.type.getName(), s.scope);
                if (t == null) {
                    Logger.syntaxError(SemanticErrors.TypeNotFound, f.getCtx());

                }
                f.type = t;
            }
        }


        // Move all functions to the top level for compilation
        List<Function> functionList = new ArrayList<>();
        for (var f : functions) {
            functionList.add(f);


        }


        // 2. Recursively type-check the body of each function
        for (var f : functions) {
            f.block.validate(f.block.scope);
        }


        // 3. Secure variable mutability
        // Todo: immutability

        // 4. Validate imports


    }

    @Override
    public String toString() {
        return name;
    }
}
