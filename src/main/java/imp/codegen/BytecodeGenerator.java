package imp.codegen;

import imp.domain.ImpFile;
import org.objectweb.asm.Opcodes;

public class BytecodeGenerator {
    public byte[] generate(ImpFile impFile) {
//        ClassDeclaration classDeclaration = compilationUnit.getClassDeclaration();
//        ClassGenerator classGenerator = new ClassGenerator();
//        return classGenerator.generate(classDeclaration).toByteArray();3
        var bytes = new byte[]{Opcodes.DUP_X1, Opcodes.AASTORE};
        return bytes;
    }
}
