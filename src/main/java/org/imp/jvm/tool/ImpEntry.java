package org.imp.jvm.tool;

public class ImpEntry {
    public static void main(String[] args) {
        var imp = new Imp("examples/scratch.imp");
        imp.compile();
    }
}
