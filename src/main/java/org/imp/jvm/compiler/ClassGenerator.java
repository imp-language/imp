package org.imp.jvm.compiler;

import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.expression.StructPropertyAccess;
import org.imp.jvm.runtime.Box;
import org.imp.jvm.statement.*;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.FunctionType;
import org.imp.jvm.types.StructType;
import org.imp.jvm.types.Type;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
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

        // Generate function closure
        var closureType = new FunctionType("closure", functionType.parent);
        var scope = functionType.signatures.get(0).block.scope;
        List<Identifier> closureParams = new ArrayList<>();
        for (var p : scope.closures.values()) {
            var identifier = new Identifier(p.getName(), BuiltInType.BOX);
            closureParams.add(identifier);
        }
//        var closureParams = scope.closures.values().stream().map(lv -> new Identifier(lv.getName(), BuiltInType.BOX)).collect(Collectors.toList());
        var closure = new Function(closureType, closureParams, BuiltInType.VOID, new Block());

        int i = 1;
        for (var c : closureParams) {
            var assignment = new ClosureAssignment(qualifiedName, c, i);
            closure.block.statements.add(assignment);
            i++;
        }
//        var assignment = new AssignmentStatement(null, );
        closure.generate(classWriter);

        /*
         * Todo: assign fields in body of closure()
         * Todo: variable reference GETFIELD in function body
         * Todo: variable
         */


        System.out.println(Box.class.descriptorString());

        // Todo: add fields for closure variables
        for (var field : closureParams) {
            Type type = field.type;
            String descriptor = type.getDescriptor();
            if (type instanceof BuiltInType builtInType) {
                descriptor = Box.class.descriptorString();
            }
            Object defaultValue = null;
            if (type instanceof BuiltInType) {
                defaultValue = ((BuiltInType) type).getDefaultValue();
            }

            defaultValue = null;

            FieldVisitor fieldVisitor = classWriter.visitField(Opcodes.ACC_PUBLIC, field.name, descriptor, null, defaultValue);
            fieldVisitor.visitEnd();
        }

        // Generate function invokers
        List<Function> functions = functionType.signatures;
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

        List<Function> functions = new ArrayList<>();

        assert structType.fields != null; // Todo: code smell

        addConstructor(structType.parent, classWriter, structType.fields, structType);

//        functions.forEach(f -> f.generate(classWriter));

        for (var field : structType.fields) {
            Type type = field.type;
            String descriptor = type.getDescriptor();
            Object defaultValue = null;
            if (type instanceof BuiltInType) {
                defaultValue = ((BuiltInType) type).getDefaultValue();
            }

            // Todo: fix
            defaultValue = null;

            FieldVisitor fieldVisitor = classWriter.visitField(Opcodes.ACC_PUBLIC, field.name, descriptor, null, defaultValue);
            fieldVisitor.visitEnd();
        }


        classWriter.visitEnd();

        return classWriter;
    }

    private void addConstructor(ImpFile parent, ClassWriter classWriter, List<Identifier> params, StructType structType) {
        var constructorType = new FunctionType("<init>", parent);
        Constructor constructor = new Constructor(structType, constructorType, params, new Block());

        constructor.generate(classWriter);
    }
}
