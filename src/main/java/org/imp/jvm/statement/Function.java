package org.imp.jvm.statement;

import org.apache.commons.lang3.NotImplementedException;
import org.imp.jvm.compiler.DescriptorFactory;
import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.expression.EmptyExpression;
import org.imp.jvm.types.FunctionType;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Function extends Statement {
    public final Block block;
    public final FunctionSignature signature;


    public Function(FunctionSignature signature, Block block) {
        super();
        this.block = block;
        this.signature = signature;
    }

    @Override
    public String toString() {
        if (signature != null) {
            return signature.toString();
        }
        return "null signature";
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        throw new NotImplementedException("ree");
    }

    @Override
    public void validate(Scope scope) {
        block.validate(scope);
    }


    public void generate(ClassWriter cw) {
        String name = signature.name;
        String description = DescriptorFactory.getMethodDescriptor(this);
        int access = Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC;

        MethodVisitor mv = cw.visitMethod(access, name, description, null, null);
        mv.visitCode();

        block.generate(mv, block.scope);

        appendReturn(mv, block.scope);

        mv.visitMaxs(-1, -1);
        mv.visitEnd();
    }

    private void appendReturn(MethodVisitor mv, Scope scope) {
        Return r = new Return(new EmptyExpression(BuiltInType.VOID));
        r.generate(mv, scope);
    }
}
