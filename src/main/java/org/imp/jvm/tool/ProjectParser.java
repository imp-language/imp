package org.imp.jvm.tool;


import org.imp.jvm.tool.manifest.Version;
import org.tomlj.TomlParseResult;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class ProjectParser {
    private static final String PATH_NAME = "project.name";
    private static final String PATH_VERSION = "project.version";
    private static final String PATH_ENTRY = "project.entry";

    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
        prop.load(new FileReader("examples/imp.properties"));
        List<String> authors = null;

        var manifest = new Manifest(
                prop.getProperty("name"),
                Version.from(prop.getProperty("version")),
                getOrDefault(prop, "description", ""),
                authors,
                getOrDefault(prop, "homepage", ""),
                getOrDefault(prop, "docs", ""),
                getOrDefault(prop, "repo", ""),
                getOrDefault(prop, "license", ""),
                null,
                getOrDefault(prop, "publish", false),
                getOrDefault(prop, "imp", new Version(1, 0, 0, ""))
        );
        System.out.println(manifest);
    }

    static <T> T getOrDefault(Properties prop, String key, T defaultValue) {
        if (prop.contains(key)) {
            return (T) prop.getProperty(key);
        } else {
            return defaultValue;
        }
    }

    public static ImpProjectConfiguration validate(TomlParseResult toml) {

        var authors = toml.getArray("package.authors").toList().stream().map(Object::toString).collect(Collectors.toList());


        return null;
    }
}
