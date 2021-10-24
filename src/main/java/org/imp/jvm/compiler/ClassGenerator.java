package org.imp.jvm.compiler;

import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.expression.Function;
import org.imp.jvm.runtime.Box;
import org.imp.jvm.statement.Block;
import org.imp.jvm.statement.ClosureAssignment;
import org.imp.jvm.statement.Constructor;
import org.imp.jvm.types.*;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        String name = "Entry";
        String qualifiedName = packageName + "/" + name;

        classWriter.visit(CLASS_VERSION, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, qualifiedName, null, "java/lang/Object", null);

        List<Function> functions = impFile.functions;
        for (var function : functions) {
            if (function.functionType.name.equals("main") || function.functionType.name.equals("<init>")) {
                function.generate(classWriter);
            }
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

        // Generate function closure
        var closureType = new FunctionType("closure", functionType.parent, false);
        var scope = functionType.signatures.getValue(0).block.scope;
        List<Identifier> closureParams = new ArrayList<>();
        for (var p : scope.closures.values()) {
            var identifier = new Identifier(p.getName(), BuiltInType.BOX);
            closureParams.add(identifier);
        }
//        var closureParams = scope.closures.values().stream().map(lv -> new Identifier(lv.getName(), BuiltInType.BOX)).collect(Collectors.toList());
        var closure = new Function(closureType, closureParams, BuiltInType.VOID, new Block(), Modifier.NONE);

        int i = 1;
        for (var c : closureParams) {
            var assignment = new ClosureAssignment(qualifiedName, c, i);
            closure.block.statements.add(assignment);
            i++;
        }
        closure.generate(classWriter);



        for (var field : closureParams) {
            Type type = field.type;
            String descriptor = type.getDescriptor();
            if (type instanceof BuiltInType builtInType) {
                descriptor = Box.class.descriptorString();
            }
            Object defaultValue = null;
            if (type instanceof BuiltInType) {
                defaultValue = type.getDefaultValue();
            }

            defaultValue = null;

            FieldVisitor fieldVisitor = classWriter.visitField(Opcodes.ACC_PUBLIC, field.name, descriptor, null, defaultValue);
            fieldVisitor.visitEnd();
        }

        // Generate function invokers
        var functions = functionType.signatures.values();
        for (var function : functions) {
            function.generate(classWriter);
        }


        addConstructor(functionType.parent, classWriter, Collections.emptyList(), null);


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


        assert structType.fields != null;

        addConstructor(structType.parent, classWriter, structType.fields, structType);


        for (var field : structType.fields) {
            Type type = field.type;
            String descriptor = type.getDescriptor();
            Object defaultValue = null; // currently not supported

            FieldVisitor fieldVisitor = classWriter.visitField(Opcodes.ACC_PUBLIC, field.name, descriptor, null, defaultValue);
            fieldVisitor.visitEnd();
        }


        classWriter.visitEnd();

        return classWriter;
    }

    /**
     * Generate JVM enum class from an Imp enum definition
     *
     * @param enumType enum data
     * @return bytecode
     */
    public ClassWriter generate(EnumType enumType) {
        // https://github.com/llbit/ow2-asm/blob/master/test/conform/org/objectweb/asm/test/cases/Enum.java
        // Todo: add stuff like valueOf from the above link
        classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS);
        String name = enumType.name;

        String qualifiedName = packageName + "/" + name;
        String descriptor = "L" + qualifiedName + ";";

        classWriter.visit(CLASS_VERSION,
                Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER + Opcodes.ACC_ENUM,
                qualifiedName,
                "Ljava/lang/Enum<" + descriptor + ">;",
                "java/lang/Enum",
                null
        );


        for (String element : enumType.elements.keySet()) {
            FieldVisitor fv = classWriter.visitField(Opcodes.ACC_PUBLIC
                            + Opcodes.ACC_FINAL
                            + Opcodes.ACC_STATIC
                            + Opcodes.ACC_ENUM,
                    element, descriptor, null, null);
            fv.visitEnd();

            // Todo: generate the optional expression of an enum element

        }


        classWriter.visitEnd();

        return classWriter;
    }


    private void addConstructor(ImpFile parent, ClassWriter classWriter, List<Identifier> params, StructType structType) {
        var constructorType = new FunctionType("<init>", parent, false);
        Constructor constructor = new Constructor(structType, constructorType, params, new Block());

        constructor.generate(classWriter);
    }
}
