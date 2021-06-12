package org.imp.jvm.expression;

import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.types.BuiltInType;
import org.imp.jvm.domain.types.Type;
import org.imp.jvm.domain.types.TypeResolver;
import org.objectweb.asm.MethodVisitor;

public class Literal extends Expression {

    public final String value;

    public Literal(Type type, String value) {
        super();
        this.type = type;
        this.value = value;
    }

    public void generate(MethodVisitor mv, Scope scope) {
        Object transformed = TypeResolver.getValueFromString(value, (BuiltInType) type);
//        int transformed = Integer.parseInt((String) value);
        mv.visitLdcInsn(transformed);
    }

    @Override
    public void validate(Scope scope) {

    }

}
