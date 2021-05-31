package org.imp.jvm.expression;

import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.LocalVariable;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.types.StructType;
import org.imp.jvm.domain.types.Type;
import org.imp.jvm.exception.SemanticErrors;
import org.imp.jvm.statement.Struct;
import org.objectweb.asm.MethodVisitor;

import java.util.ArrayList;
import java.util.List;

public class StructPropertyAccess extends Expression {

    public final IdentifierReference parent;
    public final List<Identifier> fieldPath;


    public StructPropertyAccess(IdentifierReference parent, List<Identifier> fieldPath) {
        this.parent = parent;
        this.fieldPath = fieldPath;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        // ToDo
        // https://github.com/JakubDziworski/Enkel-JVM-language/blob/1527076545f7402a279db2c19f1e28ba7f084585/compiler/src/main/java/com/kubadziworski/bytecodegeneration/expression/ReferenceExpressionGenerator.java#L11
    }

    @Override
    public void validate() {
        // Given the path, set the final type of this property access
        var parentType = parent.type;
        if (parentType instanceof StructType) {
            var structType = (StructType) parentType;
            Struct parentStruct = structType.struct;
            // Todo: assert parentStruct != null
            assert parentStruct != null;

            List<Identifier> validatedPath = parentStruct.findStructField(parentStruct, fieldPath);
            System.out.println(validatedPath);

        } else {
            Logger.syntaxError(SemanticErrors.PrimitiveTypePropertyAccess, getLine());
            // Todo: property access on primitive types
            // Will need to use boxing and unboxing probably
        }

        System.out.println(fieldPath);
//        Type f = struct.findStructField(fieldPath);
//        Struct struct = scope.getStruct(structRef.type.getName());
    }

}
