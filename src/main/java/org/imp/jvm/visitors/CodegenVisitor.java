package org.imp.jvm.visitors;

import org.imp.jvm.Environment;
import org.imp.jvm.Expr;
import org.imp.jvm.Stmt;
import org.imp.jvm.errors.Comptime;
import org.imp.jvm.types.FuncType;
import org.imp.jvm.types.StructType;
import org.imp.jvm.types.Type;
import org.imp.jvm.types.UnknownType;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;

public class CodegenVisitor implements IVisitor<Optional<ClassWriter>> {
    private static final int CLASS_VERSION = 61;
    public final Environment rootEnvironment;
    public final File file;
    public final Map<String, byte[]> code;
    private final Stack<FuncType> functionStack = new Stack<>();
    public Environment currentEnvironment;
    int flags = ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS;
    private ClassWriter cw = new ClassWriter(flags);


    public CodegenVisitor(Environment rootEnvironment, File file, Map<String, byte[]> code) {
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
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitBad(Expr.Bad expr) {
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitBinaryExpr(Expr.Binary expr) {

        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitBlockStmt(Stmt.Block block) {

        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitCall(Expr.Call expr) {
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitEmptyList(Expr.EmptyList emptyList) {
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitEnum(Stmt.Enum stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitExport(Stmt.Export stmt) {
        return stmt.stmt().accept(this);
    }

    @Override
    public Optional<ClassWriter> visitExpressionStmt(Stmt.ExpressionStmt stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitFor(Stmt.For stmt) {

        currentEnvironment = stmt.block().environment();
        stmt.condition().accept(this);
        stmt.block().accept(this);
        currentEnvironment = currentEnvironment.getParent();

        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitForInCondition(Stmt.ForInCondition stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitFunctionStmt(Stmt.Function stmt) {

        // Find the FunctionType associated with this statement
        var funcType = currentEnvironment.getVariableTyped(stmt.name().source(), FuncType.class);
        if (funcType != null) {
            functionStack.add(funcType);
            var childEnvironment = stmt.body().environment();

            for (var param : funcType.parameters) {
                if (param.type instanceof UnknownType ut) {
                    var attempt = currentEnvironment.getVariableTyped(ut.typeName, StructType.class);
                    if (attempt != null) {
                        param.type = attempt;
                        childEnvironment.setVariableType(param.name, attempt);

                    } else {
                        Comptime.TypeNotFound.submit(file, stmt, ut.typeName);
                    }
                }
            }

            currentEnvironment = childEnvironment;
            stmt.body().accept(this);

            currentEnvironment = currentEnvironment.getParent();
            functionStack.pop();


        }
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitGroupingExpr(Expr.Grouping expr) {
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitIdentifierExpr(Expr.Identifier expr) {
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitIf(Stmt.If stmt) {
        var childEnvironment = stmt.trueBlock().environment();
        childEnvironment.setParent(currentEnvironment);
        currentEnvironment = childEnvironment;
        stmt.trueBlock().accept(this);
        stmt.falseStmt().accept(this);

        currentEnvironment = currentEnvironment.getParent();
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitImport(Stmt.Import stmt) {
        // Nothing needed for codegen in Import statements
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitIndexAccess(Expr.IndexAccess expr) {
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitLiteralExpr(Expr.Literal expr) {
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitLiteralList(Expr.LiteralList expr) {
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitLogicalExpr(Expr.Logical expr) {
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitNew(Expr.New expr) {
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitParameterStmt(Stmt.Parameter stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitPostfixExpr(Expr.Postfix expr) {
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitPrefix(Expr.Prefix expr) {
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitPropertyAccess(Expr.PropertyAccess expr) {
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitRange(Expr.Range range) {
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitReturnStmt(Stmt.Return stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitStruct(Stmt.Struct struct) {
        // Codegen a new JVM class representing an Imp struct
        var structType = currentEnvironment.getVariableTyped(struct.name().source(), StructType.class);

        String name = structType.name;
        String qualifiedName = "packageName" + "/" + name;
        cw.visit(CLASS_VERSION, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, qualifiedName, null, "java/lang/Object", null);

        for (int i = 0; i < structType.fieldNames.length; i++) {
            Type type = structType.fieldTypes[i];
            String descriptor = type.getDescriptor();
            String n = structType.fieldNames[i];

            FieldVisitor fieldVisitor = cw.visitField(Opcodes.ACC_PUBLIC, n, descriptor, null, null);
            fieldVisitor.visitEnd();
        }

        cw.visitEnd();

        code.put("packageName" + "/" + structType.name, cw.toByteArray());

        return Optional.of(cw);
    }

    @Override
    public Optional<ClassWriter> visitType(Stmt.Type stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitTypeAlias(Stmt.TypeAlias stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitVariable(Stmt.Variable stmt) {
        return Optional.empty();
    }
}
