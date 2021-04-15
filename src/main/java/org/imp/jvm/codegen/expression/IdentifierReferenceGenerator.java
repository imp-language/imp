package org.imp.jvm.codegen.expression;

import org.imp.jvm.codegen.DescriptorFactory;
import org.imp.jvm.domain.expression.Expression;
import org.imp.jvm.domain.expression.FunctionCall;
import org.imp.jvm.domain.expression.IdentifierReference;
import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.types.Type;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;

public class IdentifierReferenceGenerator {

    private final Scope scope;
    private final MethodVisitor methodVisitor;


    public IdentifierReferenceGenerator(MethodVisitor methodVisitor, Scope scope) {
        this.scope = scope;
        this.methodVisitor = methodVisitor;
    }


    public void generate(IdentifierReference reference) {
        System.out.println(reference);
        // ToDo: refactor IdentifierReference to inherit from Identifier
        String varName = reference.localVariable.getName();
        Type type = reference.getType();
        int index = scope.getLocalVariableIndex(varName);
        methodVisitor.visitVarInsn(type.getLoadVariableOpcode(), index);
    }

}
