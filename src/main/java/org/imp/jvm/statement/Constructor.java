package org.imp.jvm.statement;

import org.apache.commons.lang3.NotImplementedException;
import org.imp.jvm.compiler.DescriptorFactory;
import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.expression.*;
import org.imp.jvm.types.BuiltInType;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.List;

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
//        description = "()V";
        // Todo: better constructors

        int access = Opcodes.ACC_PUBLIC;

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
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, ownerDescriptor, "<init>", "()V", false);

    }

    private void appendReturn(MethodVisitor mv, Scope scope) {
        Return r = new Return(new EmptyExpression(BuiltInType.VOID));
        r.generate(mv, scope);
    }

    public void assignFields(List<Identifier> fields) {
        System.out.println("f");
        for (var field : fields) {
            List<Expression> expressions = new ArrayList<>();
            expressions.add(new Literal(BuiltInType.STRING, "a"));
            this.block.statements.add(new FunctionCall("log", expressions, null));
//            StructPropertyAccess fieldAccess = new StructPropertyAccess();
//
//            AssignmentStatement assignment = new AssignmentStatement();
        }
    }
}
