package org.imp.jvm.codegen.statement;

import org.imp.jvm.codegen.expression.ExpressionGenerator;
import org.imp.jvm.domain.expression.Expression;
import org.imp.jvm.domain.statement.IfStatement;
import org.imp.jvm.domain.statement.ReturnStatement;
import org.imp.jvm.domain.statement.Statement;
import org.imp.jvm.domain.types.Type;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Optional;


public class ReturnGenerator {
    private final ExpressionGenerator expressionGenerator;
    private final MethodVisitor methodVisitor;

    public ReturnGenerator(ExpressionGenerator expressionGenerator, MethodVisitor methodVisitor) {
        this.expressionGenerator = expressionGenerator;
        this.methodVisitor = methodVisitor;
    }

    public void generate(ReturnStatement returnStatement) {
        Expression expression = returnStatement.getExpression();
        Type type = expression.getType();
        expression.accept(expressionGenerator);
        methodVisitor.visitInsn(type.getReturnOpcode());
    }
}
