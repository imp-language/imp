package org.imp.jvm.statement;

import org.imp.jvm.ImpParserBaseVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public abstract class Statement {

    public abstract void generate(MethodVisitor mv);

    public abstract void generate(ClassWriter cw);

}
