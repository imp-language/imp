package org.imp.jvm.tool;


import org.tomlj.Toml;
import org.tomlj.TomlParseResult;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProjectParser {
    private static final String PATH_NAME = "project.name";
    private static final String PATH_VERSION = "project.version";
    private static final String PATH_ENTRY = "project.entry";

    public static void main(String[] args) throws IOException {
        Path source = Paths.get("examples/imp.toml");
        TomlParseResult result = Toml.parse(source);
        result.errors().forEach(error -> System.err.println(error.toString()));
        validate(result);
    }

    public static ImpProjectConfiguration validate(TomlParseResult toml) {

        String name = "";
        String version = "";
        String entry = "";

        if (toml.contains(PATH_NAME)) {
            name = toml.getString(PATH_NAME);
        } else {
            throw new RuntimeException(PATH_NAME + " not found in 'imp.toml'.");
        }

        if (toml.contains(PATH_VERSION)) {
            version = toml.getString(PATH_VERSION);
        } else {
            throw new RuntimeException(PATH_VERSION + " not found in 'imp.toml'.");
        }

        if (toml.contains(PATH_ENTRY)) {
            entry = toml.getString(PATH_ENTRY);
        } else {
            throw new RuntimeException(PATH_ENTRY + " not found in 'imp.toml'.");
        }

        ImpProjectConfiguration pack = new ImpProjectConfiguration(name, version, entry);

        System.out.println(pack);
        return pack;
    }
}
