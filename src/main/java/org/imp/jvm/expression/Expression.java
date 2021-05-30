package org.imp.jvm.expression;

import org.antlr.v4.runtime.Token;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.types.Type;
import org.imp.jvm.statement.Statement;
import org.objectweb.asm.MethodVisitor;

import java.util.Optional;

public abstract class Expression extends Statement {

    public Type type;

    public abstract void generate(MethodVisitor mv, Scope scope);

}

