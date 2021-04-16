package org.imp.jvm.expression;

import org.imp.jvm.domain.CompareSign;
import org.imp.jvm.domain.Operator;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.types.BuiltInType;
import org.imp.jvm.domain.types.Type;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Arithmetic extends Expression {
    public final Expression left;
    public final Expression right;
    public final Operator op;

    public Arithmetic(Expression left, Expression right, Operator op) {
        this.op = op;
        this.type = getCommonType(left, right);
        this.left = left;
        this.right = right;
    }

    public void generate(MethodVisitor mv, Scope scope) {

        // ToDo: currently only addition is implemented

        left.generate(mv, scope);
        right.generate(mv, scope);

        mv.visitInsn(type.getAddOpcode());

    }

    private static Type getCommonType(Expression left, Expression right) {
        if (right.type == BuiltInType.STRING) return BuiltInType.STRING;
        return left.type;
    }


}
