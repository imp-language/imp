package org.imp.jvm.legacy.statement;

import org.imp.jvm.legacy.domain.scope.Scope;
import org.imp.jvm.legacy.expression.Function;
import org.objectweb.asm.MethodVisitor;

public class Export extends Statement {
    public final Function function;
    public final Scope scope;

    public Export(Function function, Scope scope) {
        this.function = function;
        this.scope = scope;
    }


    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        function.generate(mv, scope);
    }

    @Override
    public void validate(Scope scope) {
    }

    @Override
    public String toString() {
        return "export " + function.toString() + "";
    }
}
