package org.imp.jvm.compiler;

import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.root.RootUnit;
import org.imp.jvm.domain.root.StaticUnit;
import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Method;
import org.imp.jvm.expression.LocalVariableReference;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.FunctionType;
import org.imp.jvm.types.StructType;
import org.imp.jvm.types.Type;
import org.imp.jvm.statement.Block;
import org.imp.jvm.statement.Constructor;
import org.imp.jvm.statement.Function;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ClassGenerator {
    private static final int CLASS_VERSION = 52;
    private ClassWriter classWriter;
    private final String packageName;

    public ClassGenerator(String packageName) {
        this.packageName = packageName;
    }


    /**
     * Generate JVM static class from an Imp file
     *
     * @param impFile Imp source file
     * @return bytecode
     */
    public ClassWriter generate(ImpFile impFile) {
        classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS);
        String name = impFile.name;
        name = "Entry";
        String qualifiedName = packageName + "/" + name;

        classWriter.visit(CLASS_VERSION, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, qualifiedName, null, "java/lang/Object", null);

        List<Function> functions = impFile.functions;
        for (var function : functions) {
            if (function.functionType.name.equals("main") || function.functionType.name.equals("<init>")) {
                function.generate(classWriter);
            }
        }
//        functions.forEach(f -> f.generate(classWriter));

        FieldGenerator fieldGenerator = new FieldGenerator(classWriter, Opcodes.ACC_STATIC);
        List<Identifier> properties = impFile.staticUnit.properties;
        for (var prop : properties) {
            System.out.println("hmmmm");
            prop.accept(fieldGenerator);
        }
        classWriter.visitEnd();

        return classWriter;
    }

    /**
     * Generate JVM class for an Imp function type,
     * including closures.
     *
     * @param functionType Imp function type
     * @return bytecode
     */
    public ClassWriter generate(FunctionType functionType) {
        classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS);
        String name = "Function_" + functionType.name;
        String qualifiedName = packageName + "/" + name;

        classWriter.visit(CLASS_VERSION, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, qualifiedName, null, "java/lang/Object", null);

        List<Function> functionSignatures = functionType.signatures;
        for (var signature : functionSignatures) {
            signature.generate(classWriter);
        }

        var constructorType = new FunctionType("<init>", functionType.parent);
        var constructorSignature = new FunctionSignature(constructorType, Collections.emptyList(), BuiltInType.VOID);

        Constructor constructor = new Constructor(null, constructorType, Collections.emptyList(), new Block());

        constructor.generate(classWriter);

//        List<Function> functions = functionType.functions;
//        functions.forEach(f -> f.generate(classWriter));

//        FieldGenerator fieldGenerator = new FieldGenerator(classWriter, Opcodes.ACC_STATIC);
//        List<Identifier> properties = functionType.staticUnit.properties;
//        for (var prop : properties) {
//            System.out.println("hmmmm");
//            prop.accept(fieldGenerator);
//        }
        classWriter.visitEnd();

        return classWriter;
    }

    /**
     * Generate JVM class from an Imp struct definition
     * Eventually these should be compiled to value types inlined for the JVM.
     * For now it's just a class.
     *
     * @param structType Struct type
     * @return bytecode
     */
    public ClassWriter generate(StructType structType) {
        classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS);
        String name = structType.identifier.name;

        String qualifiedName = packageName + "/" + name;

        classWriter.visit(CLASS_VERSION, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, qualifiedName, null, "java/lang/Object", null);

        List<Function> functions = new ArrayList<>();

        assert structType.fields != null; // Todo: code smell
        var constructorType = new FunctionType("<init>", structType.parent);
        var constructorSignature = new FunctionSignature(constructorType, structType.fields, BuiltInType.VOID);

        Constructor constructor = new Constructor(structType, constructorType, structType.fields, new Block());


        functions.add(constructor);
        functions.forEach(f -> f.generate(classWriter));

        for (var field : structType.fields) {
            Type type = field.type;
            String descriptor = type.getDescriptor();
            Object defaultValue = null;
            if (type instanceof BuiltInType) {
                defaultValue = ((BuiltInType) type).getDefaultValue();
            }

            defaultValue = null;

            FieldVisitor fieldVisitor = classWriter.visitField(Opcodes.ACC_PUBLIC, field.name, descriptor, null, defaultValue);
            fieldVisitor.visitEnd();
        }


        classWriter.visitEnd();

        return classWriter;
    }
}
