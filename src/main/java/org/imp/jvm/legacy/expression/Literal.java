package org.imp.jvm.legacy.expression;

import org.imp.jvm.legacy.domain.scope.Scope;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.ImpType;
import org.imp.jvm.types.TypeResolver;
import org.objectweb.asm.MethodVisitor;

public class Literal extends Expression {

    public final String value;
    private Object transformed;

    public Literal(ImpType type, String value) {
        super();
        this.type = type;
        this.value = value;
    }

    public void generate(MethodVisitor mv, Scope scope) {
//        int transformed = Integer.parseInt((String) value);
        mv.visitLdcInsn(transformed);
    }

    @Override
    public void validate(Scope scope) {
        transformed = TypeResolver.getValueFromString(value, (BuiltInType) type);

    }

}