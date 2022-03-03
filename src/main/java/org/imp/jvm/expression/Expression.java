package org.imp.jvm.expression;

import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.statement.Statement;
import org.imp.jvm.types.ImpType;
import org.objectweb.asm.MethodVisitor;

public abstract class Expression extends Statement {

    public ImpType type;

    public abstract void generate(MethodVisitor mv, Scope scope);

}

