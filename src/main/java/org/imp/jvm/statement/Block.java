package org.imp.jvm.statement;

import org.apache.commons.lang3.NotImplementedException;
import org.imp.jvm.domain.scope.Scope;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.util.ArrayList;
import java.util.List;

public class Block extends Statement {
    public final List<Statement> statements;
    public Scope scope;

    public Block(List<Statement> statements, Scope scope) {
        this.statements = statements;
        this.scope = scope;
    }

    public Block() {
        this.statements = new ArrayList<>();
        this.scope = new Scope("unnamed");
    }

    @Override
    public void generate(MethodVisitor mv, Scope scope) {
        for (var stmt : statements) {
            stmt.generate(mv, this.scope);
        }
    }

}
