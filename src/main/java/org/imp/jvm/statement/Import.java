package org.imp.jvm.statement;

import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.expression.Literal;
import org.objectweb.asm.MethodVisitor;

import java.util.ArrayList;
import java.util.List;

public class Import extends Statement {
    public final Literal moduleName;
    public Scope scope;

    public Import(Literal moduleName, Scope scope) {
        this.moduleName = moduleName;
        this.scope = scope;
    }


    @Override
    public void generate(MethodVisitor mv, Scope scope) {

    }

    @Override
    public void validate(Scope scope) {

    }

    @Override
    public String toString() {
        return "import " + moduleName.value + "";
    }
}
