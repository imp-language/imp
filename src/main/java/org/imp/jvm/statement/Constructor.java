package org.imp.jvm.statement;

import org.apache.commons.lang3.NotImplementedException;
import org.imp.jvm.compiler.DescriptorFactory;
import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.types.BuiltInType;
import org.imp.jvm.expression.EmptyExpression;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class Constructor extends Function {

    public Constructor(FunctionSignature signature, Block block) {
        super(signature, block);
        this.signature.type = BuiltInType.VOID;
    }


    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        throw new NotImplementedException("ree");
    }


    public void generate(ClassWriter cw) {
        String name = signature.name;
        String description = DescriptorFactory.getMethodDescriptor(this);
        int access = Opcodes.ACC_PUBLIC;

        // Todo: remove
        description = "()V";
        

        MethodVisitor mv = cw.visitMethod(access, "<init>", description, null, null);
        mv.visitCode();
        callSuper(mv, block.scope);
        block.generate(mv, block.scope);

        appendReturn(mv, block.scope);

        mv.visitMaxs(-1, -1);
        mv.visitEnd();
    }

    private void callSuper(MethodVisitor mv, Scope scope) {
        mv.visitVarInsn(Opcodes.ALOAD, 0);
//        generateArguments(superCall);
//        String ownerDescriptor = scope.getSuperClassInternalName();
        String ownerDescriptor = "java/lang/Object";
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, ownerDescriptor, "<init>", "()V" /*TODO Handle super calls with arguments*/, false);

    }

    private void appendReturn(MethodVisitor mv, Scope scope) {
        Return r = new Return(new EmptyExpression(BuiltInType.VOID));
        r.generate(mv, scope);
    }
}
