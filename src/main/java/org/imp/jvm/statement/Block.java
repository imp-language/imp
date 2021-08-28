package org.imp.jvm.statement;

import org.imp.jvm.domain.scope.Scope;
import org.objectweb.asm.MethodVisitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Block extends Statement {
    public final List<Statement> statements;
    public Scope scope;

    public Block(List<Statement> statements, Scope scope) {
        this.statements = statements;
        this.scope = scope;
    }

    @Override
    public List<Statement> getChildren() {
        return statements;
    }

    public Block() {
        this.statements = new ArrayList<>();
        this.scope = new Scope();
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        for (var stmt : statements) {
            stmt.generate(mv, scope);
        }
    }

    @Override
    public void validate(Scope scope) {
        for (var statement : statements) {
            statement.validate(scope);
        }
    }

}
