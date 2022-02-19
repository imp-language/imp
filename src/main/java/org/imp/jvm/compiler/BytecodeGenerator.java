package org.imp.jvm.compiler;

import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.SourceFile;
import org.imp.jvm.types.FunctionType;
import org.imp.jvm.types.StructType;
import org.imp.jvm.visitors.CodegenVisitor;
import org.javatuples.Pair;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

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

    public Pair<ClassWriter, Map<StructType, ClassWriter>> generate(SourceFile source) {
        // Byte array for each section of the Imp source file

        var cw = new ClassWriter(CodegenVisitor.flags);

//        String qualifiedName = source.path() + "/Class_" + source.name();
        String qualifiedName = FilenameUtils.removeExtension(source.getFullRelativePath());
        cw.visit(CodegenVisitor.CLASS_VERSION, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, qualifiedName, null, "java/lang/Object", null);

        // Generate bytecode for each Type defined in the Imp file
        var codegenVisitor = new CodegenVisitor(source.rootEnvironment, source, cw);
        // Todo: refactor CodegenVisitor to use most of the logic from the last pass at codegen

        var results = source.acceptVisitor(codegenVisitor);

        cw.visitEnd();

        return new Pair<>(cw, codegenVisitor.structWriters);
    }
}
