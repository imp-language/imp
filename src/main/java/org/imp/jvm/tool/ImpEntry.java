package org.imp.jvm.tool;

import java.io.IOException;

public class ImpEntry {
    public static void main(String[] args) throws IOException {
        var imp = new Imp("examples/scratch.imp");
        imp.compile();
        ImpAPI.run("examples.scratch.Entry");
    }
}
