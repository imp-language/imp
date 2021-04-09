package org.imp.jvm.domain.expression;

import org.imp.jvm.codegen.statement.StatementGenerator;
import org.imp.jvm.domain.types.BuiltInType;
import org.imp.jvm.domain.types.Type;


public class Literal implements Expression {

    public final Type type;
    public final String value;

    public Literal(Type type, String value) {
        this.type = type;
        this.value = value;
    }


    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void accept(StatementGenerator generator) {
        System.out.println("huh");
    }
}
