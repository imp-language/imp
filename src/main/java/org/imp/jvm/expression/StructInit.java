package org.imp.jvm.expression;

import org.imp.jvm.compiler.DescriptorFactory;
import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.exception.Errors;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.ExternalType;
import org.imp.jvm.types.StructType;
import org.imp.jvm.types.Type;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;
import java.util.stream.Collectors;

public class StructInit extends Expression {
    public final StructType structType;
    public final List<Expression> arguments;
    public final Type owner;
    private final String structName;


    public StructInit(String structName, List<Expression> arguments, Type owner) {
        this.structType = null;
        this.structName = structName;
        this.arguments = arguments;
        this.owner = owner;
        this.type = null;
    }

    public void generate(MethodVisitor mv, Scope scope) {

        String ownerDescriptor = this.type.getInternalName();
        mv.visitTypeInsn(Opcodes.NEW, ownerDescriptor); //NEW instruction takes object descriptor as an input
        mv.visitInsn(Opcodes.DUP); //Duplicate (we do not want invokespecial to "eat" our new object

        List<Identifier> params = arguments.stream().map(arg -> new Identifier(arg.type.getName(), arg.type)).collect(Collectors.toList());
        String methodDescriptor = DescriptorFactory.getMethodDescriptor(params, BuiltInType.VOID);

        arguments.forEach(argument -> argument.generate(mv, scope));

        // Call struct constructor
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, ownerDescriptor, "<init>", methodDescriptor, false);

    }

    @Override
    public void validate(Scope scope) {
        var t = scope.getType(structName);
        if (t instanceof StructType st) {

            if (st == null) {
                Logger.syntaxError(Errors.TypeNotFound, this, getCtx().getStop().getText());

            } else {
                this.type = st;
                arguments.forEach(expression -> expression.validate(scope));

                assert st.fields != null;
                if (arguments.size() != st.fields.size()) {
                    Logger.syntaxError(Errors.StructConstructorMismatch, this, getCtx().getText());
                }
            }
        } else if (t instanceof ExternalType et) {
            this.type = et;
        } else {
            System.err.println("Type is not a struct.");
            System.exit(998);
        }
    }

}
