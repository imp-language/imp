package org.imp.jvm.visitors;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.imp.jvm.Environment;
import org.imp.jvm.SourceFile;
import org.imp.jvm.Util;
import org.imp.jvm.codegen.DescriptorFactory;
import org.imp.jvm.domain.Identifier;
import org.imp.jvm.errors.Comptime;
import org.imp.jvm.parser.Expr;
import org.imp.jvm.parser.Stmt;
import org.imp.jvm.parser.tokenizer.TokenType;
import org.imp.jvm.types.*;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class CodegenVisitor implements IVisitor<Optional<ClassWriter>> {
    public final static int flags = ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS;
    public final static int CLASS_VERSION = 61;
    public final Environment rootEnvironment;
    public final SourceFile source;
    public final File file;
    public final ClassWriter cw;
    private final Stack<FuncType> functionStack = new Stack<>();
    public Map<StructType, ClassWriter> structWriters = new HashMap<>();
    public Environment currentEnvironment;

    public CodegenVisitor(Environment rootEnvironment, SourceFile source, ClassWriter cw) {
        this.rootEnvironment = rootEnvironment;
        this.source = source;
        this.currentEnvironment = this.rootEnvironment;
        this.cw = cw;
        this.file = source.file;
    }

    @Override
    public Optional<ClassWriter> visit(Stmt stmt) {
        return stmt.accept(this);
    }

    @Override
    public Optional<ClassWriter> visitAssignExpr(Expr.Assign expr) {
        // Mutability has already been checked at this point
        var funcType = functionStack.peek();
        expr.right.accept(this);

        if (expr.left instanceof Expr.Identifier id) {
            var index = funcType.localMap.get(id.identifier.source());
            funcType.ga.storeLocal(index);

        } else {
            Comptime.Implementation.submit(file, expr, "Assignment not implemented for any recipient but identifier yet");

        }

        return Optional.empty();
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
            BinaryExprVisitor.concatenateStrings(ga, expr, this);
        } else if (expr.realType == BuiltInType.BOOLEAN) {
            if (expr.operator.type() == TokenType.AND) {
                BinaryExprVisitor.logicalAnd(ga, expr, this);
            } else if (expr.operator.type() == TokenType.OR) {
                BinaryExprVisitor.logicalOr(ga, expr, this);
            } else {
                BinaryExprVisitor.relational(ga, expr, this);
            }
        } else {
            BinaryExprVisitor.arithmetic(ga, expr, this);

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
//        expr.item.accept(this); (don't do this)

        if (expr.item instanceof Expr.Identifier identifier) {
            var type = currentEnvironment.getVariable(identifier.identifier.source());
            expr.item.realType = type;
        }

        if (expr.item.realType instanceof FuncType callType) {
            if (callType.glue) {
                // Todo: make more flexible for multiple stdlib classes
                String owner = callType.owner;
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

                Util.zip(params, expr.arguments, (param, arg) -> {
                    arg.accept(this);
                    // Only box if the arg type is a Java primitive and the param type is Object
                    if (arg.realType != null && arg.realType instanceof BuiltInType btArg) {
                        if (param.type instanceof ExternalType et && et.foundClass().equals(Object.class)) {
                            btArg.doBoxing(funcType.ga);
                        }
                    }
                });

                funcType.ga.visitMethodInsn(Opcodes.INVOKESTATIC, owner, name, methodDescriptor, false);
            } else {
                // Generate arguments
                for (var arg : expr.arguments) {
                    arg.accept(this);
                }

                // Call the invoke method
                String methodDescriptor = DescriptorFactory.getMethodDescriptor(callType.parameters, callType.returnType);
                String name = "Function_" + callType.name;
                String owner = FilenameUtils.removeExtension(source.getFullRelativePath());

                funcType.ga.visitMethodInsn(Opcodes.INVOKESTATIC, owner, name, methodDescriptor, false);
            }

        } else if (expr.item.realType instanceof StructType st) {
            // initialize instance of struct class and push to stack
            System.out.println("Generating instance of struct " + st.name);
            String ownerDescriptor = st.getInternalName();
            var ga = funcType.ga;

            ga.visitTypeInsn(Opcodes.NEW, st.qualifiedName); //NEW instruction takes object descriptor as an input
            ga.visitInsn(Opcodes.DUP); //Duplicate (we do not want invokespecial to "eat" our new object

            String descriptor = "L" + st.parentName + ";";
            ga.visitFieldInsn(Opcodes.GETSTATIC, st.parentName, "instance", descriptor);
            ga.visitInsn(Opcodes.DUP);

            ga.visitMethodInsn(Opcodes.INVOKESTATIC, "java/util/Objects", "requireNonNull", "(Ljava/lang/Object;)Ljava/lang/Object;", false);
            ga.pop();

            String typeDescriptor = "";
            // Generate arguments
            for (var arg : expr.arguments) {
                arg.accept(this);
                typeDescriptor += arg.realType.getDescriptor();
            }

            funcType.ga.visitMethodInsn(
                    Opcodes.INVOKESPECIAL,
                    st.qualifiedName,
                    "<init>",
                    "(" + descriptor + typeDescriptor + ")V",
                    false
            );

//            funcType.ga.invokeConstructor(Type.getType("lib/date$Date"), new Method("<init>", "()V"));

//            ga.pop();

        } else {
            System.err.println("Bad!");
            System.exit(43);
        }

        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitEmptyList(Expr.EmptyList emptyList) {
        var ga = functionStack.peek().ga;

        ga.newInstance(Type.getType("Ljava/util/ArrayList;"));
        ga.dup();

        ga.invokeConstructor(Type.getType("Ljava/util/ArrayList;"), new Method("<init>", "()V"));
        return Optional.empty();
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
            funcType.argMap.put(param.name, i);
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
        expr.expr.accept(this);
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitIdentifierExpr(Expr.Identifier expr) {

        var funcType = functionStack.peek();
        var type = currentEnvironment.getVariable(expr.identifier.source());
        expr.realType = type;
        if (type instanceof FuncType ft) {

        } else if (type instanceof StructType st) {
            int index;
            String source = expr.identifier.source();
            if (funcType.localMap.containsKey(source)) {
                index = funcType.localMap.get(source);
                funcType.ga.loadLocal(index, Type.getType(type.getDescriptor()));
            } else {
                index = funcType.argMap.get(source);
                funcType.ga.loadArg(index);
            }
        } else {
            String source = expr.identifier.source();
            int index;
            if (funcType.localMap.containsKey(source)) {
                index = funcType.localMap.get(source);
                funcType.ga.loadLocal(index, Type.getType(type.getDescriptor()));
            } else {
                index = funcType.argMap.get(source);
                funcType.ga.loadArg(index);
            }
//            funcType.ga.visitVarInsn(type.getLoadVariableOpcode(), index);
        }
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitIf(Stmt.If stmt) {
        var funcType = functionStack.peek();
        var ga = funcType.ga;

        // Generate condition (returns true or false)
        stmt.condition.accept(this);

        currentEnvironment = stmt.trueBlock.environment;

        Label endLabel = new Label();
        Label falseLabel = new Label();
        // if condition is true (cond == 0) do trueLabel then jump to end
        ga.ifZCmp(GeneratorAdapter.EQ, falseLabel);
        stmt.trueBlock.accept(this);
        ga.goTo(endLabel);
        ga.mark(falseLabel);
        if (stmt.falseStmt != null) stmt.falseStmt.accept(this);
        ga.mark(endLabel);

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
            Comptime.Implementation.submit(source.file, expr, "This should never happen. All literals should be builtin, for now.");
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
        var innerCw = new ClassWriter(CodegenVisitor.flags);
        var structType = currentEnvironment.getVariableTyped(struct.name.source(), StructType.class);

        String name = structType.name;
        String innerName = source.getFullRelativePath() + "$" + name;

        // Create the inner class
        innerCw.visit(CodegenVisitor.CLASS_VERSION, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, innerName, null, "java/lang/Object", null);
        // Link inner class to outer class
        cw.visitInnerClass(innerName, source.getFullRelativePath(), name, Opcodes.ACC_PUBLIC);
        // Link outer class to inner class
        innerCw.visitOuterClass(source.getFullRelativePath(), name, "()V");

        // Add field reference to outer class
        String descriptor = "L" + source.getFullRelativePath() + ";";
        System.out.println(descriptor);
        FieldVisitor fieldVisitor = innerCw.visitField(Opcodes.ACC_FINAL + Opcodes.ACC_SYNTHETIC, "this$0", descriptor, null, null);
        fieldVisitor.visitEnd();

        String constructorDescriptor = "";

        // Add fields
        for (int i = 0; i < structType.fieldNames.length; i++) {
            ImpType type = structType.fieldTypes[i];
            descriptor = type.getDescriptor();
            String n = structType.fieldNames[i];

            fieldVisitor = innerCw.visitField(Opcodes.ACC_PUBLIC, n, descriptor, null, null);
            fieldVisitor.visitEnd();

            constructorDescriptor += descriptor;
        }

        // Generate inner class Struct constructor
        descriptor = "(" + "L" + source.getFullRelativePath() + ";" + constructorDescriptor + ")V";
        MethodVisitor mv = innerCw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", descriptor, null, null);
        mv.visitCode();

        // Load outer class
        descriptor = "L" + source.getFullRelativePath() + ";";
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitVarInsn(Opcodes.ALOAD, 1);
        String ownerInternalName = source.getFullRelativePath() + "$" + name;
        mv.visitFieldInsn(Opcodes.PUTFIELD, ownerInternalName, "this$0", descriptor);

        // Call super()
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        String ownerDescriptor = "java/lang/Object";
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, ownerDescriptor, "<init>", "()V", false);
        // Todo: set fields

        // Set fields
        for (int i = 0; i < structType.fieldNames.length; i++) {
            ImpType type = structType.fieldTypes[i];
            descriptor = type.getDescriptor();
            String n = structType.fieldNames[i];
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitVarInsn(type.getLoadVariableOpcode(), i + 2);
            mv.visitFieldInsn(Opcodes.PUTFIELD, ownerInternalName, n, descriptor);
        }
        // Append return
        mv.visitInsn(Opcodes.RETURN);

        mv.visitEnd();
        mv.visitMaxs(-1, -1);

        // Todo: generate internal classes
//        code.put(qualifiedName, cw.toByteArray());
        structWriters.put(structType, innerCw);

        return Optional.of(innerCw);
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
        var type = currentEnvironment.getVariable(stmt.identifier());
        stmt.localIndex = funcType.ga.newLocal(Type.getType(type.getDescriptor()));
        funcType.localMap.put(stmt.identifier(), stmt.localIndex);
        funcType.ga.storeLocal(stmt.localIndex, Type.getType(type.getDescriptor()));

        return Optional.empty();
    }


}
