package org.imp.jvm.expression;

import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.LocalVariable;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.statement.Struct;
import org.objectweb.asm.MethodVisitor;

public class StructPropertyAccess extends Expression {

    public final Struct struct;
    public final Identifier field;

    public StructPropertyAccess(Struct struct, Identifier field) {
        this.struct = struct;
        this.field = field;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        // ToDo
        // https://github.com/JakubDziworski/Enkel-JVM-language/blob/1527076545f7402a279db2c19f1e28ba7f084585/compiler/src/main/java/com/kubadziworski/bytecodegeneration/expression/ReferenceExpressionGenerator.java#L11
    }

}
