package org.imp.jvm.statement;

import org.apache.commons.lang3.NotImplementedException;
import org.imp.jvm.compiler.DescriptorFactory;
import org.imp.jvm.domain.scope.FunctionSignature;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.expression.EmptyExpression;
import org.imp.jvm.types.FunctionType;
import org.imp.jvm.types.Type;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;
import java.util.stream.Collectors;

public class Function extends Statement {
    public final Block block;
    public final List<Identifier> parameters;
    public final Type returnType;

    public final FunctionType functionType;


    public Function(FunctionType functionType, List<Identifier> parameters, Type returnType, Block block) {
        super();
        this.block = block;
        this.functionType = functionType;
        this.parameters = parameters;
        this.returnType = returnType;
    }

    @Override
    public String toString() {
        if (functionType != null) {
            return functionType.name + this.toStringRepr();
        }
        return "null signature";
    }


    public String toStringRepr() {
        // ToDo: this throws an exception, probably something with null parameters or return type
        String params = parameters.stream().map(parameters -> parameters.type.toString())
                .collect(Collectors.joining(", "));
        return "(" + String.join(", ", params) + ") " + returnType;
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
        String name = functionType.name;
        int access = Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC;
        if (!name.equals("main")) {
            name = "invoke";
            access = Opcodes.ACC_PUBLIC;
        }
        String description = DescriptorFactory.getMethodDescriptor(this);

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

    public boolean matches(String otherSignatureName, List<Identifier> otherSignatureParameters) {
        boolean namesAreEqual = this.functionType.name.equals(otherSignatureName);
        if (!namesAreEqual) return false;

        return doParametersMatch(parameters, otherSignatureParameters);
    }

    private boolean doParametersMatch(List<Identifier> a, List<Identifier> b) {
        if (a.size() != b.size()) return false;
        for (int i = 0; i < a.size(); i++) {
            Identifier aIdent = a.get(i);
            Identifier bIdent = b.get(i);
            if (!aIdent.type.equals(bIdent.type)) {
                return false;
            }
        }
        return true;
    }
}
