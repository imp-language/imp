package org.imp.jvm.statement;

import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.expression.Expression;
import org.imp.jvm.types.ImpType;
import org.objectweb.asm.MethodVisitor;

public class Return extends Statement {
    public final Expression expression;


    public Return(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        ImpType type = expression.type;
        expression.generate(mv, scope);
        mv.visitInsn(type.getReturnOpcode());
    }

    @Override
    public void validate(Scope scope) {
        expression.validate(scope);
    }
}
