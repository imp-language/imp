package org.imp.jvm.codegen.statement;

import org.imp.jvm.codegen.DescriptorFactory;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.statement.Block;
import org.imp.jvm.domain.statement.Function;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class FunctionGenerator {
    private final ClassWriter classWriter;
    private final int access;

    public FunctionGenerator(ClassWriter classWriter, int access) {
        this.classWriter = classWriter;
        this.access = access;
    }

    public void generate(Function function) {
        String name = function.name;
        String description = DescriptorFactory.getMethodDescriptor(function);
        Block block = function.block;
//        Scope scope = block.getScope();

        int access = Opcodes.ACC_PUBLIC + this.access;
        MethodVisitor methodVisitor = classWriter.visitMethod(access, name, description, null, null);
        methodVisitor.visitCode();

//        StatementGenerator statementScopeGenrator = new StatementGenerator(methodVisitor,scope);
//        block.accept(statementScopeGenrator);
//        appendReturnIfNotExists(function, block,statementScopeGenrator);

        methodVisitor.visitMaxs(-1, -1);
        methodVisitor.visitEnd();
    }
}
