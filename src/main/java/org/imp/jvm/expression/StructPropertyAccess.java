package org.imp.jvm.expression;

import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.types.StructType;
import org.imp.jvm.exception.SemanticErrors;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;

public class StructPropertyAccess extends Expression {

    public final LocalVariableReference parent;
    public final List<Identifier> fieldPath;
    public List<Identifier> validatedPath = null;

    public StructPropertyAccess(LocalVariableReference parent, List<Identifier> fieldPath) {
        this.parent = parent;
        this.fieldPath = fieldPath;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        Identifier last = validatedPath.get(validatedPath.size() - 1);
        String name = last.name;

        String ownerInternalName = this.parent.type.getInternalName();
        String descriptor = last.type.getDescriptor();

        int index = scope.getLocalVariableIndex(parent.localVariable.name);
        if (parent.localVariable.name.equals("p")) {
            // Todo: BAD- need to decide how scope will work
            index = 0;
        }
        mv.visitVarInsn(Opcodes.ALOAD, index);

        mv.visitFieldInsn(Opcodes.GETFIELD, ownerInternalName, name, descriptor);
    }

    @Override
    public void validate(Scope scope) {
        // Find the type of the parent reference
        parent.validate(scope);

        // Given the path, set the final type of this property access
        var parentType = parent.type;
        if (parentType instanceof StructType parentStructType) {

            int originalSize = fieldPath.size();
            validatedPath = parentStructType.findStructField(parentStructType, fieldPath);

            if (validatedPath.size() == originalSize) {
                // The type of this expression is the type of the last node of validated path
                this.type = validatedPath.get(validatedPath.size() - 1).type;
            }


        } else {
            Logger.syntaxError(SemanticErrors.PrimitiveTypePropertyAccess, getCtx());
            // Todo: property access on primitive types
            // Will need to use boxing and unboxing probably
        }

    }

    public Identifier getLast() {
        return validatedPath.get(validatedPath.size() - 1);
    }

}
