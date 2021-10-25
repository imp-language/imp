package org.imp.jvm.expression;

import org.apache.commons.lang3.NotImplementedException;
import org.imp.jvm.compiler.DescriptorFactory;
import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.ImpFile;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.LocalVariable;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.exception.Errors;
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

    public FunctionType functionType;

    public final Modifier modifier;

    public FunctionKind kind = FunctionKind.Internal;

    public enum FunctionKind {
        Internal,
        External,
        Standard
    }

    public String name;
    private ImpFile parent;


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
            Modifier modifier, String name,
            List<Identifier> parameters,
            Type returnType,
            Block block,
            ImpFile parent
    ) {
        super();
        this.modifier = modifier;
        this.name = name;
        this.parameters = parameters;
        this.returnType = returnType;
        this.block = block;
        this.parent = parent;

        this.functionType = null;
    }


    public Function(
            FunctionType functionType,
            List<Identifier> parameters,
            Type returnType,
            FunctionKind kind
    ) {
        super();
        this.modifier = null;
        this.block = null;
        this.functionType = functionType;
        this.parameters = parameters;
        this.kind = kind;
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


        functionType = scope.findFunctionType(name, false);
        // If no FunctionTypes of name exist on the current scope,
        if (functionType == null) {
            // Create a new FunctionType and add it to the scope
            functionType = new FunctionType(name, parent, false);
            scope.addFunctionType(functionType);
        }

        if (functionType.getSignatures().containsKey(Function.getDescriptor(parameters))) {
            Logger.syntaxError(Errors.DuplicateFunctionOverloads, this, name);
            return;
        } else {
            functionType.addSignature(Function.getDescriptor(parameters), this);

        }

        if (name != null && !name.equals("main")) {

            Scope newScope = new Scope(scope);
            newScope.functionType = functionType;
            parameters.forEach(param -> newScope.addLocalVariable(new LocalVariable(param.name, param.type)));
            block.scope = newScope;
        }

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


        if (this.kind == FunctionKind.Internal) {

            MethodVisitor mv = cw.visitMethod(access, name, description, null, null);
            mv.visitCode();

            block.generate(mv, block.scope);

            appendReturn(mv, block.scope);

            mv.visitMaxs(-1, -1);
            mv.visitEnd();
        }
    }

    private void appendReturn(MethodVisitor mv, Scope scope) {
        Return r = new Return(new EmptyExpression(BuiltInType.VOID));
        r.generate(mv, scope);
    }

    
}
