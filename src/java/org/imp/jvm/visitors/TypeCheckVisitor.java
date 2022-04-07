package org.imp.jvm.visitors;

import org.imp.jvm.Environment;
import org.imp.jvm.SourceFile;
import org.imp.jvm.Util;
import org.imp.jvm.errors.Comptime;
import org.imp.jvm.parser.Expr;
import org.imp.jvm.parser.Stmt;
import org.imp.jvm.parser.tokenizer.Location;
import org.imp.jvm.parser.tokenizer.TokenType;
import org.imp.jvm.types.*;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;
import java.util.Stack;
import java.util.concurrent.atomic.AtomicReference;

public class TypeCheckVisitor implements IVisitor<Optional<ImpType>> {

    public final Environment rootEnvironment;
    public final File file;
    private final Stack<FuncType> functionStack = new Stack<>();
    public Environment currentEnvironment;

    public TypeCheckVisitor(Environment rootEnvironment, SourceFile source) {
        this.rootEnvironment = rootEnvironment;
        this.file = source.file;
        this.currentEnvironment = this.rootEnvironment;
    }


    @Override
    public Optional<ImpType> visit(Stmt stmt) {
        return stmt.accept(this);
    }

    @Override
    public Optional<ImpType> visitAlias(Stmt.Alias stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitAssignExpr(Expr.Assign expr) {
        expr.left.accept(this);
        expr.right.accept(this);

        if (expr.left.realType != expr.right.realType) {
            if (expr.left instanceof Expr.Identifier id) {
                Comptime.BadAssignment.submit(file, expr, id.identifier.source(), expr.left.realType.getName(), expr.right.realType.getName());
            } else {
                Comptime.Implementation.submit(file, expr, "Assignment not implemented for any recipient but identifier yet");
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitBad(Expr.Bad expr) {
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitBinaryExpr(Expr.Binary expr) {
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
            case EQUAL, NOTEQUAL, LT, GT, LE, GE -> {
                if (t1.get() instanceof BuiltInType bt1 && t2.get() instanceof BuiltInType bt2) {
                    // check if two values can be compared
                    if (expr.operator.type() != TokenType.EQUAL && expr.operator.type() != TokenType.NOTEQUAL) {
                        if (bt1 == BuiltInType.STRING || bt2 == BuiltInType.STRING) {
                            Comptime.CannotApplyOperator.submit(file, expr, expr.operator.source(), bt1.getName(), bt2.getName());
                        } else if (bt1 == BuiltInType.BOOLEAN || bt2 == BuiltInType.BOOLEAN) {
                            Comptime.CannotApplyOperator.submit(file, expr, expr.operator.source(), bt1.getName(), bt2.getName());

                        }
                    }
                    totalType = BuiltInType.BOOLEAN;
                }
            }
            case AND, OR -> {
                if (t1.get() instanceof BuiltInType bt1 && t2.get() instanceof BuiltInType bt2) {
                    // check if two values can be compared
                    if (bt1 != BuiltInType.BOOLEAN || bt2 != BuiltInType.BOOLEAN) {
                        Comptime.CannotApplyOperator.submit(file, expr, expr.operator.source(), bt1.getName(), bt2.getName());
                    }
                    totalType = BuiltInType.BOOLEAN;
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
    public Optional<ImpType> visitBlockStmt(Stmt.Block block) {
        for (var stmt : block.statements) {
            stmt.accept(this);
        }
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitCall(Expr.Call expr) {

        var e = expr.item.accept(this);
        if (e.isPresent()) {
            var t = e.get();
            switch (t) {
                case FuncType ft -> {
                    if (ft.parameters.size() != expr.arguments.size()) {
                        Comptime.FunctionSignatureMismatch.submit(file, expr.item, ft.name, "a");
                        return Optional.empty();
                    }
                    AtomicReference<ImpType> returnType = new AtomicReference<>(ft.returnType);

                    // Make sure parameter and argument types match.
                    Util.zip(ft.parameters, expr.arguments, (param, arg) -> {
                        var at = arg.accept(this);
                        if (at.isPresent()) {
                            var argType = at.get();
                            if (argType == BuiltInType.VOID) {
                                Comptime.VoidUsage.submit(file, arg);
                            }
                            if (!TypeResolver.typesMatch(argType, param.type)) {
                                Comptime.ParameterTypeMismatch.submit(file, arg, argType.getName(), param.type.getName());
                            }

                            if (argType instanceof ListType lt) {
                                if (ft.name.equals("at")) {
//                                returnType.set(lt.contentType);
                                    // Todo: need to cast here
                                }
                            }
                        }
                    });

                    expr.realType = returnType.get();
                    return Optional.of(returnType.get());
                }
                case StructType st -> {
                    if (st.fieldNames.length != expr.arguments.size()) {
                        Comptime.FunctionSignatureMismatch.submit(file, expr.item, st.name, "a");
                        return Optional.empty();
                    }
                    // constructor function
                    for (var arg : expr.arguments) {
                        arg.accept(this);
                    }
                    expr.realType = st;
                    return Optional.of(st);
                }
                default -> throw new IllegalStateException("Unexpected value: " + t);
            }

        } else {
            // Todo: pass missing method name
            Comptime.MethodNotFound.submit(file, expr.item, "name");
        }

        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitEmpty(Expr.Empty empty) {
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitEmptyList(Expr.EmptyList emptyList) {
        var t = emptyList.realType;
        return Optional.of(t);
    }

    @Override
    public Optional<ImpType> visitEnum(Stmt.Enum stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitExport(Stmt.Export stmt) {
        stmt.stmt.accept(this);
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitExpressionStmt(Stmt.ExpressionStmt stmt) {
        return stmt.expr.accept(this);
    }

    @Override
    public Optional<ImpType> visitFor(Stmt.For stmt) {

        currentEnvironment = stmt.block.environment;

        // visit expression (could be an iterator or a list)
        var b = stmt.expr.accept(this);
        if (b.isPresent()) {
            switch (b.get()) {
                case ExternalType et/*for now assume it's an iterator every time*/ -> {
                    if (et.foundClass().getName().equals("java.util.Iterator")) {
                        currentEnvironment.setVariableType(stmt.name.source(), BuiltInType.INT);
                    }
                }
                case ListType lt -> Util.Exit("Todo: iterate over lists", 97);
                default -> Comptime.NotIterable.submit(file, stmt.expr, b.get().getName());
            }
        }

        // visit block
        stmt.block.accept(this);

        // reset environment pointer
        currentEnvironment = currentEnvironment.getParent();

        return Optional.empty();

    }

    @Override
    public Optional<ImpType> visitFunctionStmt(Stmt.Function stmt) {

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

            // Conditionally add a return stmt
            if (!funcType.hasReturn2) {
                var returnStmt = new Stmt.Return(
                        new Location(0, 0),
                        new Expr.Empty(new Location(0, 0))
                );
                stmt.body.statements.add(returnStmt);
            }

            currentEnvironment = currentEnvironment.getParent();
            functionStack.pop();


        }
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitGroupingExpr(Expr.Grouping expr) {
        return expr.expr.accept(this);
    }

    @Override
    public Optional<ImpType> visitIdentifierExpr(Expr.Identifier expr) {
        var t = currentEnvironment.getVariable(expr.identifier.source());
        if (t != null) {
            expr.realType = t;
        } else {
            Comptime.IdentifierNotFound.submit(file, expr, expr.identifier.source());
        }
        return Optional.ofNullable(t);
    }

    @Override
    public Optional<ImpType> visitIf(Stmt.If stmt) {
        currentEnvironment = stmt.trueBlock.environment;
        stmt.condition.accept(this);
        stmt.trueBlock.accept(this);
        if (stmt.falseStmt != null) stmt.falseStmt.accept(this);

        currentEnvironment = currentEnvironment.getParent();
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitImport(Stmt.Import stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitIndexAccess(Expr.IndexAccess expr) {

        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitLiteralExpr(Expr.Literal expr) {
        var t = expr.realType;
        if (t == null) {
            Comptime.Implementation.submit(file, expr, "This should never happen. All literals should be builtin, for now.");
        }
        return Optional.ofNullable(t);
    }

    @Override
    public Optional<ImpType> visitLiteralList(Expr.LiteralList expr) {

        var firstType = expr.entries.get(0).accept(this);

        firstType.ifPresent(type -> {
            expr.realType = new ListType(type);

            for (int i = 0; i < expr.entries.size(); i++) {
                var t = expr.entries.get(i).accept(this);
                if (t.isPresent()) {
                    if (!(t.get().equals(type))) {
                        Comptime.ListTypeError.submit(file, expr.entries.get(i), t.get().getName(), type.getName());
                        break;
                    }
                }
            }
        });

        return Optional.of(expr.realType);
    }


    @Override
    public Optional<ImpType> visitNew(Expr.New expr) {
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitParameterStmt(Stmt.Parameter stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitPostfixExpr(Expr.Postfix expr) {
        expr.realType = expr.expr.accept(this).get();

        if (!(expr.realType instanceof BuiltInType bt && bt.isNumeric())) {
            Comptime.CannotPostfix.submit(file, expr.expr, expr.operator.source(), expr.realType.getName());
        }
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitPrefix(Expr.Prefix expr) {
        return expr.right.accept(this);
    }

    @Override
    public Optional<ImpType> visitPropertyAccess(Expr.PropertyAccess expr) {
        // For now, assume all expr chains are identifiers
        var exprs = expr.exprs;

        var start = exprs.get(0);
        var startType = start.accept(this);
        ImpType t;
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


                }/* else if (t instanceof BuiltInType builtInType) {
                    System.out.println(builtInType);
                }*/
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
    public Optional<ImpType> visitRange(Expr.Range range) {
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitReturnStmt(Stmt.Return stmt) {
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

        functionStack.peek().hasReturn2 = true;

        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitStruct(Stmt.Struct struct) {
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

        var b = "";
        switch (b) {
            case "" -> System.out.println("a");
            case "a" -> {
                System.out.println("");
                System.out.println("");
            }

        }

        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitType(Stmt.Type stmt) {
        return Optional.empty();
    }


    @Override
    public Optional<ImpType> visitVariable(Stmt.Variable stmt) {

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
