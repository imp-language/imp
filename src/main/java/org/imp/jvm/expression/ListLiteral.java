package org.imp.jvm.expression;

import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.types.BuiltInType;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.List;

public class ListLiteral extends Literal {
    private final String ARRAY_LIST_CLASS = "java/util/ArrayList";

    public final List<Expression> elements;

    public ListLiteral(List<Expression> elements) {
        super(BuiltInType.OBJECT, "");
        this.elements = elements;
    }

    public void generate(MethodVisitor mv, Scope scope) {
        mv.visitTypeInsn(Opcodes.NEW, ARRAY_LIST_CLASS);
        mv.visitInsn(Opcodes.DUP);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, ARRAY_LIST_CLASS, "<init>", "()V", false);

    }

    @Override
    public void validate(Scope scope) {
        for (Expression el : elements) {
            el.validate(scope);
        }
        var firstType = elements.get(0).type;
        for (int i = 1; i < elements.size(); i++) {
            if (!elements.get(i).type.equals(firstType)) {
                System.err.println("Reeee cannot have lists of multiple types. Try a tuple.");
                System.exit(90);
            }
        }


    }

}
