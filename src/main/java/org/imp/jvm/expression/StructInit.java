package org.imp.jvm.expression;

import org.imp.jvm.compiler.DescriptorFactory;
import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.StructType;
import org.imp.jvm.types.Type;
import org.imp.jvm.exception.SemanticErrors;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StructInit extends Expression {
    public StructType structType;
    private String structName;
    public List<Expression> arguments;
    public final Type owner;

    public StructInit(StructType structType, List<Expression> arguments, Type owner) {
        this.structType = structType;
        this.arguments = arguments;
        this.owner = owner;
        this.type = structType;
    }

    public StructInit(String structName, List<Expression> arguments, Type owner) {
        this.structType = null;
        this.structName = structName;
        this.arguments = arguments;
        this.owner = owner;
        this.type = null;
    }

    public void generate(MethodVisitor mv, Scope scope) {

        String ownerDescriptor = this.type.getInternalName();
        mv.visitTypeInsn(Opcodes.NEW, ownerDescriptor); //NEW instruction takes object decriptor as an input
        mv.visitInsn(Opcodes.DUP); //Duplicate (we do not want invokespecial to "eat" our brand new object

        List<Identifier> params = arguments.stream().map(arg -> new Identifier(arg.type.getName(), arg.type)).collect(Collectors.toList());
        FunctionSignature constructorSignature = new FunctionSignature(params, BuiltInType.VOID);
        String methodDescriptor = DescriptorFactory.getMethodDescriptor(params, BuiltInType.VOID);

        arguments.forEach(argument -> argument.generate(mv, scope));

        // Call struct constructor
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, ownerDescriptor, "<init>", methodDescriptor, false);

    }

    @Override
    public void validate(Scope scope) {
        var st = scope.getStruct(structName);
        if (st == null) {
            Logger.syntaxError(SemanticErrors.TypeNotFound, getCtx());
        } else {
            this.type = st;
            arguments.forEach(expression -> expression.validate(scope));

            if (arguments.size() != st.fields.size()) {
                Logger.syntaxError(SemanticErrors.StructConstructorMismatch, getCtx());
            }
        }
    }

}
