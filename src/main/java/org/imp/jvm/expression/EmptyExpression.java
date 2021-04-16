package org.imp.jvm.expression;

import org.imp.jvm.codegen.DescriptorFactory;
import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.types.Type;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;

public class EmptyExpression extends Expression {

    public EmptyExpression(Type type) {
        this.type = type;
    }

    public void generate(MethodVisitor mv, Scope scope) {
        // Do nothing
    }

}
