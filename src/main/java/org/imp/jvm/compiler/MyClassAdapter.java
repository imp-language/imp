package org.imp.jvm.compiler;

import org.imp.jvm.parser.visitor.statement.ClassVisitor;
import org.objectweb.asm.util.CheckClassAdapter;

public class MyClassAdapter extends ClassVisitor {

    public MyClassAdapter(CheckClassAdapter checkClassAdapter) {
        super();
    }
}
