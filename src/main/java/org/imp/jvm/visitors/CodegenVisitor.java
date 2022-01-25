package org.imp.jvm.visitors;

import org.apache.commons.lang3.NotImplementedException;
import org.imp.jvm.Environment;
import org.imp.jvm.Expr;
import org.imp.jvm.Stmt;
import org.imp.jvm.compiler.DescriptorFactory;
import org.imp.jvm.domain.SourceFile;
import org.imp.jvm.types.*;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Map;
import java.util.Optional;
import java.util.Stack;

public class CodegenVisitor implements IVisitor<Optional<ClassWriter>> {
    private static final int CLASS_VERSION = 61;
    public final Environment rootEnvironment;
    public final SourceFile file;
    public final Map<String, byte[]> code;
    final int flags = ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS;
    private final Stack<FuncType> functionStack = new Stack<>();
    private final ClassWriter cw = new ClassWriter(flags);
    public Environment currentEnvironment;


    public CodegenVisitor(Environment rootEnvironment, SourceFile file, Map<String, byte[]> code) {
        this.rootEnvironment = rootEnvironment;
        this.file = file;
        this.currentEnvironment = this.rootEnvironment;
        this.code = code;
    }

    @Override
    public Optional<ClassWriter> visit(Stmt stmt) {
        return stmt.accept(this);
    }

    @Override
    public Optional<ClassWriter> visitAssignExpr(Expr.Assign expr) {
        throw new NotImplementedException("method not implemented");
    }

    @Override
    public Optional<ClassWriter> visitBad(Expr.Bad expr) {
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitBinaryExpr(Expr.Binary expr) {
        throw new NotImplementedException("method not implemented");
    }

    @Override
    public Optional<ClassWriter> visitBlockStmt(Stmt.Block block) {
        for (var stmt : block.statements()) {
            stmt.accept(this);
        }
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitCall(Expr.Call expr) {
        throw new NotImplementedException("method not implemented");
    }

    @Override
    public Optional<ClassWriter> visitEmptyList(Expr.EmptyList emptyList) {
        throw new NotImplementedException("method not implemented");
    }

    @Override
    public Optional<ClassWriter> visitEnum(Stmt.Enum stmt) {
        throw new NotImplementedException("method not implemented");
    }

    @Override
    public Optional<ClassWriter> visitExport(Stmt.Export stmt) {
        return stmt.stmt().accept(this);
    }

    @Override
    public Optional<ClassWriter> visitExpressionStmt(Stmt.ExpressionStmt stmt) {
        throw new NotImplementedException("method not implemented");
    }

    @Override
    public Optional<ClassWriter> visitFor(Stmt.For stmt) {
        throw new NotImplementedException("method not implemented");
    }

    @Override
    public Optional<ClassWriter> visitForInCondition(Stmt.ForInCondition stmt) {
        throw new NotImplementedException("method not implemented");
    }

    @Override
    public Optional<ClassWriter> visitFunctionStmt(Stmt.Function stmt) {
        var funcType = currentEnvironment.getVariableTyped(stmt.name().source(), FuncType.class);
        functionStack.add(funcType);
        var childEnvironment = stmt.body().environment();

        // Generate class
        String name = "Function_" + funcType.name;
        String qualifiedName = file.path() + "/" + name;
        cw.visit(CLASS_VERSION, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, qualifiedName, null, "java/lang/Object", null);

        // Generate invoker method
        String descriptor = DescriptorFactory.getMethodDescriptor(funcType.parameters, funcType.returnType);
        funcType.mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "invoke", descriptor, null, null);
        funcType.mv.visitCode();

        // Generate function body
        currentEnvironment = childEnvironment;
        stmt.body().accept(this);

        // Finish generating invoker function

        funcType.mv.visitMaxs(-1, -1);
        funcType.mv.visitEnd();

        cw.visitEnd();
        code.put(qualifiedName, cw.toByteArray());

        currentEnvironment = currentEnvironment.getParent();
        functionStack.pop();

        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitGroupingExpr(Expr.Grouping expr) {
        throw new NotImplementedException("method not implemented");
    }

    @Override
    public Optional<ClassWriter> visitIdentifierExpr(Expr.Identifier expr) {

        var funcType = functionStack.peek();
        var type = currentEnvironment.getVariable(expr.identifier().source());
        var index = currentEnvironment.getLocalVariableIndex(expr.identifier().source());
        funcType.mv.visitVarInsn(type.getLoadVariableOpcode(), index);
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitIf(Stmt.If stmt) {
        throw new NotImplementedException("method not implemented");
    }

    @Override
    public Optional<ClassWriter> visitImport(Stmt.Import stmt) {
        // Nothing needed for codegen in Import statements
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitIndexAccess(Expr.IndexAccess expr) {
        throw new NotImplementedException("method not implemented");
    }

    @Override
    public Optional<ClassWriter> visitLiteralExpr(Expr.Literal expr) {
        var funcType = functionStack.peek();
        var transformed = TypeResolver.getValueFromString(expr.literal().source(), BuiltInType.getFromToken(expr.literal().type()));
        funcType.mv.visitLdcInsn(transformed);
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitLiteralList(Expr.LiteralList expr) {
        throw new NotImplementedException("method not implemented");
    }

    @Override
    public Optional<ClassWriter> visitLogicalExpr(Expr.Logical expr) {
        throw new NotImplementedException("method not implemented");
    }

    @Override
    public Optional<ClassWriter> visitNew(Expr.New expr) {
        throw new NotImplementedException("method not implemented");
    }

    @Override
    public Optional<ClassWriter> visitParameterStmt(Stmt.Parameter stmt) {

        throw new NotImplementedException("method not implemented");
    }

    @Override
    public Optional<ClassWriter> visitPostfixExpr(Expr.Postfix expr) {

        throw new NotImplementedException("method not implemented");
    }

    @Override
    public Optional<ClassWriter> visitPrefix(Expr.Prefix expr) {

        throw new NotImplementedException("method not implemented");
    }

    @Override
    public Optional<ClassWriter> visitPropertyAccess(Expr.PropertyAccess expr) {

        throw new NotImplementedException("method not implemented");
    }

    @Override
    public Optional<ClassWriter> visitRange(Expr.Range range) {

        throw new NotImplementedException("method not implemented");
    }

    @Override
    public Optional<ClassWriter> visitReturnStmt(Stmt.Return stmt) {
        var funcType = functionStack.peek();

        stmt.expr().accept(this);
//        var type = currentEnvironment.getVariable(stmt.identifier());
//        Type type = expression.type;
        // Todo: need to bubble up the type from expression
        funcType.mv.visitInsn(BuiltInType.INT.getReturnOpcode());
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitStruct(Stmt.Struct struct) {
        // Codegen a new JVM class representing an Imp struct
        var structType = currentEnvironment.getVariableTyped(struct.name().source(), StructType.class);

        String name = structType.name;
        String qualifiedName = file.path() + "/" + name;
        cw.visit(CLASS_VERSION, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, qualifiedName, null, "java/lang/Object", null);

//        addConstructor(structType.parent, classWriter, structType.fields, structType);

        for (int i = 0; i < structType.fieldNames.length; i++) {
            Type type = structType.fieldTypes[i];
            String descriptor = type.getDescriptor();
            String n = structType.fieldNames[i];

            FieldVisitor fieldVisitor = cw.visitField(Opcodes.ACC_PUBLIC, n, descriptor, null, null);
            fieldVisitor.visitEnd();
        }

        cw.visitEnd();

        code.put(qualifiedName, cw.toByteArray());

        return Optional.of(cw);
    }

    @Override
    public Optional<ClassWriter> visitType(Stmt.Type stmt) {
        throw new NotImplementedException("method not implemented");
    }

    @Override
    public Optional<ClassWriter> visitTypeAlias(Stmt.TypeAlias stmt) {
        throw new NotImplementedException("method not implemented");
    }

    @Override
    public Optional<ClassWriter> visitVariable(Stmt.Variable stmt) {
        // The fact that each variable in the block (environment) is known at this point
        // means it's possible to do compiler optimizations like moving all declarations
        // to one initialization at the beginning of the block or function.
        var funcType = functionStack.peek();

        stmt.expr().accept(this);
        int index = currentEnvironment.getLocalVariableIndex(stmt.identifier());
        var type = currentEnvironment.getVariable(stmt.identifier());
        funcType.mv.visitVarInsn(type.getStoreVariableOpcode(), index);
        return Optional.empty();
    }

}
