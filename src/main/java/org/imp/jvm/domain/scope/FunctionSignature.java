package org.imp.jvm.domain.scope;

import org.imp.jvm.expression.EmptyExpression;
import org.imp.jvm.statement.Return;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.FunctionType;
import org.imp.jvm.types.Type;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;
import java.util.stream.Collectors;

// Todo: deprecate
public class FunctionSignature extends Identifier {
    public final List<Identifier> parameters;

    public final FunctionType functionType;

    public FunctionSignature(FunctionType functionType, List<Identifier> parameters, Type returnType) {
        this.name = functionType.name;
        this.functionType = functionType;
        this.parameters = parameters;
        this.type = returnType;

    }

    public FunctionSignature(List<Identifier> parameters, Type returnType) {
        this.name = "<init>";
        this.parameters = parameters;
        this.type = returnType;
        this.functionType = null;

    }

    public boolean matches(String otherSignatureName, List<Identifier> otherSignatureParameters) {
        boolean namesAreEqual = this.name.equals(otherSignatureName);
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

    @Override
    public String toString() {
        // ToDo: this throws an exception, probably something with null parameters or return type
        String params = parameters.stream().map(Object::toString)
                .collect(Collectors.joining(", "));
        return "(" + String.join(", ", params) + ") " + type;
    }

    public String toStringRepr() {
        // ToDo: this throws an exception, probably something with null parameters or return type
        String params = parameters.stream().map(parameters -> parameters.type.toString())
                .collect(Collectors.joining(", "));
        return "(" + String.join(", ", params) + ") " + type;
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {

    }

    public void generate(ClassWriter cw) {
        String name = functionType.name;
        name = "invoke";
        String description = "DescriptorFactory.getMethodDescriptor(this)";
        int access = Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC;

        MethodVisitor mv = cw.visitMethod(access, name, description, null, null);
        mv.visitCode();

//        block.generate(mv, block.scope);
//
//        appendReturn(mv, block.scope);

        mv.visitMaxs(-1, -1);
        mv.visitEnd();
    }

    private void appendReturn(MethodVisitor mv, Scope scope) {
        Return r = new Return(new EmptyExpression(BuiltInType.VOID));
        r.generate(mv, scope);
    }
}
