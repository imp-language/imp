package org.imp.jvm.legacy.statement;

import org.imp.jvm.legacy.domain.scope.Scope;
import org.imp.jvm.legacy.expression.Literal;
import org.objectweb.asm.MethodVisitor;

public class Import extends Statement {
    public final Literal moduleName;
    public final Scope scope;
    public String alias = null;

    public Import(Literal moduleName, Scope scope) {
        this.moduleName = moduleName;
        this.scope = scope;
    }

    public Import(Literal moduleName, Scope scope, String alias) {
        this.moduleName = moduleName;
        this.scope = scope;
        this.alias = alias;
    }


    @Override
    public void generate(MethodVisitor mv, Scope scope) {

    }

    @Override
    public void validate(Scope scope) {

    }

    public String getValue() {
        String v = moduleName.value;
        return v.substring(1, v.length() - 1);
    }


    @Override
    public String toString() {
        return "import " + moduleName.value + "";
    }
}
