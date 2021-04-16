package org.imp.jvm.statement;

import org.imp.jvm.domain.scope.Scope;
import org.objectweb.asm.MethodVisitor;

public abstract class Statement {

    public abstract void generate(MethodVisitor mv, Scope scope);


}
