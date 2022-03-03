package org.imp.jvm.legacy.expression;

import org.imp.jvm.legacy.domain.scope.Scope;
import org.imp.jvm.legacy.statement.Statement;
import org.imp.jvm.types.ImpType;
import org.objectweb.asm.MethodVisitor;

public abstract class Expression extends Statement {

    public ImpType type;

    public abstract void generate(MethodVisitor mv, Scope scope);

}

