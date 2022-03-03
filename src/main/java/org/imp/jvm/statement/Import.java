package org.imp.jvm.statement;

import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.expression.Literal;
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
        String quotes = "\"";                                    //this should (maybe) remove the need to put quotes in the actual import statement within the code, which would
        moduleName.value = moduleName.concat(quotes);            //make it look a bit better in my opinion. reject this if you want, its kinda a preference thing
        moduleName.value = quotes.concat(moduleName.value);
        return "import " + moduleName.value + "";
    }
}
