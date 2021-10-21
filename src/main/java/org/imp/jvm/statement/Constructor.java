package org.imp.jvm.statement;

import org.apache.commons.lang3.NotImplementedException;
import org.imp.jvm.compiler.DescriptorFactory;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.expression.EmptyExpression;
import org.imp.jvm.expression.Function;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.FunctionType;
import org.imp.jvm.types.Modifier;
import org.imp.jvm.types.StructType;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;

// Todo: generate constructor alternates, including empty constructors
public class Constructor extends Function {

    public final StructType structType;

    public Constructor(StructType structType, FunctionType functionType, List<Identifier> parameters, Block block) {
        super(functionType, parameters, BuiltInType.VOID, block, Modifier.NONE);
        this.structType = structType;
    }


    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        throw new NotImplementedException("ree");
    }


    public void generate(ClassWriter cw) {
        String description = DescriptorFactory.getMethodDescriptor(this);

        int access = Opcodes.ACC_PUBLIC;

        MethodVisitor mv = cw.visitMethod(access, "<init>", description, null, null);
        mv.visitCode();
        callSuper(mv);


        block.generate(mv, block.scope);

        if (structType != null) {

            int i = 1;
            for (var param : parameters) {

                mv.visitVarInsn(Opcodes.ALOAD, 0); // loads 'this'
                mv.visitVarInsn(param.type.getLoadVariableOpcode(), i);
                i++; // a more robust solution might be needed for larger constructors

                String ownerInternalName = structType.getInternalName();
                mv.visitFieldInsn(Opcodes.PUTFIELD, ownerInternalName, param.name, param.type.getDescriptor());

            }
        }


        appendReturn(mv, block.scope);

        mv.visitMaxs(-1, -1);
        mv.visitEnd();
    }

    private void callSuper(MethodVisitor mv) {
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        String ownerDescriptor = "java/lang/Object";
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, ownerDescriptor, "<init>", "()V", false);

    }

    private void appendReturn(MethodVisitor mv, Scope scope) {
        Return r = new Return(new EmptyExpression(BuiltInType.VOID));
        r.generate(mv, scope);
    }


}
