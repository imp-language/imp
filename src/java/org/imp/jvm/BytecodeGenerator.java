package org.imp.jvm;

import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.domain.SourceFile;
import org.imp.jvm.tool.Compiler;
import org.imp.jvm.types.StructType;
import org.imp.jvm.visitors.CodegenVisitor;
import org.javatuples.Pair;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Map;

import static java.lang.invoke.MethodType.methodType;

public class BytecodeGenerator {
    static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    static final MethodType EQUALS_DESC = methodType(boolean.class, BytecodeGenerator.class, Object.class);
    static final MethodType HASHCODE_DESC = methodType(int.class, BytecodeGenerator.class);
    static final MethodType TO_STRING_DESC = methodType(String.class, BytecodeGenerator.class);

    public static void addToString(ClassWriter cw) {
        var _mv = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL, "toString", "()Ljava/lang/String;", null, null);
        var ga = new GeneratorAdapter(_mv, Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL, "toString", "()Ljava/lang/String;");
        ga.loadThis();

        String owner = "java/lang/runtime/ObjectMethods";
        String name = "bootstrap";
        String descriptor = "("
                + "Ljava/lang/invoke/MethodHandles$Lookup;" +
                "Ljava/lang/String;" +
                "Ljava/lang/invoke/TypeDescriptor;" +
                "Ljava/lang/Class;" +
                "Ljava/lang/String;" +
                "[Ljava/lang/invoke/MethodHandle;" +
                ")Ljava/lang/Object;";

        var handle = new Handle(Opcodes.H_INVOKESTATIC, owner, name, descriptor, false);
        Object args = new Object[]{"data"};

//        CallSite cs = (CallSite) ObjectMethods.bootstrap(LOOKUP, "toString", BytecodeGenerator.TO_STRING_DESC, BytecodeGenerator.class, BytecodeGenerator.NAME_LIST, C.ACCESSORS);
//        MethodHandle handle = cs.dynamicInvoker();
//        try {
//            var b =MethodHandles.lookup().findClass("Lmain$Tree;");

//            var mh = MethodHandles.lookup().findGetter(b, "data", int.class);
        ga.invokeDynamic(
                "toString",
                "(Lmain$Tree;)Ljava/lang/String;",
                handle,
                Type.getType("Lmain$Tree;"),
                "data"
        );

        ga.visitInsn(Opcodes.ARETURN);

//        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
//            e.printStackTrace();
//        }

//        ga.visitInvokeDynamicInsn("toString", "(Lmain$Tree;)Ljava/lang/String;",handle);
    }

    public Pair<ClassWriter, Map<StructType, ClassWriter>> generate(Compiler compiler, SourceFile source) {
        var cw = new ClassWriter(CodegenVisitor.flags);

        String qualifiedName = FilenameUtils.removeExtension(source.getFullRelativePath());
        cw.visit(CodegenVisitor.CLASS_VERSION, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, qualifiedName, null, "java/lang/Object", null);

        // Add constructor
        var initMv = cw.visitMethod(Opcodes.ACC_PUBLIC, Constants.Init, "()V", null, null);
        var ga = new GeneratorAdapter(initMv, Opcodes.ACC_PUBLIC, Constants.Init, "()V");
        ga.visitVarInsn(Opcodes.ALOAD, 0);
        ga.visitMethodInsn(
                Opcodes.INVOKESPECIAL,
                "java/lang/Object",
                Constants.Init,
                "()V",
                false
        );
        ga.returnValue();
        ga.endMethod();

        // Add instance field
        cw.visitField(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "instance", "L" + qualifiedName + ";", null, null);

        var mvStatic = cw.visitMethod(Opcodes.ACC_STATIC, Constants.Clinit, "()V", null, null);
        ga = new GeneratorAdapter(mvStatic, Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, Constants.Clinit, "()V");
        var t = Type.getType("L" + qualifiedName + ";");
        ga.newInstance(t);
        ga.dup();
        ga.invokeConstructor(t, new Method(Constants.Init, "()V"));

        mvStatic.visitFieldInsn(Opcodes.PUTSTATIC, qualifiedName, "instance", "L" + qualifiedName + ";");
        mvStatic.visitInsn(Opcodes.RETURN);
        ga.endMethod();

        // Generate bytecode for each Type defined in the Imp file
        var codegenVisitor = new CodegenVisitor(compiler, source.rootEnvironment, source, cw);

        source.acceptVisitor(codegenVisitor);

        cw.visitEnd();

        return new Pair<>(cw, codegenVisitor.structWriters);
    }

    static class Empty {
        static final MethodType EQUALS_DESC = methodType(boolean.class, Empty.class, Object.class);
        static final MethodType HASHCODE_DESC = methodType(int.class, Empty.class);
        static final MethodType TO_STRING_DESC = methodType(String.class, Empty.class);
        static final MethodHandle[] ACCESSORS = new MethodHandle[]{};
        static final String NAME_LIST = "";

        Empty() {
        }
    }
}
