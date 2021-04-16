package org.imp.jvm.statement;

import org.apache.commons.lang3.NotImplementedException;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.types.Type;
import org.imp.jvm.expression.Expression;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class Return extends Statement {
    public final Expression expression;


    public Return(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        Type type = expression.type;
        expression.generate(mv, scope);
        mv.visitInsn(type.getReturnOpcode());
    }
}
