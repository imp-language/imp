package org.imp.jvm.codegen;

import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.SourceFile;
import org.imp.jvm.types.StructType;
import org.imp.jvm.visitors.CodegenVisitor;
import org.javatuples.Pair;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.util.Map;

public class BytecodeGenerator {

    public Pair<ClassWriter, Map<StructType, ClassWriter>> generate(SourceFile source) {
        // Byte array for each section of the Imp source file

        var cw = new ClassWriter(CodegenVisitor.flags);

//        String qualifiedName = source.path() + "/Class_" + source.name();
        String qualifiedName = FilenameUtils.removeExtension(source.getFullRelativePath());
        cw.visit(CodegenVisitor.CLASS_VERSION, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, qualifiedName, null, "java/lang/Object", null);

        // Add constructor
        var initMv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        initMv.visitCode();
        initMv.visitVarInsn(Opcodes.ALOAD, 0);
        initMv.visitMethodInsn(
                Opcodes.INVOKESPECIAL,
                "java/lang/Object",
                "<init>",
                "()V",
                false
        );
        initMv.visitInsn(Opcodes.RETURN);
        initMv.visitEnd();
        initMv.visitMaxs(-1, -1);

        // Add instance field
        cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "instance", "L" + qualifiedName + ";", null, null);

        var mvStatic = cw.visitMethod(Opcodes.ACC_STATIC, "<clinit>", "()V", null, null);
        mvStatic.visitCode();
        mvStatic.visitTypeInsn(Opcodes.NEW, qualifiedName);
        mvStatic.visitInsn(Opcodes.DUP);
        mvStatic.visitMethodInsn(
                Opcodes.INVOKESPECIAL,
                qualifiedName,
                "<init>",
                "()V",
                false
        );

        mvStatic.visitFieldInsn(Opcodes.PUTSTATIC, qualifiedName, "instance", "L" + qualifiedName + ";");
        mvStatic.visitInsn(Opcodes.RETURN);
        mvStatic.visitEnd();
        mvStatic.visitMaxs(-1, -1);

        // Generate bytecode for each Type defined in the Imp file
        var codegenVisitor = new CodegenVisitor(source.rootEnvironment, source, cw);
        // Todo: refactor CodegenVisitor to use most of the logic from the last pass at codegen

        var results = source.acceptVisitor(codegenVisitor);

        cw.visitEnd();

        return new Pair<>(cw, codegenVisitor.structWriters);
    }
}
