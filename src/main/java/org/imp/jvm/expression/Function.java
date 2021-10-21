package org.imp.jvm.expression;

import org.apache.commons.lang3.NotImplementedException;
import org.imp.jvm.compiler.DescriptorFactory;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.statement.Block;
import org.imp.jvm.statement.Return;
import org.imp.jvm.statement.Statement;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.FunctionType;
import org.imp.jvm.types.Modifier;
import org.imp.jvm.types.Type;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;
import java.util.stream.Collectors;

public class Function extends Expression {
    public final Block block;
    public final List<Identifier> parameters;
    public final Type returnType;

    public final FunctionType functionType;

    public final Modifier modifier;

    public boolean isStandard = false;

    public Function(
            FunctionType functionType,
            List<Identifier> parameters,
            Type returnType,
            Block block,
            Modifier modifier
    ) {
        super();
        this.modifier = modifier;
        this.block = block;
        this.functionType = functionType;
        this.parameters = parameters;
        this.returnType = returnType;
    }


    public Function(
            FunctionType functionType,
            List<Identifier> parameters,
            Type returnType,
            boolean isStandard
    ) {
        super();
        this.modifier = null;
        this.block = null;
        this.functionType = functionType;
        this.parameters = parameters;
        this.isStandard = isStandard;
        this.returnType = returnType;
    }


    @Override
    public List<Statement> getChildren() {
        return List.of(block);
    }

    @SuppressWarnings("UnnecessaryLocalVariable")
    static public String getDescriptor(List<Identifier> identifiers) {
        String params = identifiers.stream().map(parameters -> parameters.type.getDescriptor())
                .collect(Collectors.joining(", "));
        return params;
    }

    @Override
    public String toString() {
        if (functionType != null) {
            return functionType.name + this.toStringRepr();
        }
        return "null signature";
    }


    public String toStringRepr() {
        String params = parameters.stream().map(parameters -> parameters.type.toString())
                .collect(Collectors.joining(", "));
        return "(" + String.join(", ", params) + ") " + returnType;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        throw new NotImplementedException("reeb");
    }

    @Override
    public void validate(Scope scope) {
        assert block != null;
        block.validate(block.scope);


    }


    public void generate(ClassWriter cw) {
        String name = functionType.name;
        int access = Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC;
        if (!name.equals("main")) {
            if (!name.equals("closure")) {
                name = "invoke";
            }
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
