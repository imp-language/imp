package org.imp.jvm.statement;

import org.imp.jvm.domain.scope.Scope;
import org.imp.jvm.domain.types.Mutability;
import org.imp.jvm.expression.Expression;
import org.objectweb.asm.MethodVisitor;

public class Declaration extends Statement {
    public final Mutability mutability;
    public final String name;
    public final Expression expression;

    public Declaration(Mutability mutability, String name, Expression expression) {
        this.mutability = mutability;
        this.name = name;
        this.expression = expression;
    }


    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        // Todo: https://github.com/JakubDziworski/Enkel-JVM-language/blob/1527076545f7402a279db2c19f1e28ba7f084585/compiler/src/main/java/com/kubadziworski/bytecodegeneration/statement/VariableDeclarationStatementGenerator.java
        expression.generate(mv);
        Assignment assignment = new Assignment(this);
        assignment.generate(mv, scope);
    }


}
