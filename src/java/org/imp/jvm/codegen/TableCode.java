package org.imp.jvm.codegen;

import org.imp.jvm.parser.Stmt;
import org.imp.jvm.visitors.CodegenVisitor;
import org.imp.runtime.Batteries;
import org.objectweb.asm.Label;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;
import org.objectweb.asm.commons.TableSwitchGenerator;

import java.util.List;

public class TableCode implements TableSwitchGenerator {
    final GeneratorAdapter ga;
    final int localIndex;
    final Label endLabel;
    final List<Stmt.Block> caseBlocks;
    final CodegenVisitor visitor;

    public TableCode(
            GeneratorAdapter ga,
            int localIndex,
            Label endLabel,
            List<Stmt.Block> caseBlocks,
            CodegenVisitor visitor
    ) {
        this.ga = ga;

        this.localIndex = localIndex;
        this.endLabel = endLabel;
        this.caseBlocks = caseBlocks;
        this.visitor = visitor;
    }

    @Override
    public void generateCase(int i, Label label) {
        var caseBlock = caseBlocks.get(i);
        System.out.println(i);
        ga.mark(label);
        visitor.currentEnvironment = caseBlock.environment;
//        caseBlock.accept(visitor);
        ga.loadLocal(2);
        ga.invokeStatic(Type.getType(Batteries.class), new Method("log", "(Ljava/lang/Object;)V"));

        visitor.currentEnvironment = visitor.currentEnvironment.getParent();
        ga.goTo(endLabel);
//        ga.loadLocal(2);
    }

    @Override
    public void generateDefault() {

    }
}
