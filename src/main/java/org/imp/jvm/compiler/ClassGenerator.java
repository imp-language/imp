package org.imp.jvm.compiler;

import org.imp.jvm.domain.root.RootUnit;
import org.imp.jvm.domain.root.StaticUnit;
import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Method;
import org.imp.jvm.domain.types.BuiltInType;
import org.imp.jvm.domain.types.Type;
import org.imp.jvm.statement.Block;
import org.imp.jvm.statement.Constructor;
import org.imp.jvm.statement.Function;
import org.imp.jvm.statement.Struct;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.ModuleVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ClassGenerator {
    private static final int CLASS_VERSION = 52;
    private ClassWriter classWriter;
    private final String packageName;

    public ClassGenerator(String packageName) {
        this.packageName = packageName;
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
        classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS);
        String name = staticUnit.name;
        name = "Entry";
        String qualifiedName = packageName + "/" + name;

        classWriter.visit(CLASS_VERSION, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, qualifiedName, null, "java/lang/Object", null);

        List<Function> functions = staticUnit.functions;
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
     * Eventually these should be compiled to value types inlined for the JVM.
     * For now it's just a class.
     *
     * @param struct Struct type
     * @return bytecode
     */
    public ClassWriter generate(Struct struct) {
        classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS);
        String name = struct.identifier.name;
        
        String qualifiedName = packageName + "/" + name;

        classWriter.visit(CLASS_VERSION, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, qualifiedName, null, "java/lang/Object", null);

        List<Function> functions = new ArrayList<>();
        var constructorSignature = new FunctionSignature(name, Collections.emptyList(), BuiltInType.VOID);
        functions.add(new Constructor(constructorSignature, new Block()));
        functions.forEach(f -> f.generate(classWriter));

        for (var field : struct.fields) {
            Type type = field.type;
            String descriptor = type.getDescriptor();
            Object defaultValue = null;
            if (type instanceof BuiltInType) {
                defaultValue = ((BuiltInType) type).getDefaultValue();
            }


            FieldVisitor fieldVisitor = classWriter.visitField(Opcodes.ACC_PUBLIC, field.name, descriptor, null, defaultValue);
            fieldVisitor.visitEnd();
        }


        classWriter.visitEnd();

        return classWriter;
    }
}
