package org.imp.jvm.domain;


import org.imp.jvm.domain.root.ClassUnit;
import org.imp.jvm.domain.root.StaticUnit;
import org.imp.jvm.domain.types.Type;
import org.imp.jvm.domain.types.TypeResolver;
import org.imp.jvm.statement.Struct;

import java.util.ArrayList;
import java.util.List;

public class ImpFile {
    // Filename
    public String name;

    public String packageName = "";

    // Top-level entities in the source file.
    public final StaticUnit staticUnit;

    // Todo: remove, this is replaced by structs
    // All classes defined in the source file.
    public final List<ClassUnit> classUnits = new ArrayList<>();

    // All structs defined in the source file.
    public final List<Struct> structs = new ArrayList<>();

    public ImpFile(StaticUnit staticUnit, String name) {
        this.name = name;
        this.packageName = name;
//        this.name = FilenameUtils.getBaseName(file.getName());
        this.staticUnit = staticUnit;
    }

    public String getClassName() {
        return name;
    }


    public void validate() {
//        System.out.println(this.structs.get(1).toString());
        System.out.println(this.staticUnit.functions);

        // 0. Ensure all struct fields have valid types
        for (var s : structs) {
            for (var f : s.fields) {
                Type t = TypeResolver.getFromName(f.type.getName(), s.scope);
                // todo: error when no type found
//                Logger.syntaxError(SemanticErrors.TypeNotFound, s.getLine());
                f.type = t;
            }
        }

        // 1. Validate and store all function signatures
        for (var f : staticUnit.functions) {

        }


        // 2. Recursively type-check the body of each function
        for (var f : staticUnit.functions) {
            f.block.validate();
        }


//        Type f = struct.findStructField(fieldPath);
//        Struct struct = scope.getStruct(structRef.type.getName());


        // 2. Secure variable mutability
    }
}
