package org.imp.jvm.codegen.expression;

import org.imp.jvm.domain.CompareSign;
import org.imp.jvm.domain.expression.Expression;
import org.imp.jvm.domain.expression.Literal;
import org.imp.jvm.domain.expression.RelationalExpression;
import org.imp.jvm.domain.types.Type;
import org.imp.jvm.domain.types.TypeResolver;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class LiteralExpressionGenerator {

    private final MethodVisitor methodVisitor;


    public LiteralExpressionGenerator(MethodVisitor methodVisitor) {
        this.methodVisitor = methodVisitor;
    }

    public void generate(Literal literal) {
        Type type = literal.getType();
        Object value = literal.value;
        // ToDo: support types other than integers
        int transformed = Integer.parseInt((String) value);
        methodVisitor.visitLdcInsn(transformed);
    }


}
