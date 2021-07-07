package org.imp.jvm.domain;


import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.root.StaticUnit;
import org.imp.jvm.exception.SemanticErrors;
import org.imp.jvm.runtime.Box;
import org.imp.jvm.statement.Function;
import org.imp.jvm.types.FunctionType;
import org.imp.jvm.types.StructType;
import org.imp.jvm.types.Type;
import org.imp.jvm.types.TypeResolver;

import java.util.ArrayList;
import java.util.List;

public class ImpFile {
    // Filename
    public String name;

    public String packageName = "";

    // Top-level entities in the source file.
    public final StaticUnit staticUnit;

    // All first-class functions defined in the source file.
    public final List<Function> functions = new ArrayList<>();

    // All structs defined in the source file.
    public final List<StructType> structTypes = new ArrayList<>();


    public ImpFile(StaticUnit staticUnit, String name) {
        this.name = name;
        this.packageName = name;
        this.staticUnit = staticUnit;
    }

    public String getClassName() {
        return name;
    }


    public void validate() {
        

        // 0. Ensure all struct fields have valid types
        for (var s : structTypes) {
            for (var f : s.fields) {
                Type t = TypeResolver.getFromName(f.type.getName(), s.scope);
                if (t == null) {
                    Logger.syntaxError(SemanticErrors.TypeNotFound, f.getCtx());

                }
                f.type = t;
            }
        }


        // 1. Recursively type-check the body of each function
        for (var f : functions) {
            f.block.validate(f.block.scope);
        }


//        Type f = struct.findStructField(fieldPath);
//        Struct struct = scope.getStruct(structRef.type.getName());


        // 2. Secure variable mutability
    }
}
