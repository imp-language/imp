package org.imp.jvm.codegen;

import org.imp.jvm.domain.Identifier;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

public class FieldGenerator {
    private final ClassWriter classWriter;
    private final int access;

    public FieldGenerator(ClassWriter classWriter, int access) {
        this.classWriter = classWriter;
        this.access = access;
    }

    public void generate(Identifier field) {
        String name = field.name;
        String descriptor = field.type.getDescriptor();
        FieldVisitor fieldVisitor = classWriter.visitField(access + Opcodes.ACC_PUBLIC, name, descriptor, null, 0);
        fieldVisitor.visitEnd();
    }
}
