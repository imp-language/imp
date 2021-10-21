package org.imp.jvm.expression;

import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.Operator;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.exception.Errors;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.ListType;
import org.imp.jvm.types.overloads.OperatorOverload;
import org.imp.jvm.types.Type;
import org.objectweb.asm.MethodVisitor;

public class MemberIndex extends Expression {

    public final Expression expression;
    public final Expression index;
    public OperatorOverload overload;

    public MemberIndex(Expression expression, Expression index) {
        this.expression = expression;
        this.index = index;
    }


    @Override
    public void validate(Scope scope) {
        // Get type of the index
        index.validate(scope);
        Type indexType = index.type;

        // Index has to be of int type, if not we error
        if (indexType != BuiltInType.INT) {
            Logger.syntaxError(Errors.InvalidIndexType, this, getCtx().getStart().getText(), indexType.getName());
            return;
        }


        // Get type of the expression
        expression.validate(scope);
        Type expressionType = expression.type;

        // Find the operator overload for member index for the given type
        this.overload = expressionType.getOperatorOverload(Operator.INDEX);

        this.overload.setType(expressionType);

        if (this.overload.type instanceof ListType listType) {
            this.overload.setType(listType.contentType);
        }


        // Or error if indexing is not supported for the type
        if (this.overload == null) {
            Logger.syntaxError(Errors.UnsupportedOperator, this, "[]", getCtx().getText(), expressionType);
        }

        // Set type of the expression
        this.overload.validate(scope);
        this.type = this.overload.type;


    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        expression.generate(mv, scope);
        index.generate(mv, scope);
        overload.generate(mv, scope);
    }

}
