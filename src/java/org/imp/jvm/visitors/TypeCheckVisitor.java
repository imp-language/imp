package org.imp.jvm.visitors;

import org.imp.jvm.Util;
import org.imp.jvm.domain.Environment;
import org.imp.jvm.domain.SourceFile;
import org.imp.jvm.errors.Comptime;
import org.imp.jvm.parser.Expr;
import org.imp.jvm.parser.Stmt;
import org.imp.jvm.parser.tokenizer.Location;
import org.imp.jvm.parser.tokenizer.TokenType;
import org.imp.jvm.tool.Compiler;
import org.imp.jvm.types.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

public class TypeCheckVisitor implements IVisitor<Optional<ImpType>> {

    public final Environment rootEnvironment;
    public final File file;
    public final Compiler compiler;
    private final Stack<FuncType> functionStack = new Stack<>();
    public Environment currentEnvironment;

    public TypeCheckVisitor(Compiler compiler, Environment rootEnvironment, SourceFile source) {
        this.rootEnvironment = rootEnvironment;
        this.file = source.file;
        this.currentEnvironment = this.rootEnvironment;
        this.compiler = compiler;
    }


    @Override
    public Optional<ImpType> visit(Stmt stmt) {
        return stmt.accept(this);
    }

    @Override
    public Optional<ImpType> visitAlias(Stmt.Alias stmt) {
        var a = currentEnvironment.getVariable(stmt.identifier.source());

        var resolvedTypes = new HashSet<ImpType>();
        // Todo: do we need to disallow `string | string` cause that would really just be `string`?
        if (a instanceof UnionType ut) {
            for (var type : ut.types) {
                if (type instanceof UnknownType ukt) {
                    var attempt = currentEnvironment.getVariable(ukt.typeName);
                    if (attempt != null) {
                        resolvedTypes.add(attempt);
                    }
                } else {
                    resolvedTypes.add(type);
                }
            }
            ut.types = resolvedTypes;

            currentEnvironment.setVariableType(stmt.identifier(), ut);
        }

        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitAssignExpr(Expr.Assign expr) {
        expr.left.accept(this);
        expr.right.accept(this);

        switch (expr.left) {
            case Expr.Identifier id -> {
                if (expr.left.realType != expr.right.realType) {
                    Comptime.BadAssignment.submit(compiler, file, expr, id.identifier.source(), expr.left.realType.getName(), expr.right.realType.getName());
                }
            }
            case Expr.PropertyAccess pa -> {
                var lType = expr.left.realType;
                var rType = expr.right.realType;
                if (TypeResolver.typesMatch(lType, rType)) {
                    System.out.println("prop type match ok");
                } else {
                    Comptime.ParameterTypeMismatch.submit(compiler, file, expr.left, rType.getName(), lType.getName());
                }

            }
            default -> throw new IllegalStateException("Unexpected value: " + expr.left.realType);
        }

//        if (expr.left.realType != expr.right.realType) {
//            if (expr.left instanceof Expr.Identifier id) {
//                Comptime.BadAssignment.submit(compiler, file, expr, id.identifier.source(), expr.left.realType.getName(), expr.right.realType.getName());
//            } else {
//                Comptime.Implementation.submit(compiler, file, expr, "bad property access assignment, error todo");
//            }
//        } else {
//            if (expr.left instanceof Expr.PropertyAccess pa) {
//                var lType = expr.left.realType;
//                var rType = expr.right.realType;
//                if (TypeResolver.typesMatch(lType, rType)) {
//                    System.out.println("prop type match ok");
//                } else {
//                    Util.exit("prop type match bad", 79);
//                }
//
//            } else {
//                Util.exit("what", 428);
//            }
//        }

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
            Comptime.Implementation.submit(compiler, file, expr, "Types in binary expression not determined.");
            return Optional.empty();
        }

        if (t1.get() == BuiltInType.ANY || t2.get() == BuiltInType.ANY) {
            Comptime.CannotApplyOperator.submit(compiler, file, expr, expr.operator.source(), t1.get(), t2.get());
            return Optional.empty();
        }

        var totalType = t1.get();

        switch (expr.operator.type()) {
            case ADD, SUB, MUL, DIV, MOD -> {
                if (t1.get() instanceof BuiltInType bt1 && t2.get() instanceof BuiltInType bt2) {
                    totalType = BuiltInType.widen(bt1, bt2);
                } else {
                    Comptime.CannotApplyOperator.submit(compiler, file, expr, expr.operator.source(), t1.get(), t2.get());
                }
            }
            case EQUAL, NOTEQUAL, LT, GT, LE, GE -> {
                if (t1.get() instanceof BuiltInType bt1 && t2.get() instanceof BuiltInType bt2) {
                    // check if two values can be compared
                    if (expr.operator.type() != TokenType.EQUAL && expr.operator.type() != TokenType.NOTEQUAL) {
                        if (bt1 == BuiltInType.STRING || bt2 == BuiltInType.STRING) {
                            Comptime.CannotApplyOperator.submit(compiler, file, expr, expr.operator.source(), bt1.getName(), bt2.getName());
                        } else if (bt1 == BuiltInType.BOOLEAN || bt2 == BuiltInType.BOOLEAN) {
                            Comptime.CannotApplyOperator.submit(compiler, file, expr, expr.operator.source(), bt1.getName(), bt2.getName());

                        }
                    }
                    totalType = BuiltInType.BOOLEAN;
                }
            }
            case AND, OR -> {
                if (t1.get() instanceof BuiltInType bt1 && t2.get() instanceof BuiltInType bt2) {
                    // check if two values can be compared
                    if (bt1 != BuiltInType.BOOLEAN || bt2 != BuiltInType.BOOLEAN) {
                        Comptime.CannotApplyOperator.submit(compiler, file, expr, expr.operator.source(), bt1.getName(), bt2.getName());
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
            if (t instanceof StructType ttt) {
                if (ttt.parameters.size() != expr.arguments.size()) {
                    var params = ttt.parameters.stream().map(s -> s.type.getName()).collect(Collectors.joining(", "));
                    Comptime.FunctionSignatureMismatch.submit(compiler, file, expr.item, ttt.name, params);
                    return Optional.empty();
                }

                // Make sure parameter and argument types match.
                Util.zip(ttt.parameters, expr.arguments, (param, arg) -> {
                    var at = arg.accept(this);
                    if (at.isPresent()) {
                        var argType = at.get();
                        if (argType == BuiltInType.VOID) {
                            Comptime.VoidUsage.submit(compiler, file, arg);
                        }
                        if (!TypeResolver.typesMatch(param.type, argType)) {
                            Comptime.ParameterTypeMismatch.submit(compiler, file, arg, argType.getName(), param.type.getName());
                        } else {
                            arg.realType = argType;
                        }

                    }
                });
                if (t instanceof FuncType ftt) {
                    ImpType returnType = ftt.returnType;
                    expr.realType = returnType;
                    return Optional.of(returnType);
                } else {
                    expr.realType = ttt;
                    return Optional.of(ttt);
                }
            } else {
                throw new IllegalStateException("Unexpected value: " + t);
            }

        } else {
            // Todo: pass missing method name
            Comptime.MethodNotFound.submit(compiler, file, expr.item, "name");
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
                case ListType lt -> Util.exit("Todo: iterate over lists", 97);
                default -> Comptime.NotIterable.submit(compiler, file, stmt.expr, b.get().getName());
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
                    var attempt = currentEnvironment.getVariable(ut.typeName);
                    if (attempt != null) {
                        param.type = attempt;
                        childEnvironment.setVariableType(param.name, attempt);

                    } else {
                        Comptime.TypeNotFound.submit(compiler, file, stmt, ut.typeName);
                    }
                }
            }

            if (funcType.returnType instanceof UnknownType ut) {
                var attempt = currentEnvironment.getVariable(ut.typeName);
                if (attempt != null) {
                    funcType.returnType = attempt;
                } else {
                    Comptime.TypeNotFound.submit(compiler, file, stmt, ut.typeName);
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
            Comptime.IdentifierNotFound.submit(compiler, file, expr, expr.identifier.source());
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
            Comptime.Implementation.submit(compiler, file, expr, "This should never happen. All literals should be builtin, for now.");
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
                        Comptime.ListTypeError.submit(compiler, file, expr.entries.get(i), t.get().getName(), type.getName());
                        break;
                    }
                }
            }
        });

        return Optional.of(expr.realType);
    }

    @Override
    public Optional<ImpType> visitMatch(Stmt.Match match) {
        /*
         * Steps to typecheck a match statement:
         * - get real type of the parameter, if it's not a UnionType error?
         * - get the real type of each case, making sure that there are no duplicate
         * - if the union type isn't fully covered, error
         *
         */

        match.expr.accept(this);

        if (match.expr.realType instanceof UnionType ut) {
            var typesToCover = new HashSet<>(ut.types);
            match.cases.forEach((keyTypeStmt, block) -> {
                // Todo: Ensure the resolved ImpType is not an UnknownType
                var caseType = match.types.get(keyTypeStmt);
                typesToCover.remove(caseType);

                var childEnvironment = block.environment;
                childEnvironment.setParent(currentEnvironment);
                childEnvironment.setVariableType(match.identifier.identifier.source(), caseType);

                currentEnvironment = childEnvironment;
                block.accept(this);
                currentEnvironment = currentEnvironment.getParent();
            });
            if (!typesToCover.isEmpty()) {
                Comptime.MatchCoverage.submit(compiler, file, match, ut, Util.joinConjunction(typesToCover));
            }


        } else {
            Comptime.TypeNotResolved.submit(compiler, file, match.expr, "print");
//            Util.exit("error", 97);
        }

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
            Comptime.CannotPostfix.submit(compiler, file, expr.expr, expr.operator.source(), expr.realType.getName());
        }
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitPrefix(Expr.Prefix expr) {
        return expr.right.accept(this);
    }

    @Override
    public Optional<ImpType> visitPropertyAccess(Expr.PropertyAccess expr) {
        /*
         * The last identifier in the chain determines the realType of
         * this PropertyAccess expression. We do not need to store the
         * intermediate types that are determined during this process.
         */
        var t = expr.expr.accept(this);

        var typeChain = new ArrayList<ImpType>();

        if (t.isPresent()) {
            var exprType = t.get();
            if (exprType instanceof StructType st) {
                StructType pointer = st;
                typeChain.add(pointer);

                ImpType result = st;

                for (Expr.Identifier identifier : expr.identifiers) {
                    var foundField = pointer.parameters.stream().filter(i -> i.name.equals(identifier.identifier.source())).findAny();
                    if (foundField.isPresent()) {
                        var pointerCandidate = foundField.get().type;
                        if (pointerCandidate instanceof StructType pst) {
                            pointer = pst;
                            typeChain.add(pointer);
                            result = pointerCandidate;
                        } else {
                            result = pointerCandidate;
                            typeChain.add(pointerCandidate);

                        }

                    } else {
                        Comptime.FieldNotPresent.submit(compiler, file, identifier, identifier.identifier.source(), pointer.name);
                        break;
                    }
                }
                System.out.println(result);
                expr.realType = result;
            }
        } else {
            Comptime.MethodNotFound.submit(compiler, file, expr.expr, "ree");
        }
        expr.typeChain = typeChain;

        return Optional.ofNullable(expr.realType);
    }


    @Override
    public Optional<ImpType> visitReturnStmt(Stmt.Return stmt) {
        // Set the return type of the function to the type of the
        // expression you are returning.
        var t = stmt.expr.accept(this);

        if (t.isPresent()) {
            if (functionStack.size() > 0) {
                var newType = t.get();
                var functionType = functionStack.peek();
                if (functionType.returnType instanceof UnionType ut) {
                    if (!ut.types.contains(newType)) {
                        Comptime.ParameterTypeMismatch.submit(compiler, file, stmt.expr, newType, functionType.returnType);
                    }
                } else if (functionType.returnType == BuiltInType.VOID) {
                    functionType.returnType = t.get();
                } else if (newType != functionType.returnType) {
                    Comptime.ReturnTypeMismatch.submit(compiler, file, stmt, functionType.name, functionType.returnType, newType);
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
            Util.zip(struct.fields, structType.parameters, (a, b) -> {
                if (b.type instanceof UnknownType ut) {
                    var attempt = currentEnvironment.getVariable(ut.typeName);
                    if (attempt != null) {
                        b.type = attempt;
                    } else {
                        Comptime.TypeNotFound.submit(compiler, file, a, ut.typeName);
                    }
                }
            });

        }

        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitType(Stmt.TypeStmt stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitUnionType(Stmt.UnionTypeStmt unionTypeStmt) {
        return Optional.empty();
    }

    @Override
    public Optional<ImpType> visitVariable(Stmt.Variable stmt) {

        var expr = stmt.expr;
        var t = expr.accept(this);

        if (t.isPresent()) {
            currentEnvironment.setVariableType(stmt.name.source(), t.get());
        } else {
            Comptime.TypeNotResolved.submit(compiler, file, expr, stmt.name.source());
        }
        return Optional.empty();
    }


}
