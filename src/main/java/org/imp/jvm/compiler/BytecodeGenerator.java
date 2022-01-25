package org.imp.jvm.compiler;

import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.SourceFile;
import org.imp.jvm.types.FunctionType;
import org.imp.jvm.visitors.CodegenVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BytecodeGenerator {
    public Map<String, byte[]> generate(ImpFile impFile) {
        String cleanedPath = impFile.packageName.replace(File.separatorChar, '/');
        ClassGenerator classGenerator = new ClassGenerator(cleanedPath);

        // Byte array for each section of the Imp source file
        var code = new HashMap<String, byte[]>();

        // Generate bytecode for impFile.StaticUnit
        ClassWriter staticWriter = classGenerator.generate(impFile);
        code.put(impFile.packageName + "/" + "Entry", staticWriter.toByteArray());
//        code.put("examples/scratch/Entry", staticWriter.toByteArray());

        // Generate bytecode for each Struct defined in the Imp file
        for (var struct : impFile.structTypes) {
            code.put(impFile.packageName + "/" + struct.name, classGenerator.generate(struct).toByteArray());
        }

        // Generate bytecode for each Enum defined in the Imp file
        for (var enumType : impFile.enumTypes) {
            code.put(impFile.packageName + "/" + enumType.name, classGenerator.generate(enumType).toByteArray());
        }

        // Generate bytecode for each function defined in the Imp file
        Set<FunctionType> functionTypes = new HashSet<>();
        for (var function : impFile.functions) {
            FunctionType functionType = function.functionType;
            if (!functionTypes.contains(functionType) && functionType != null) {
                if (!functionType.name.equals("main") && !functionType.name.equals("<init>")) {
                    functionTypes.add(functionType);
                }
            }
        }

        for (var functionType : functionTypes) {
            String className = "Function_" + functionType.name;
            var a = classGenerator.generate(functionType).toByteArray();
            code.put(impFile.packageName + "/" + className, a);

        }

        return code;
    }

    public Map<String, byte[]> generate(SourceFile source) {
        // Byte array for each section of the Imp source file
        var code = new HashMap<String, byte[]>();
        String cleanedPath = source.path();

        // Generate bytecode for each Type defined in the Imp file
        var codegenVisitor = new CodegenVisitor(source.rootEnvironment, source, code);
        // Todo: refactor CodegenVisitor to use most of the logic from the last pass at codegen

        var results = source.acceptVisitor(codegenVisitor);

        System.out.println(results);



        /*
        ClassGenerator classGenerator = new ClassGenerator(cleanedPath);


        // Generate bytecode for impFile.StaticUnit
        ClassWriter staticWriter = classGenerator.generate(source);
        code.put(source.packageName + "/" + "Entry", staticWriter.toByteArray());
//        code.put("examples/scratch/Entry", staticWriter.toByteArray());



        // Generate bytecode for each Enum defined in the Imp file
        for (var enumType : source.enumTypes) {
            code.put(source.packageName + "/" + enumType.name, classGenerator.generate(enumType).toByteArray());
        }

        // Generate bytecode for each function defined in the Imp file
        Set<FunctionType> functionTypes = new HashSet<>();
        for (var function : source.functions) {
            FunctionType functionType = function.functionType;
            if (!functionTypes.contains(functionType) && functionType != null) {
                if (!functionType.name.equals("main") && !functionType.name.equals("<init>")) {
                    functionTypes.add(functionType);
                }
            }
        }

        for (var functionType : functionTypes) {
            String className = "Function_" + functionType.name;
            var a = classGenerator.generate(functionType).toByteArray();
            code.put(source.packageName + "/" + className, a);

        }

        return code;

        */

        return codegenVisitor.code;
    }
}
