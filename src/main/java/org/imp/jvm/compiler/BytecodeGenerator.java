package org.imp.jvm.compiler;

import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.root.StaticUnit;
import org.objectweb.asm.ClassWriter;

import java.util.HashMap;
import java.util.Map;

public class BytecodeGenerator {
    public Map<String, byte[]> generate(ImpFile impFile) {
        ClassGenerator classGenerator = new ClassGenerator(impFile.packageName);

        // Byte array for each section of the Imp source file
        var code = new HashMap<String, byte[]>();

        // Generate bytecode for impFile.StaticUnit
        StaticUnit staticUnit = impFile.staticUnit;
        ClassWriter staticWriter = classGenerator.generate(impFile);
        code.put(impFile.packageName + "/" + "Entry", staticWriter.toByteArray());

        // Generate bytecode for each Struct defined in the imp file
        for (var struct : impFile.structTypes) {
            code.put(impFile.packageName + "/" + struct.identifier.name, classGenerator.generate(struct).toByteArray());
        }

        return code;
    }
}
