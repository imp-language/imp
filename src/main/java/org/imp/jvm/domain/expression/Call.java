package org.imp.jvm.domain.expression;

import org.imp.jvm.codegen.expression.ExpressionGenerator;
import org.imp.jvm.codegen.statement.StatementGenerator;
import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.types.Type;

import java.util.List;


public abstract class Call implements Expression {

    public Type type;
    public List<Identifier> arguments;


    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void accept(ExpressionGenerator generator) {

    }

    @Override
    public void accept(StatementGenerator generator) {
        System.out.println("huh");
    }
}
