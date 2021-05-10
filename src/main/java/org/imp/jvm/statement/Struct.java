package org.imp.jvm.statement;

import org.apache.commons.lang3.NotImplementedException;
import org.imp.jvm.codegen.DescriptorFactory;
import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.types.BuiltInType;
import org.imp.jvm.expression.EmptyExpression;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;

public class Struct extends Statement {
    public final Identifier identifier;
    public final List<Identifier> fields;

    public Struct(Identifier identifier, List<Identifier> fields) {
        super();
        this.identifier = identifier;
        this.fields = fields;
    }


    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        throw new NotImplementedException("ree");
    }


    public void generate(ClassWriter cw) {
        throw new NotImplementedException("ree");
    }
}
