package org.imp.jvm.tool;

import java.io.IOException;

@Deprecated(forRemoval = true)
public class ImpEntry {
    public static void main(String[] args) throws IOException, InterruptedException {
        var imp = new Imp("examples/scratch.imp");
        imp.compile();
        ImpAPI.run("examples.scratch.Entry");
    }
}
