package org.imp.jvm.expression;

import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.LocalVariable;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.statement.Struct;
import org.objectweb.asm.MethodVisitor;

import java.util.ArrayList;
import java.util.List;

public class StructPropertyAccess extends Expression {

    public final IdentifierReference parent;
    public final List<Identifier> fieldPath;


    public StructPropertyAccess(IdentifierReference parent, List<Identifier> fieldPath) {
        this.parent = parent;
        this.fieldPath = fieldPath;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        // ToDo
        // https://github.com/JakubDziworski/Enkel-JVM-language/blob/1527076545f7402a279db2c19f1e28ba7f084585/compiler/src/main/java/com/kubadziworski/bytecodegeneration/expression/ReferenceExpressionGenerator.java#L11
    }

}
