package org.imp.jvm.codegen;

import org.imp.jvm.domain.root.RootUnit;
import org.imp.jvm.domain.scope.Method;
import org.imp.jvm.domain.scope.Property;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.util.Collection;
import java.util.List;

public class ClassGenerator {
    private static final int CLASS_VERSION = 52;
    private final ClassWriter classWriter;

    public ClassGenerator() {
        classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS);
    }

    public ClassWriter generate(RootUnit classDeclaration) {
        String name = classDeclaration.name;
        classWriter.visit(CLASS_VERSION, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, name, null, "java/lang/Object", null);
        List<Method> methods = classDeclaration.methods;
        Collection<Property> fields = classDeclaration.properties;
//        FieldGenerator fieldGenerator = new FieldGenerator(classWriter);
//        fields.forEach(f -> f.accept(fieldGenerator));
//        MethodGenerator methodGenerator = new MethodGenerator(classWriter);
//        methods.forEach(f -> f.accept(methodGenerator));
        classWriter.visitEnd();
        return classWriter;
    }
}
