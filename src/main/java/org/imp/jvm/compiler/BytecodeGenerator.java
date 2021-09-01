package org.imp.jvm.compiler;

import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.types.FunctionType;
import org.objectweb.asm.ClassWriter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BytecodeGenerator {
    public Map<String, byte[]> generate(ImpFile impFile) {
        ClassGenerator classGenerator = new ClassGenerator(impFile.packageName);

        // Byte array for each section of the Imp source file
        var code = new HashMap<String, byte[]>();

        // Generate bytecode for impFile.StaticUnit
        ClassWriter staticWriter = classGenerator.generate(impFile);
        code.put(impFile.packageName + "/" + "Entry", staticWriter.toByteArray());

        // Generate bytecode for each Struct defined in the Imp file
        for (var struct : impFile.structTypes) {
            code.put(impFile.packageName + "/" + struct.identifier.name, classGenerator.generate(struct).toByteArray());
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
}
