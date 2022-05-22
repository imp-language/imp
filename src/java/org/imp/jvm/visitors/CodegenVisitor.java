package org.imp.jvm.visitors;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.imp.jvm.BytecodeGenerator;
import org.imp.jvm.Constants;
import org.imp.jvm.Util;
import org.imp.jvm.domain.Environment;
import org.imp.jvm.domain.Identifier;
import org.imp.jvm.domain.SourceFile;
import org.imp.jvm.errors.Comptime;
import org.imp.jvm.parser.Expr;
import org.imp.jvm.parser.Stmt;
import org.imp.jvm.parser.tokenizer.TokenType;
import org.imp.jvm.tool.Compiler;
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
    public final Compiler compiler;
    public final ClassWriter cw;
    public final Map<StructType, ClassWriter> structWriters = new HashMap<>();
    private final Stack<FuncType> functionStack = new Stack<>();

    public Environment currentEnvironment;

    public CodegenVisitor(Compiler compiler, Environment rootEnvironment, SourceFile source, ClassWriter cw) {
        this.compiler = compiler;
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
    public Optional<ClassWriter> visitAlias(Stmt.Alias stmt) {
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitAssignExpr(Expr.Assign expr) {
        // Mutability has already been checked at this point
        var funcType = functionStack.peek();
        var ga = funcType.ga;

        if (expr.left instanceof Expr.Identifier id) {
            expr.right.accept(this);
            var index = funcType.localMap.get(id.identifier.source());
            ga.storeLocal(index);
        } else if (expr.left instanceof Expr.PropertyAccess pa) {
            var lType = expr.left.realType;
            var rType = expr.right.realType;

            // Load top of access chain
            var root = pa.expr;
            root.accept(this);

            // Get fields
            var typeChain = pa.typeChain;
            var identifiers = pa.identifiers;
            for (int i = 0; i < typeChain.size() - 2; i++) {
                var current = typeChain.get(i);
                var next = typeChain.get(i + 1);
                var fieldName = identifiers.get(i).identifier.source();
                ga.getField(Type.getType(current.getDescriptor()), fieldName, Type.getType(next.getDescriptor()));
            }

            // Generate expr
            expr.right.accept(this);

            // Only box if the arg type is a Java primitive
            if (rType instanceof BuiltInType btArg) {
                if (lType instanceof UnionType) {
                    btArg.doBoxing(ga);
                }
            }

            // Put expr into field
            ga.putField(Type.getType(typeChain.get(typeChain.size() - 2).getDescriptor()),
                    identifiers.get(identifiers.size() - 1).identifier.source(),
                    Type.getType(typeChain.get(typeChain.size() - 1).getDescriptor()));

        } else {
            Comptime.Implementation.submit(compiler, file, expr, "Assignment not implemented for any recipient but identifier yet");
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
        for (var stmt : block.statements) stmt.accept(this);
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitCall(Expr.Call expr) {

        var funcType = functionStack.peek();
//        expr.item.accept(this); (don't do this)

        if (expr.item instanceof Expr.Identifier identifier) {
            expr.item.realType = currentEnvironment.getVariable(identifier.identifier.source());
        }

        if (expr.item.realType instanceof FuncType callType) {
            if (callType.glue) {
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
                String methodDescriptor = Util.getMethodDescriptor(params, returnType);
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
                var ga = funcType.ga;
                Util.zip(callType.parameters, expr.arguments, (param, arg) -> {
                    arg.accept(this);
                    // Only box if the arg type is a Java primitive
                    if (arg.realType != null && arg.realType instanceof BuiltInType btArg) {
                        switch (param.type) {
                            case ExternalType et && et.foundClass().equals(Object.class) -> btArg.doBoxing(ga);
                            case UnionType ut -> btArg.doBoxing(funcType.ga);
                            default -> {
                            }
                        }
                    }
                });

                // Call the invoke method
                String methodDescriptor = Util.getMethodDescriptor(callType.parameters, callType.returnType);
                String name = "Function_" + callType.name;
                String owner = FilenameUtils.removeExtension(source.getFullRelativePath());

                funcType.ga.visitMethodInsn(Opcodes.INVOKESTATIC, owner, name, methodDescriptor, false);
            }

        } else if (expr.item.realType instanceof StructType st) {
            var ga = funcType.ga;

            ga.newInstance(Type.getType("L" + st.qualifiedName + ";"));
            ga.visitInsn(Opcodes.DUP);

            StringBuilder typeDescriptor = new StringBuilder();
            Util.zip(st.parameters, expr.arguments, (param, arg) -> {
                arg.accept(this);
                if (param.type instanceof UnionType ut) {
                    typeDescriptor.append("Ljava/lang/Object;");
                    // Only box if the arg type is a Java primitive and the param type is Object
                    if (arg.realType instanceof BuiltInType btArg) {
                        btArg.doBoxing(ga);
                    }

                } else {
                    typeDescriptor.append(arg.realType.getDescriptor());
                }

            });

            ga.invokeConstructor(Type.getType("L" + st.qualifiedName + ";"), new Method(Constants.Init, "(" + typeDescriptor + ")V"));


        } else {
            System.err.println("Bad!");
            System.exit(43);
        }

        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitEmpty(Expr.Empty empty) {
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitEmptyList(Expr.EmptyList emptyList) {
        var ga = functionStack.peek().ga;

        ga.newInstance(Constants.ArrayListType);
        ga.dup();

        ga.invokeConstructor(Constants.ArrayListType, new Method(Constants.Init, "()V"));
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
        // for loops either have a stmt.expr of type Identifier or type Call
        // Eventually we might have type LiteralList but for now this suffices.
        // Loading and storing of stmt.expr is handled below.
        var funcType = functionStack.peek();
        var ga = functionStack.peek().ga;
        currentEnvironment = stmt.block.environment;
        var startLabel = new Label();
        var endLabel = new Label();

        // ALOAD the iterator
        if (stmt.expr instanceof Expr.Identifier id) {
            ga.mark(startLabel);
            stmt.expr.accept(this);

        } else {
            stmt.expr.accept(this);
            stmt.localExprIndex = ga.newLocal(Constants.IteratorType);
            funcType.localMap.put("______", stmt.localExprIndex);
            ga.storeLocal(stmt.localExprIndex, Constants.IteratorType);
            ga.mark(startLabel);
            ga.loadLocal(stmt.localExprIndex);
        }

        // Check if iterator has next
        ga.invokeInterface(Constants.IteratorType, new Method("hasNext", "()Z"));
        ga.visitJumpInsn(Opcodes.IFEQ, endLabel);

        // ALOAD the iterator for body
        if (stmt.expr instanceof Expr.Identifier id) {
            stmt.expr.accept(this);
        } else {
            ga.loadLocal(stmt.localExprIndex);
        }
        ga.invokeInterface(Constants.IteratorType, new Method("next", "()Ljava/lang/Object;"));
        BuiltInType.INT.doUnboxing(ga);
        stmt.localNameIndex = ga.newLocal(Type.getType(BuiltInType.INT.getDescriptor()));
        funcType.localMap.put(stmt.name.source(), stmt.localNameIndex);
        funcType.ga.storeLocal(stmt.localNameIndex, Type.getType(BuiltInType.INT.getDescriptor()));

        // Visit body
        stmt.block.accept(this);

        // Jump back to beginning and mark end label
        ga.goTo(startLabel);
        ga.mark(endLabel);
        // Reset scope
        currentEnvironment = currentEnvironment.getParent();
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitFunctionStmt(Stmt.Function stmt) {
        var funcType = currentEnvironment.getVariableTyped(stmt.name.source(), FuncType.class);
        functionStack.add(funcType);
        var childEnvironment = stmt.body.environment;

        // Generate function signature
        String name = "Function_" + funcType.name;
        var access = Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC;
        String descriptor = Util.getMethodDescriptor(funcType.parameters, funcType.returnType);
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
        funcType.ga.endMethod();
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
        var ga = funcType.ga;
        var type = currentEnvironment.getVariable(expr.identifier.source());
        expr.realType = type;

        int index;
        String source = expr.identifier.source();
        if (funcType.localMap.containsKey(source)) {
            index = funcType.localMap.get(source);
            ga.loadLocal(index, Type.getType(type.getDescriptor()));
        } else {
            index = funcType.argMap.get(source);
            ga.loadArg(index);
        }

        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitIf(Stmt.If stmt) {
        var funcType = functionStack.peek();
        var ga = funcType.ga;
        Label endLabel = new Label();
        Label falseLabel = new Label();

        // Generate condition (returns true or false)
        stmt.condition.accept(this);

        // if condition is true (cond == 0) do trueLabel then jump to end
        currentEnvironment = stmt.trueBlock.environment;
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
            Comptime.Implementation.submit(compiler, source.file, expr, "This should never happen. All literals should be builtin, for now.");
        }
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitLiteralList(Expr.LiteralList expr) {
        var ga = functionStack.peek().ga;

        ga.newInstance(Constants.ListWrapperType);
        ga.dup();
        ga.push(((ListType) expr.realType).contentType().getName());
        ga.invokeConstructor(Constants.ListWrapperType, new Method(Constants.Init, "(Ljava/lang/String;)V"));
        ga.dup();
        ga.invokeVirtual(Constants.ListWrapperType, new Method("list", "()Ljava/util/ArrayList;"));

        for (var entry : expr.entries) {
            // We never want to store the initialized list in this visitor location
            // Instead we dup the reference every time. Inefficient but who's going
            // to write big lists by hand.
            ga.dup();

            entry.accept(this);
            var rt = entry.realType;
            if (rt instanceof BuiltInType bt) {
                bt.doBoxing(ga);
            }
            ga.invokeVirtual(Constants.ArrayListType, new Method("add", "(Ljava/lang/Object;)Z"));
            ga.pop();
        }
        ga.pop();
        return Optional.empty();
    }

    /**
     * Converting match statements to a sequence of if statements with `instanceof`
     * calls provides union type checking. Each case generates its own if statement.
     */
    @Override
    public Optional<ClassWriter> visitMatch(final Stmt.Match match) {

        var funcType = functionStack.peek();
        var ga = funcType.ga;

        // Execute and store the match expression as an untyped Object
        match.expr.accept(this);
        int localExprIndex = ga.newLocal(Constants.ObjectType);
        var scopedName = match.identifier.identifier.source();
        funcType.localMap.put(scopedName, localExprIndex);
        ga.storeLocal(localExprIndex);

        for (Stmt.TypeStmt typeStmt : match.cases.keySet()) {
            var end = new Label();
            ga.loadLocal(localExprIndex);
            var t = match.types.get(typeStmt);
            // Todo: fix this with TypeCheckVisitor, should have no UKTs in this pass
            if (t instanceof UnknownType ukt) {
                var attempt = currentEnvironment.getVariable(ukt.typeName);
                if (attempt != null) {
                    t = attempt;
                }
            }

            if (t instanceof ListType lt) {
                // Cast to ListWrapper
                ga.instanceOf(Constants.ListWrapperType);
                ga.visitJumpInsn(Opcodes.IFEQ, end);
                ga.loadLocal(localExprIndex);
                ga.checkCast(Constants.ListWrapperType);
                ga.storeLocal(localExprIndex);

                // Acquire the type
                ga.loadLocal(localExprIndex);
                funcType.ga.invokeVirtual(Constants.ListWrapperType, new Method("name", "()Ljava/lang/String;"));
                // Compare content type
                ga.push(lt.contentType().getName());
                funcType.ga.invokeVirtual(Type.getType(String.class), new Method("equals", "(Ljava/lang/Object;)Z"));
                ga.visitJumpInsn(Opcodes.IFEQ, end);
                ga.loadLocal(localExprIndex);

            } else {
                // Check if match expression is this case's type
                Type typeClass;
                if (t instanceof BuiltInType bt) {
                    typeClass = Type.getType(t.getTypeClass());
                } else if (t instanceof StructType st) {
                    typeClass = Type.getType("L" + st.qualifiedName + ";");
                } else {
                    typeClass = Type.getType(t.getDescriptor());
                }
                ga.instanceOf(typeClass);
                ga.visitJumpInsn(Opcodes.IFEQ, end);
                ga.loadLocal(localExprIndex);
                ga.checkCast(typeClass);
            }

            // Store the scoped local and potentially cast to primitive
            if (t instanceof BuiltInType bt && bt.isNumeric()) {
                // if a cast is needed, override the local index
                bt.unboxNoCheck(ga);

                int localPrimitiveType = ga.newLocal(Type.getType(Util.convert(bt.getTypeClass())));
                ga.storeLocal(localPrimitiveType);
                funcType.localMap.put(scopedName, localPrimitiveType);
            } else {
                int localObjectType = ga.newLocal(Constants.ObjectType);
                ga.storeLocal(localObjectType);
                funcType.localMap.put(scopedName, localObjectType);
            }

            // Codegen the case body
            var block = match.cases.get(typeStmt);
            currentEnvironment = block.environment;
            block.accept(this);
            currentEnvironment = currentEnvironment.getParent();

            // Mark the end of this if statement
            ga.mark(end);
        }

        return Optional.empty();
    }


    @Override
    public Optional<ClassWriter> visitParameterStmt(Stmt.Parameter stmt) {

        throw new NotImplementedException("method not implemented");
    }

    @Override
    public Optional<ClassWriter> visitPostfixExpr(Expr.Postfix expr) {

        var ga = functionStack.peek().ga;
        expr.expr.accept(this);
        if (expr.realType instanceof BuiltInType bt) {
            bt.pushOne(ga);
            int opcode = bt.getAddOpcode();
            if (expr.operator.type() == TokenType.DEC) opcode = bt.getSubtractOpcode();

            ga.visitInsn(opcode);
            // Todo: store this
            if (expr.expr instanceof Expr.Identifier eid) {

                ga.storeLocal(functionStack.peek().localMap.get(eid.identifier.source()));
            }
        } else {
            Util.exit("postfix only works with builtin types", 49);
        }
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitPrefix(Expr.Prefix expr) {
        var ga = functionStack.peek().ga;

        expr.right.accept(this);

        var t = expr.right.realType;
        if (expr.operator.type() == TokenType.SUB && t instanceof BuiltInType bt) {
            ga.visitInsn(bt.getNegOpcode());
            expr.realType = t;
        }
        return Optional.empty();
    }

    @Override
    public Optional<ClassWriter> visitPropertyAccess(Expr.PropertyAccess expr) {
        var ga = functionStack.peek().ga;
        var t = expr.realType;

        var root = expr.expr;
        var rootType = root.realType;
        System.out.println(rootType);

        root.accept(this);

        for (int i = 0; i < expr.typeChain.size() - 1; i++) {
            var current = expr.typeChain.get(i);
            var next = expr.typeChain.get(i + 1);
            var fieldName = expr.identifiers.get(i).identifier.source();
            ga.getField(Type.getType(current.getDescriptor()), fieldName, Type.getType(next.getDescriptor()));
            if (current instanceof StructType st && next instanceof UnionType ut) {
//                if (ut.getDescriptor().equals("Ljava/lang/Object;")) {
//
//                } else {
//                ga.visitTypeInsn(Opcodes.CHECKCAST, current.getDescriptor());
//                }
            }
        }

        return Optional.empty();
    }


    @Override
    public Optional<ClassWriter> visitReturnStmt(Stmt.Return stmt) {
        var funcType = functionStack.peek();
        if (!(stmt.expr instanceof Expr.Empty)) {
            stmt.expr.accept(this);

            // Box any primitive value that is returned by a function with UnionType result
            if (funcType.returnType instanceof UnionType && stmt.expr.realType instanceof BuiltInType bt) {
                bt.doBoxing(funcType.ga);
            }

            funcType.ga.visitInsn(funcType.returnType.getReturnOpcode());
        } else {
            funcType.ga.visitInsn(Opcodes.RETURN);
        }
        return Optional.empty();
    }


    @Override
    public Optional<ClassWriter> visitStruct(Stmt.Struct struct) {
        var innerCw = new ClassWriter(CodegenVisitor.flags);
        var structType = currentEnvironment.getVariableTyped(struct.name.source(), StructType.class);

        String name = structType.name;
        String innerName = source.getFullRelativePath() + "$" + name;

        // Create the inner class
        innerCw.visit(CodegenVisitor.CLASS_VERSION, Constants.PublicStatic, innerName, null, "java/lang/Object", null);
        // Link inner class to outer class
        // Todo: should this be public + static?
        cw.visitInnerClass(innerName, source.getFullRelativePath(), name, Constants.PublicStatic);
        // Link outer class to inner class
        innerCw.visitOuterClass(source.getFullRelativePath(), name, "()V");

        // Add field reference to outer class
//        String descriptor = "L" + source.getFullRelativePath() + ";";
//        FieldVisitor fieldVisitor = innerCw.visitField(Opcodes.ACC_FINAL + Opcodes.ACC_SYNTHETIC, "this$0", descriptor, null, null);
//        fieldVisitor.visitEnd();

        StringBuilder constructorDescriptor = new StringBuilder();

        for (Identifier field : structType.parameters) {
            ImpType type = field.type;
            var descriptor = type.getDescriptor();
            String n = field.name;

            var fieldVisitor = innerCw.visitField(Opcodes.ACC_PUBLIC, n, descriptor, null, null);
            fieldVisitor.visitEnd();

            constructorDescriptor.append(descriptor);
        }

        // Generate inner class Struct constructor
        var descriptor = "(" + constructorDescriptor + ")V";
        MethodVisitor _mv = innerCw.visitMethod(Opcodes.ACC_PUBLIC, Constants.Init, descriptor, null, null);
        var ga = new GeneratorAdapter(_mv, Opcodes.ACC_PUBLIC, Constants.Init, descriptor);

        // Load outer class
//        descriptor = "L" + source.getFullRelativePath() + ";";
//        ga.visitVarInsn(Opcodes.ALOAD, 0);
//        ga.visitVarInsn(Opcodes.ALOAD, 1);
        String ownerInternalName = source.getFullRelativePath() + "$" + name;
//        ga.visitFieldInsn(Opcodes.PUTFIELD, ownerInternalName, "this$0", descriptor);

        // Call super()
        ga.loadThis();
        ga.invokeConstructor(Constants.ObjectType, new Method(Constants.Init, "()V"));

        // Set fields
        for (int i = 0; i < structType.parameters.size(); i++) {
            ImpType type = structType.parameters.get(i).type;
            descriptor = type.getDescriptor();
            String n = structType.parameters.get(i).name;
            ga.visitVarInsn(Opcodes.ALOAD, 0);
            ga.loadArg(i);
//            ga.visitVarInsn(type.getLoadVariableOpcode(), i + 2);

            ga.visitFieldInsn(Opcodes.PUTFIELD, ownerInternalName, n, descriptor);
        }

        // Append return
        ga.returnValue();
        ga.endMethod();

        // Add toString
        BytecodeGenerator.addToString(innerCw, structType, constructorDescriptor.toString(), ownerInternalName);

        structWriters.put(structType, innerCw);

        return Optional.of(innerCw);
    }

    @Override
    public Optional<ClassWriter> visitType(Stmt.TypeStmt stmt) {
        throw new NotImplementedException("method not implemented");
    }

    @Override
    public Optional<ClassWriter> visitUnionType(Stmt.UnionTypeStmt unionTypeStmt) {
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

        // Postfix stuff
        if (stmt.expr instanceof Expr.Postfix pe) {
            pe.localIndex = stmt.localIndex;
        }

        return Optional.empty();
    }
}
