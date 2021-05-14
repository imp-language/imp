package org.imp.jvm.expression;

import org.imp.jvm.compiler.DescriptorFactory;
import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.types.StructType;
import org.imp.jvm.domain.types.Type;
import org.imp.jvm.statement.Struct;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;

public class StructInit extends Expression {
    public final Struct struct;
    public List<Expression> arguments;
    public final Type owner;

    public StructInit(Struct struct, List<Expression> arguments, Type owner) {
        this.struct = struct;
        this.arguments = arguments;
        this.owner = owner;
//        this.type = struct;
    }

    public void generate(MethodVisitor mv, Scope scope) {
        // generate arguments

        // Kinda like FunctionCall


    }

}
