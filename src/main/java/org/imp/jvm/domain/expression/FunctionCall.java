package org.imp.jvm.domain.expression;

import org.imp.jvm.codegen.expression.ExpressionGenerator;
import org.imp.jvm.codegen.statement.StatementGenerator;
import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.types.Type;

import java.util.List;

public class FunctionCall extends Call {
    public final FunctionSignature signature;

    public FunctionCall(FunctionSignature signature, List<Expression> arguments) {
        this.signature = signature;
        this.arguments = arguments;
        this.type = signature.type;
    }


    @Override
    public void accept(ExpressionGenerator generator) {
        generator.generate(this);
    }

    @Override
    public void accept(StatementGenerator generator) {
        generator.generate(this);
    }

}