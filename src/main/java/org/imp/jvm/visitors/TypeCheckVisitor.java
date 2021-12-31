package org.imp.jvm.visitors;

import org.imp.jvm.Environment;
import org.imp.jvm.Expr;
import org.imp.jvm.Stmt;
import org.imp.jvm.errors.Comptime;
import org.imp.jvm.types.*;

import java.io.File;
import java.util.Optional;
import java.util.Stack;

public class TypeCheckVisitor implements Stmt.Visitor<Optional<Type>>, Expr.Visitor<Optional<Type>> {

    public final Environment rootEnvironment;
    public final File file;
    public Environment currentEnvironment;

    private final Stack<FuncType> functionStack = new Stack<>();

    public TypeCheckVisitor(Environment rootEnvironment, File file) {
        this.rootEnvironment = rootEnvironment;
        this.file = file;
        this.currentEnvironment = this.rootEnvironment;
    }


    @Override
    public Optional<Type> visit(Stmt stmt) {
        return stmt.accept(this);
    }

    @Override
    public Optional<Type> visitBlockStmt(Stmt.Block block) {
        for (var stmt : block.statements()) {
            stmt.accept(this);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitExport(Stmt.Export stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitEnum(Stmt.Enum stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitStruct(Stmt.Struct struct) {
        var structType = currentEnvironment.getVariableTyped(struct.name().source(), StructType.class);
        if (structType != null) {
            for (var field : structType.fields) {
                if (field.type instanceof UnknownType ut) {
                    var attempt = currentEnvironment.getVariableTyped(ut.typeName, StructType.class);
                    if (attempt != null) {
                        field.type = attempt;
                    }
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<Type> visitExpressionStmt(Stmt.ExpressionStmt stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitFunctionStmt(Stmt.Function stmt) {

        // Find the FunctionType associated with this statement
        var funcType = currentEnvironment.getVariableTyped(stmt.name().source(), FuncType.class);
        if (funcType != null) {
            functionStack.add(funcType);

            for (var param : funcType.parameters) {
                if (param.type instanceof UnknownType ut) {
                    var attempt = currentEnvironment.getVariableTyped(ut.typeName, StructType.class);
                    if (attempt != null) {
                        param.type = attempt;
                    } else {
                        Comptime.TypeNotFound.submit(file, stmt, ut.typeName);
                    }
                }
            }
            currentEnvironment = stmt.body().environment();
            stmt.body().accept(this);

            currentEnvironment = currentEnvironment.getParent();
            functionStack.pop();


        }
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitIf(Stmt.If stmt) {
        var childEnvironment = stmt.trueBlock().environment();
        childEnvironment.setParent(currentEnvironment);
        currentEnvironment = childEnvironment;
        stmt.trueBlock().accept(this);
        stmt.falseStmt().accept(this);

        currentEnvironment = currentEnvironment.getParent();
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitImport(Stmt.Import stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitReturnStmt(Stmt.Return stmt) {
        // Set the return type of the function to the type of the
        // expression you are returning.
        var t = stmt.expr().accept(this);

        if (t.isPresent()) {
            if (functionStack.size() > 0) {
                var newType = t.get();
                var top = functionStack.peek();
                if (top.returnType == BuiltInType.VOID) {
                    top.returnType = t.get();
                } else if (newType != top.returnType) {
                    Comptime.ReturnTypeMismatch.submit(file, stmt, top.name, top.returnType, newType);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitVariable(Stmt.Variable stmt) {

        var expr = stmt.expr();
        var t = expr.accept(this);

        if (t.isPresent()) {
            currentEnvironment.setVariableType(stmt.name().source(), t.get());

        }
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitParameterStmt(Stmt.Parameter stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitFor(Stmt.For stmt) {

        currentEnvironment = stmt.block().environment();
        stmt.condition().accept(this);
        stmt.block().accept(this);
        currentEnvironment = currentEnvironment.getParent();

        return Optional.empty();
    }

    @Override
    public Optional<Type> visitForInCondition(Stmt.ForInCondition stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitTypeAlias(Stmt.TypeAlias stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitAssignExpr(Expr.Assign expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitBinaryExpr(Expr.Binary expr) {
        var t1 = expr.left().accept(this);
        var t2 = expr.right().accept(this);

        if (t1.isEmpty() || t2.isEmpty()) {
            Comptime.Implementation.submit(file, expr, "Types in binary expression not determined.");
        }

        var totalType = t1.get();

        switch (expr.operator().type()) {
            case ADD, SUB, MUL, DIV, MOD -> {
                if (t1.get() instanceof BuiltInType bt1 && t2.get() instanceof BuiltInType bt2) {
                    totalType = BuiltInType.widen(bt1, bt2);
                }
            }
            default -> {
            }
        }


        return Optional.of(totalType);
    }


    @Override
    public Optional<Type> visitBad(Expr.Bad expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitCall(Expr.Call expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitGroupingExpr(Expr.Grouping expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitIdentifierExpr(Expr.Identifier expr) {
        return Optional.of(currentEnvironment.getVariable(expr.identifier().source()));
    }

    @Override
    public Optional<Type> visitIndexAccess(Expr.IndexAccess expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitLogicalExpr(Expr.Logical expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitLiteralExpr(Expr.Literal expr) {
        var t = BuiltInType.getFromToken(expr.literal().type());
        if (t == null) {
            Comptime.Implementation.submit(file, expr, "This should never happen. All literals should be builtin, for now.");
        }
        return Optional.ofNullable(t);
    }

    @Override
    public Optional<Type> visitLiteralList(Expr.LiteralList expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitNew(Expr.New expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitPrefix(Expr.Prefix expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitPostfixExpr(Expr.Postfix expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitPropertyAccess(Expr.PropertyAccess expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitRange(Expr.Range range) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitEmptyList(Expr.EmptyList emptyList) {
        return Optional.empty();
    }
}
