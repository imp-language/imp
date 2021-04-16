package org.imp.jvm.statement;

import org.apache.commons.lang3.NotImplementedException;
import org.imp.jvm.domain.types.Type;
import org.imp.jvm.expression.Expression;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Return extends Statement {
    public final Expression expression;


    public Return(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void generate(MethodVisitor mv) {
        Type type = expression.type;
        expression.generate(mv);
        mv.visitInsn(type.getReturnOpcode());
    }

    @Override
    public void generate(ClassWriter cw) {
        throw new NotImplementedException("ree");
    }
}
