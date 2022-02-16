package org.imp.jvm.visitors;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.imp.jvm.Environment;
import org.imp.jvm.Expr;
import org.imp.jvm.Stmt;
import org.imp.jvm.compiler.DescriptorFactory;
import org.imp.jvm.domain.SourceFile;
import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.errors.Comptime;
import org.imp.jvm.tokenizer.TokenType;
import org.imp.jvm.types.*;
import org.imp.runtime.Batteries;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.GeneratorAdapter;

import java.util.List;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

public class CodegenVisitor implements IVisitor<Optional<ClassWriter>> {
    public final Environment rootEnvironment;
    public final SourceFile file;
    public final ClassWriter cw;
    private final Stack<FuncType> functionStack = new Stack<>();
    public Environment currentEnvironment;

    public CodegenVisitor(Environment rootEnvironment, SourceFile file, ClassWriter cw) {
        this.rootEnvironment = rootEnvironment;
        this.file = file;
        this.currentEnvironment = this.rootEnvironment;
        this.cw = cw;
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
        var funcType = functionStack.peek();
        var ga = funcType.ga;
        var left = expr.left;
        var right = expr.right;
        if (expr.realType.equals(BuiltInType.STRING)) {
            ga.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
            ga.visitInsn(Opcodes.DUP);
            ga.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);

            expr.left.accept(this);

            String leftExprDescriptor = expr.left.realType.getDescriptor();
            String descriptor = "(" + leftExprDescriptor + ")Ljava/lang/StringBuilder;";
            ga.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", descriptor, false);

            expr.right.accept(this);

            String rightExprDescriptor = expr.right.realType.getDescriptor();
            descriptor = "(" + rightExprDescriptor + ")Ljava/lang/StringBuilder;";
            ga.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", descriptor, false);
            ga.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;", false);
        } else if (expr.realType == BuiltInType.BOOLEAN) {
            System.out.println("compile binary comp");
            // Currently, only primitive comparisons are implemented
            expr.left.accept(this);
            expr.right.accept(this);
            // Cast right to left's type
            var lType = Type.getType(expr.left.realType.getDescriptor());
            if (expr.left.realType != expr.right.realType) {
                ga.cast(Type.getType(expr.right.realType.getDescriptor()), lType);
            }

            Label endLabel = new Label();
            Label falseLabel = new Label();
//
            // if false, jump to falseLabel
            ga.ifCmp(lType, GeneratorAdapter.NE, falseLabel);
            // else set true and jump to endLabel
            ga.push(true);
            ga.goTo(endLabel);

            ga.mark(falseLabel);
            ga.push(false);
            ga.mark(endLabel);
        } else {
            ImpType goalType = expr.realType;
            if (left.realType.equals(right.realType)) {
                expr.left.accept(this);
                expr.right.accept(this);
            } else {
                // the types don't match
                if (left.realType == BuiltInType.INT && right.realType == BuiltInType.FLOAT) {
                    expr.left.accept(this);
                    ga.visitInsn(Opcodes.I2F);
                    expr.right.accept(this);
                    goalType = BuiltInType.FLOAT;
                } else if (left.realType == BuiltInType.FLOAT && right.realType == BuiltInType.INT) {
                    expr.left.accept(this);
                    expr.right.accept(this);
                    ga.visitInsn(Opcodes.I2F);
                    goalType = BuiltInType.FLOAT;
                } else if (left.realType == BuiltInType.INT && right.realType == BuiltInType.DOUBLE) {
                    expr.left.accept(this);
                    ga.visitInsn(Opcodes.I2D);
                    expr.right.accept(this);
                    goalType = BuiltInType.DOUBLE;
                } else if (left.realType == BuiltInType.DOUBLE && right.realType == BuiltInType.INT) {
                    expr.left.accept(this);
                    expr.right.accept(this);
                    ga.visitInsn(Opcodes.I2D);
                    goalType = BuiltInType.DOUBLE;
                }

            }
            int op = switch (expr.operator.type()) {
                case ADD -> goalType.getAddOpcode();
                case SUB -> goalType.getSubtractOpcode();
                case MUL -> goalType.getMultiplyOpcode();
                case DIV -> goalType.getDivideOpcode();
                // Todo: Modulus
                case MOD -> 0;
                case LT, GT, LE, GE -> 0;
                default -> throw new IllegalStateException("Unexpected value: " + expr.operator.type());
            };
            ga.visitInsn(op);
        }
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitBlockStmt(Stmt.Block block) {
        for (var stmt : block.statements) {
            stmt.accept(this);
        }
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitCall(Expr.Call expr) {

        var funcType = functionStack.peek();
        expr.item.accept(this);

        if (expr.item.realType instanceof FuncType callType) {
            if (callType.glue) {
                // Todo: make more flexible for multiple stdlib classes
                String owner = Batteries.class.getName().replace('.', '/');
                /*
                 * Before calling the function, we must consider 3 cases:
                 *
                 * 1. The call is to `log(...args)`, we must generate the code to pass varargs to the function
                 * 2. The call is to `log(arg)`, we just have to cast the value to Object.
                 * 3. The call is to any other function, just generate the arguments.
                 */
                String name = callType.name;
                if (callType.isPrefixed) name = "_" + name;

                List<Identifier> params = callType.parameters.stream().map(arg -> new Identifier(arg.type.getName(), arg.type)).collect(Collectors.toList());
                ImpType returnType = callType.returnType;
                String methodDescriptor = DescriptorFactory.getMethodDescriptor(params, returnType);
                // Generate arguments
                for (var arg : expr.arguments) {
                    arg.accept(this);
                    if (arg.realType != null && arg.realType instanceof BuiltInType bt) {
                        bt.doBoxing(funcType.ga);
                    }
                }

                funcType.ga.visitMethodInsn(Opcodes.INVOKESTATIC, owner, name, methodDescriptor, false);
            } else {
                // Generate arguments
                for (var arg : expr.arguments) {
                    arg.accept(this);
                }

                // Call the invoke method
                String methodDescriptor = DescriptorFactory.getMethodDescriptor(callType.parameters, callType.returnType);
                String name = "Function_" + callType.name;
                String owner = FilenameUtils.removeExtension(file.base());

                funcType.ga.visitMethodInsn(Opcodes.INVOKESTATIC, owner, name, methodDescriptor, false);
            }


        } else {
            System.err.println("Bad!");
            System.exit(43);
        }

        return Optional.empty();
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
        return stmt.stmt.accept(this);
    }

    @Override
    public Optional<ClassWriter> visitExpressionStmt(Stmt.ExpressionStmt stmt) {
        stmt.expr.accept(this);
        return Optional.empty();
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
        var funcType = currentEnvironment.getVariableTyped(stmt.name.source(), FuncType.class);
        functionStack.add(funcType);
        var childEnvironment = stmt.body.environment;

        // Generate class
        String name = "Function_" + funcType.name;
        var access = Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC;
        // Generate invoker method
        String descriptor = DescriptorFactory.getMethodDescriptor(funcType.parameters, funcType.returnType);

        if (funcType.name.equals("main")) {
            name = "main";
            descriptor = "([Ljava/lang/String;)V";
        }

        var mv = cw.visitMethod(access, name, descriptor, null, null);
        funcType.ga = new GeneratorAdapter(mv, access, name, descriptor);

        for (int i = 0; i < funcType.parameters.size(); i++) {
            var param = funcType.parameters.get(i);
            funcType.localMap.put(param.name, i);
        }

        // Generate function body
        currentEnvironment = childEnvironment;
        stmt.body.accept(this);

        // Finish generating invoker function

//        funcType.ga.visitMaxs(-1, -1);
        funcType.ga.endMethod();

//        code.put(qualifiedName, cw.toByteArray());

        // All methods must return something, even voids
        if (funcType.name.equals("main")) {
            funcType.ga.visitInsn(Opcodes.RETURN);
        }

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
        var type = currentEnvironment.getVariable(expr.identifier.source());
        expr.realType = type;
        if (type instanceof FuncType ft) {

        } else {
            // Todo: TERRIBLE hack that sparsely does locals on even indices only
            // See https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-2.html#jvms-2.6.1
            var index = funcType.localMap.get(expr.identifier.source());
//            funcType.ga.visitVarInsn(type.getLoadVariableOpcode(), index);
            funcType.ga.loadLocal(index, Type.getType(type.getDescriptor()));
        }
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
        if (expr.realType instanceof BuiltInType bt) {
            var transformed = TypeResolver.getValueFromString(expr.literal.source(), BuiltInType.getFromToken(expr.literal.type()));
            var ga = functionStack.peek().ga;
            switch (bt) {
                case INT -> ga.push((int) transformed);
                case FLOAT -> ga.push((float) transformed);
                case DOUBLE -> ga.push((double) transformed);
                case BOOLEAN -> ga.push((boolean) transformed);
                case STRING -> ga.push((String) transformed);
            }
        } else {
            Comptime.Implementation.submit(file.file, expr, "This should never happen. All literals should be builtin, for now.");
        }
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
        var ga = functionStack.peek().ga;

        expr.right.accept(this);

        var t = expr.right.realType;
        if (expr.operator.type() == TokenType.SUB) {
            ga.visitInsn(t.getNegOpcode());
            expr.realType = t;
        }
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitPropertyAccess(Expr.PropertyAccess expr) {

//        throw new NotImplementedException("method not implemented");
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitRange(Expr.Range range) {

        throw new NotImplementedException("method not implemented");
    }

    @Override
    public Optional<ClassWriter> visitReturnStmt(Stmt.Return stmt) {
        var funcType = functionStack.peek();
        stmt.expr.accept(this);
        funcType.ga.visitInsn(stmt.expr.realType.getReturnOpcode());
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitStruct(Stmt.Struct struct) {
        // Codegen a new JVM class representing an Imp struct
        var structType = currentEnvironment.getVariableTyped(struct.name.source(), StructType.class);

        String name = structType.name;
        String qualifiedName = file.path() + "/" + name;
//        cw.visit(CLASS_VERSION, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, qualifiedName, null, "java/lang/Object", null);

//        addConstructor(structType.parent, classWriter, structType.fields, structType);

        for (int i = 0; i < structType.fieldNames.length; i++) {
            ImpType type = structType.fieldTypes[i];
            String descriptor = type.getDescriptor();
            String n = structType.fieldNames[i];

            FieldVisitor fieldVisitor = cw.visitField(Opcodes.ACC_PUBLIC, n, descriptor, null, null);
            fieldVisitor.visitEnd();
        }

        cw.visitEnd();

        // Todo: generate internal classes
//        code.put(qualifiedName, cw.toByteArray());

        return Optional.of(cw);
    }

    @Override
    public Optional<ClassWriter> visitType(Stmt.Type stmt) {
        throw new NotImplementedException("method not implemented");
    }

    @Override
    public Optional<ClassWriter> visitTypeAlias(Stmt.TypeAlias stmt) {
//        throw new NotImplementedException("method not implemented");
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitVariable(Stmt.Variable stmt) {
        // The fact that each variable in the block (environment) is known at this point
        // means it's possible to do compiler optimizations like moving all declarations
        // to one initialization at the beginning of the block or function.
        var funcType = functionStack.peek();

        stmt.expr.accept(this);

        // Add another local to the function

//        stmt.localIndex = funcType.ga.newLocal(Type.getType(stmt.expr.realType.getDescriptor()));
        stmt.localIndex = funcType.ga.newLocal(Type.INT_TYPE);
        System.out.println("store: " + stmt.identifier() + "@" + stmt.localIndex);

        funcType.localMap.put(stmt.identifier(), stmt.localIndex);

        var type = currentEnvironment.getVariable(stmt.identifier());
        // Todo: indexes need to be better
//        funcType.ga.visitVarInsn(type.getStoreVariableOpcode(), stmt.localIndex);
        funcType.ga.storeLocal(stmt.localIndex, Type.getType(type.getDescriptor()));

        return Optional.empty();
    }


}
