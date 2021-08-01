package org.imp.jvm.tool;

import org.tomlj.Toml;
import org.tomlj.TomlParseResult;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImpEntry {
    public static void main(String[] args) {
        var imp = new Imp("examples/scratch.imp");
        imp.compile();
    }
}
