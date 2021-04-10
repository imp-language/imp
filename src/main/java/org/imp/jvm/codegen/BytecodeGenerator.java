package org.imp.jvm.codegen;

import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.root.StaticUnit;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.util.HashMap;
import java.util.Map;

public class BytecodeGenerator {
    public Map<String, byte[]> generate(ImpFile impFile) {
        // Byte array for each section of the Imp source file
        var code = new HashMap<String, byte[]>();

        // Generate bytecode for impFile.StaticUnit
        StaticUnit staticUnit = impFile.staticUnit;
        ClassGenerator classGenerator = new ClassGenerator();
        ClassWriter staticWriter = classGenerator.generate(staticUnit);
        code.put("main", staticWriter.toByteArray());

        // Generate bytecode for each impFile.ClassUnit[]
        for (var classUnit : impFile.classUnits) {
            code.put("", classGenerator.generate(classUnit).toByteArray());
        }

        return code;
    }
}
