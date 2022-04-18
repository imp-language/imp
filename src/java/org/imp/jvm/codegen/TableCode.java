package org.imp.jvm.codegen;

import org.objectweb.asm.Label;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.TableSwitchGenerator;

public class TableCode implements TableSwitchGenerator {
    final GeneratorAdapter ga;
    final int localIndex;
    final Label endLabel;

    public TableCode(GeneratorAdapter ga, int localIndex, Label endLabel) {
        this.ga = ga;

        this.localIndex = localIndex;
        this.endLabel = endLabel;
    }

    @Override
    public void generateCase(int i, Label label) {
        System.out.println(i);
        ga.mark(label);
        ga.push(4);
        ga.pop();
        ga.goTo(endLabel);
//        ga.loadLocal(2);
    }

    @Override
    public void generateDefault() {

    }
}
