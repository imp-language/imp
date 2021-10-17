package org.imp.jvm.expression;

import org.imp.jvm.compiler.Logger;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.exception.Errors;
import org.imp.jvm.types.BuiltInType;
import org.imp.jvm.types.ListType;
import org.imp.jvm.types.Type;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;
import java.util.Optional;

public class ListLiteral extends Literal {

    public final List<Expression> elements;

    public Type listType = null;

    public ListLiteral(List<Expression> elements) {
        super(BuiltInType.OBJECT, "");
        this.elements = elements;
    }

    public void generate(MethodVisitor mv, Scope scope) {
        mv.visitLdcInsn(elements.size());
        mv.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object");

        for (int i = 0; i < elements.size(); i++) {
            mv.visitInsn(Opcodes.DUP);
            mv.visitLdcInsn(i);
            Expression el = elements.get(i);
            el.generate(mv, scope);
            // Todo: cast bad
            if (el.type instanceof BuiltInType builtInType) {
                builtInType.doBoxing(mv);
            }
            mv.visitInsn(Opcodes.AASTORE);
        }

        String AS_LIST_DESCRIPTOR = "([Ljava/lang/Object;)Ljava/util/List;";
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/util/Arrays", "asList", AS_LIST_DESCRIPTOR, false);
    }

    @Override
    public void validate(Scope scope) {
        for (Expression el : elements) {
            el.validate(scope);
        }
        var firstType = elements.get(0).type;

        for (int i = 1; i < elements.size(); i++) {
            if (!elements.get(i).type.equals(firstType)) {
                Logger.syntaxError(Errors.ListTypeError, "no filename", this.getCtx(), elements.get(i).type, firstType);
            }
        }

        this.listType = firstType;
        this.type = new ListType(firstType);


    }

}
