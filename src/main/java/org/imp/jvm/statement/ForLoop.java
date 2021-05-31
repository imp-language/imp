package org.imp.jvm.statement;

import org.apache.commons.lang3.NotImplementedException;
import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.expression.Expression;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ForLoop extends Loop {
    public final Declaration declaration;
    public final Expression condition;
    public final Statement incrementer;

    public ForLoop(Declaration declaration, Expression condition, Statement incrementer, Block body) {
        super(body);
        this.declaration = declaration;
        this.condition = condition;
        this.incrementer = incrementer;
    }

    @Override
    public void validate() {
        declaration.validate();
        condition.validate();
        incrementer.validate();
        body.validate();
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        declaration.generate(mv, scope);

        Label conditionLabel = new Label();
        Label incrementLabel = new Label();
        Label endLoopLabel = new Label();
        Label bodyLabel = new Label();


        mv.visitLabel(conditionLabel);
        condition.generate(mv, scope);
        mv.visitJumpInsn(Opcodes.IFEQ, endLoopLabel);
//
        mv.visitLabel(bodyLabel);
        this.body.generate(mv, scope);
        if (incrementer != null) {
            incrementer.generate(mv, scope);
        }
        mv.visitJumpInsn(Opcodes.GOTO, conditionLabel);
        mv.visitLabel(endLoopLabel);


    }


}
