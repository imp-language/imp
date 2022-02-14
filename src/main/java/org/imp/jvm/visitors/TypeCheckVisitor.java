package org.imp.jvm.visitors;

import org.imp.jvm.Environment;
import org.imp.jvm.Expr;
import org.imp.jvm.Stmt;
import org.imp.jvm.Util;
import org.imp.jvm.errors.Comptime;
import org.imp.jvm.types.*;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;
import java.util.Stack;

public class TypeCheckVisitor implements IVisitor<Optional<Type>> {

    public final Environment rootEnvironment;
    public final File file;
    private final Stack<FuncType> functionStack = new Stack<>();
    public Environment currentEnvironment;

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
    public Optional<Type> visitAssignExpr(Expr.Assign expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitBad(Expr.Bad expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitBinaryExpr(Expr.Binary expr) {
        var t1 = expr.left.accept(this);
        var t2 = expr.right.accept(this);

        if (t1.isEmpty() || t2.isEmpty()) {
            Comptime.Implementation.submit(file, expr, "Types in binary expression not determined.");
            return Optional.empty();
        }

        if (t1.get() == BuiltInType.ANY || t2.get() == BuiltInType.ANY) {
            Comptime.CannotApplyOperator.submit(file, expr, expr.operator.source(), t1.get(), t2.get());
            return Optional.empty();
        }

        var totalType = t1.get();

        switch (expr.operator.type()) {
            case ADD, SUB, MUL, DIV, MOD -> {
                if (t1.get() instanceof BuiltInType bt1 && t2.get() instanceof BuiltInType bt2) {
                    totalType = BuiltInType.widen(bt1, bt2);
                }
            }
            default -> {
            }
        }

        expr.left.realType = t1.get();
        expr.right.realType = t2.get();

        expr.realType = totalType;

        return Optional.of(totalType);
    }

    @Override
    public Optional<Type> visitBlockStmt(Stmt.Block block) {
        for (var stmt : block.statements) {
            stmt.accept(this);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitCall(Expr.Call expr) {

        var e = expr.item.accept(this);
        if (e.isPresent()) {
            var ft = (FuncType) e.get();
            var returnType = ((FuncType) e.get()).returnType;

            for (var arg : expr.arguments) {
                arg.accept(this);
            }
            return Optional.of(returnType);
        } else {
            // Todo: pass missing method name
            Comptime.MethodNotFound.submit(file, expr.item, "name");
        }

        return Optional.empty();
    }

    @Override
    public Optional<Type> visitEmptyList(Expr.EmptyList emptyList) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitEnum(Stmt.Enum stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitExport(Stmt.Export stmt) {
        stmt.stmt.accept(this);
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitExpressionStmt(Stmt.ExpressionStmt stmt) {
        return stmt.expr.accept(this);
    }

    @Override
    public Optional<Type> visitFor(Stmt.For stmt) {

        currentEnvironment = stmt.block.environment;
        stmt.condition.accept(this);
        stmt.block.accept(this);
        currentEnvironment = currentEnvironment.getParent();

        return Optional.empty();
    }

    @Override
    public Optional<Type> visitForInCondition(Stmt.ForInCondition stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitFunctionStmt(Stmt.Function stmt) {

        // Find the FunctionType associated with this statement
        var funcType = currentEnvironment.getVariableTyped(stmt.name.source(), FuncType.class);
        if (funcType != null) {
            functionStack.add(funcType);
            var childEnvironment = stmt.body.environment;

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
            stmt.body.accept(this);

            currentEnvironment = currentEnvironment.getParent();
            functionStack.pop();


        }
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitGroupingExpr(Expr.Grouping expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitIdentifierExpr(Expr.Identifier expr) {
        var t = currentEnvironment.getVariable(expr.identifier.source());
        if (t != null) {
            expr.realType = t;
        }
        return Optional.ofNullable(t);
    }

    @Override
    public Optional<Type> visitIf(Stmt.If stmt) {
        var childEnvironment = stmt.trueBlock.environment;
        childEnvironment.setParent(currentEnvironment);
        currentEnvironment = childEnvironment;
        stmt.trueBlock.accept(this);
        stmt.falseStmt.accept(this);

        currentEnvironment = currentEnvironment.getParent();
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitImport(Stmt.Import stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitIndexAccess(Expr.IndexAccess expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitLiteralExpr(Expr.Literal expr) {
        var t = expr.realType;
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
    public Optional<Type> visitLogicalExpr(Expr.Logical expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitNew(Expr.New expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitParameterStmt(Stmt.Parameter stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitPostfixExpr(Expr.Postfix expr) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitPrefix(Expr.Prefix expr) {
        return expr.right.accept(this);
    }

    @Override
    public Optional<Type> visitPropertyAccess(Expr.PropertyAccess expr) {
        // For now, assume all expr chains are identifiers
        var exprs = expr.exprs;

        var start = exprs.get(0);
        var startType = start.accept(this);
        Type t;
        if (startType.isPresent()) {
            t = startType.get();
            for (int i = 1; i < exprs.size(); i++) {
                if (t instanceof StructType structType) {
                    var identifier = (Expr.Identifier) exprs.get(i);
                    var id = identifier.identifier.source();
                    if (Arrays.asList(structType.fieldNames).contains(id)) {
                        // Ok, continue to next step
                        int idx = Arrays.asList(structType.fieldNames).indexOf(id);
                        t = structType.fieldTypes[idx];
                    } else {
                        Comptime.PropertyNotFound.submit(file, exprs.get(i), t.getName(), id);
                    }


                } else if (t instanceof BuiltInType builtInType) {
                    System.out.println(builtInType);
                }
            }
            return Optional.of(t);
        } else {
            // Todo: Property access error
            System.err.println("bad");
        }

//        System.exit(-1);

//        var left = expr.left();
//        var right = expr.right();
//        // Left is usually an identifier
//        var lType = left.accept(this);
//        if (right instanceof Expr.Identifier identifier) {
//
//        } else {
//
//        }
//
//        // 1. Get type of left-hand component
////        var rType = right.accept(this);
//        if (lType.isPresent()) {
//            // 2. Find available fields in said type
//            //    (For now, StructType only)
//            var structType = (StructType) lType.get();
//            System.out.println(structType);
//            // 3. See if the right expression is available in the right
//            // Todo: figure out how to do this (it should be a flat list of identifiers as we don't have methods yet)
//            String rightId = "dob";
//            var t = structType.findType(rightId);
//            if (t.isPresent()) {
//                System.out.println(t.get());
//            } else {
//                // Todo: Property access error
//                System.err.println("bad");
//            }
//
//            System.exit(9);
//            var field = StructType.findStructField(structType, rightId);
//            if (field.isPresent()) {
//                var type = field.get().type;
//                System.out.println(type);
//
//                var n = type.getName().split("\\.");
//                var p = Path.of("examples", n[0]);
//
//                var fieldType = ExportTable.get(p.toString(), n[1]);
//                System.out.println(fieldType);
//            }
//        }
        //    (only Identifier, PropertyAccess, and FunctionCall can be a right side expression here)
        // 4. Return the type of the rightmost expression (after recursion)

        return Optional.empty();
    }

    @Override
    public Optional<Type> visitRange(Expr.Range range) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitReturnStmt(Stmt.Return stmt) {
        // Set the return type of the function to the type of the
        // expression you are returning.
        var t = stmt.expr.accept(this);

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
    public Optional<Type> visitStruct(Stmt.Struct struct) {
        var structType = currentEnvironment.getVariableTyped(struct.name.source(), StructType.class);
        if (structType != null) {
            Util.zip(struct.fields, structType.fields, (a, b) -> {
                if (b.type instanceof UnknownType ut) {
                    var attempt = currentEnvironment.getVariableTyped(ut.typeName, StructType.class);
                    if (attempt != null) {
                        b.type = attempt;
                    } else {
                        Comptime.TypeNotFound.submit(file, a, ut.typeName);
                    }
                }
            });

        }

        return Optional.empty();
    }

    @Override
    public Optional<Type> visitType(Stmt.Type stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<Type> visitTypeAlias(Stmt.TypeAlias stmt) {
        var externalType = currentEnvironment.getVariableTyped(stmt.identifier(), ExternalType.class);

        return Optional.empty();
    }

    @Override
    public Optional<Type> visitVariable(Stmt.Variable stmt) {

        var expr = stmt.expr;
        var t = expr.accept(this);

        if (t.isPresent()) {
            currentEnvironment.setVariableType(stmt.name.source(), t.get());
        } else {
            Comptime.TypeNotResolved.submit(file, expr, stmt.name.source());
        }
        return Optional.empty();
    }
}
