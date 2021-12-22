package org.imp.jvm.visitors;

import org.imp.jvm.Expr;
import org.imp.jvm.Stmt;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.TypeResolver;
import org.objectweb.asm.MethodVisitor;

public class CodegenVisitor implements Stmt.Visitor<Generator>, Expr.Visitor<Void> {

    private final MethodVisitor mv;
    private Scope scope;

    public CodegenVisitor(MethodVisitor mv, Scope scope) {
        this.mv = mv;
        this.scope = scope;
    }

    @Override
    public Void visitAssignExpr(Expr.Assign expr) {
        return null;
    }

    @Override
    public Void visitBinaryExpr(Expr.Binary expr) {
        return null;
    }

    @Override
    public Void visitBad(Expr.Bad expr) {
        return null;
    }

    @Override
    public Void visitCall(Expr.Call expr) {
        return null;
    }

    @Override
    public Void visitGroupingExpr(Expr.Grouping expr) {
        return null;
    }

    @Override
    public Void visitIdentifierExpr(Expr.Identifier expr) {
        return null;
    }

    @Override
    public Void visitIndexAccess(Expr.IndexAccess expr) {
        return null;
    }

    @Override
    public Void visitLogicalExpr(Expr.Logical expr) {
        return null;
    }

    @Override
    public Void visitLiteralExpr(Expr.Literal expr) {
        BuiltInType type = BuiltInType.getFromToken(expr.literal().type());
        Object transformed = TypeResolver.getValueFromString(expr.literal().source(), type);
        mv.visitLdcInsn(transformed);

        return null;
    }

    @Override
    public Void visitLiteralList(Expr.LiteralList expr) {
        return null;
    }

    @Override
    public Void visitNew(Expr.New expr) {
        return null;
    }

    @Override
    public Void visitPrefix(Expr.Prefix expr) {
        return null;
    }

    @Override
    public Void visitPostfixExpr(Expr.Postfix expr) {
        return null;
    }

    @Override
    public Void visitPropertyAccess(Expr.PropertyAccess expr) {
        return null;
    }

    @Override
    public Void visitRange(Expr.Range range) {
        return null;
    }

    @Override
    public Void visitEmptyList(Expr.EmptyList emptyList) {
        return null;
    }

    @Override
    public Generator visit(Stmt stmt) {
        return null;
    }

    @Override
    public Generator visitBlockStmt(Stmt.Block stmt) {
        return null;
    }

    @Override
    public Generator visitExport(Stmt.Export stmt) {
        return null;
    }

    @Override
    public Generator visitEnum(Stmt.Enum stmt) {
        return null;
    }

    @Override
    public Generator visitStruct(Stmt.Struct stmt) {
        return null;
    }

    @Override
    public Generator visitExpressionStmt(Stmt.ExpressionStmt stmt) {
        return null;
    }

    @Override
    public Generator visitFunctionStmt(Stmt.Function stmt) {
        return null;
    }

    @Override
    public Generator visitIf(Stmt.If stmt) {
        return null;
    }

    @Override
    public Generator visitReturnStmt(Stmt.Return stmt) {
        return null;
    }

    @Override
    public Generator visitVariable(Stmt.Variable stmt) {
        return null;
    }

    @Override
    public Generator visitParameterStmt(Stmt.Parameter stmt) {
        return null;
    }

    @Override
    public Generator visitFor(Stmt.For stmt) {
        return null;
    }

    @Override
    public Generator visitForInCondition(Stmt.ForInCondition stmt) {
        return null;
    }

    @Override
    public Generator visitTypeAlias(Stmt.TypeAlias stmt) {
        return null;
    }
}
