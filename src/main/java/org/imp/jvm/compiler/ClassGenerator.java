package org.imp.jvm.compiler;

import org.imp.jvm.domain.root.RootUnit;
import org.imp.jvm.domain.root.StaticUnit;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Method;
import org.imp.jvm.statement.Struct;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.util.Collection;
import java.util.List;

public class ClassGenerator {
    private static final int CLASS_VERSION = 52;
    private final ClassWriter classWriter;

    public ClassGenerator() {
        classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS);
//        classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
//        classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
    }

    public ClassWriter generate(RootUnit classDeclaration) {
        String name = classDeclaration.name;
        classWriter.visit(CLASS_VERSION, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, name, null, "java/lang/Object", null);
        List<Method> methods = classDeclaration.methods;
        Collection<Identifier> fields = classDeclaration.properties;
//        FieldGenerator fieldGenerator = new FieldGenerator(classWriter);
//        fields.forEach(f -> f.accept(fieldGenerator));
//        MethodGenerator methodGenerator = new MethodGenerator(classWriter);
//        methods.forEach(f -> f.accept(methodGenerator));
        classWriter.visitEnd();
        return classWriter;
    }

    /**
     * Generate JVM static class from an Imp file
     *
     * @param staticUnit StaticUnit
     * @return bytecode
     */
    public ClassWriter generate(StaticUnit staticUnit) {
        String name = staticUnit.name;
//        classWriter.visit(CLASS_VERSION, Opcodes.ACC_STATIC + Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, name, null, "java/lang/Object", null);
        classWriter.visit(CLASS_VERSION, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, name, null, "java/lang/Object", null);

        List<org.imp.jvm.statement.Function> functions = staticUnit.functions;
        functions.forEach(f -> f.generate(classWriter));

        FieldGenerator fieldGenerator = new FieldGenerator(classWriter, Opcodes.ACC_STATIC);
        List<Identifier> properties = staticUnit.properties;
        for (var prop : properties) {
            prop.accept(fieldGenerator);
        }
        classWriter.visitEnd();

        return classWriter;
    }

    /**
     * Generate JVM class from an Imp struct definition
     *
     * @param struct Struct type
     * @return bytecode
     */
    public ClassWriter generate(Struct struct) {
        return null;
    }
}
