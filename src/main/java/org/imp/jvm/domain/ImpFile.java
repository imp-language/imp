package org.imp.jvm.domain;


import org.imp.jvm.domain.root.ClassUnit;
import org.imp.jvm.domain.root.StaticUnit;
import org.imp.jvm.domain.types.Type;
import org.imp.jvm.domain.types.TypeResolver;
import org.imp.jvm.expression.StructPropertyAccess;
import org.imp.jvm.statement.Assignment;
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
        System.out.println(this);
//        System.out.println(this.structs.get(1).toString());

        // 0. Ensure all struct fields have valid types
        for (var s : structs) {
            for (var f : s.fields) {
                Type t = TypeResolver.getFromName(f.type.getName(), s.scope);
                // todo: error when no type found
//                Logger.syntaxError(SemanticErrors.TypeNotFound, s.getLine());
                f.type = t;
            }
        }

        System.out.println(this);

        // 1. Recursively type-check property access expressions
        staticUnit.functions.forEach(function -> {
            function.block.validate();

//            function.block.statements.forEach(statement -> {
//                if (statement instanceof Assignment) {
//                    Assignment assignment = (Assignment) statement;
//                    // Property access expressions could be found in the recipient or the provider of an Assignment statement
//                    if (assignment.recipient instanceof StructPropertyAccess) {
//                        StructPropertyAccess access = (StructPropertyAccess) assignment.recipient;
//
//                    }
//
//                    if (assignment.provider instanceof StructPropertyAccess) {
//                        // Todo: this has to recurse somehow.
//                    }
//                }
//            });
        });


//        Type f = struct.findStructField(fieldPath);
//        Struct struct = scope.getStruct(structRef.type.getName());


        // 2. Secure variable mutability
    }
}
