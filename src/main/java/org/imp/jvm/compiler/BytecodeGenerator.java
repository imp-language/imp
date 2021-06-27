package org.imp.jvm.compiler;

import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.root.StaticUnit;
import org.imp.jvm.statement.Function;
import org.imp.jvm.types.FunctionType;
import org.objectweb.asm.ClassWriter;

import java.util.*;

public class BytecodeGenerator {
    public Map<String, byte[]> generate(ImpFile impFile) {
        ClassGenerator classGenerator = new ClassGenerator(impFile.packageName);

        // Byte array for each section of the Imp source file
        var code = new HashMap<String, byte[]>();

        // Generate bytecode for impFile.StaticUnit
        StaticUnit staticUnit = impFile.staticUnit;
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
            code.put(impFile.packageName + "/" + className, classGenerator.generate(functionType).toByteArray());

        }

        return code;
    }
}
