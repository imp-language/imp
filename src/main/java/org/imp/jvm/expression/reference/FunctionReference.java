package org.imp.jvm.expression.reference;

import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.types.FunctionType;
import org.objectweb.asm.MethodVisitor;

public class FunctionReference extends Reference {
    public final FunctionType functionType;

    public FunctionReference(FunctionType f) {
        this.functionType = f;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        // LocalVariableReference to a first-class function
        String signature = functionType.toString();
        mv.visitLdcInsn(signature);
    }

    @Override
    public void validate(Scope scope) {
        this.type = functionType;
    }

    @Override
    public String getName() {
        return functionType.name;
    }
}
