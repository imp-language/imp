package org.imp.jvm.domain.expression;

import org.imp.jvm.codegen.expression.ExpressionGenerator;
import org.imp.jvm.codegen.statement.StatementGenerator;
import org.imp.jvm.domain.CompareSign;
import org.imp.jvm.domain.scope.LocalVariable;
import org.imp.jvm.domain.types.Type;


public class IdentifierReference implements Expression {

    public final LocalVariable localVariable;

    public IdentifierReference(LocalVariable localVariable) {
        this.localVariable = localVariable;
    }


    @Override
    public Type getType() {
        return localVariable.getType();
    }

    @Override
    public void accept(ExpressionGenerator generator) {
        generator.generate(this);
    }

    @Override
    public void accept(StatementGenerator generator) {
        System.out.println("huh");
    }
}
