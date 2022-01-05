package org.imp.jvm.tool;

import org.apache.commons.collections4.map.MultiKeyMap;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.imp.jvm.domain.SourceFile;
import org.imp.jvm.types.Type;

import java.util.Optional;

public class ExportTable {

    public static String createTable = "create table ExportTable (source string, name string)";

    // Eventually this will be backed by SQLite or something
    private static final MultiKeyMap<String, Type> table = new MultiKeyMap<>();


    public static void add(SourceFile source, String name, Type type) {
        String path = FilenameUtils.removeExtension(source.file.getPath());
        path = FilenameUtils.separatorsToUnix(path);
        table.put(path, name, type);
    }

    public static Optional<Type> get(String source, String name) {
        String path = FilenameUtils.separatorsToUnix(source);
        return Optional.ofNullable(table.get(path, name));
    }

    public static String dump() {
        StringBuilder s = new StringBuilder();
        table.forEach((k1, k2) -> {
            s.append(StringUtils.rightPad(k1.toString(), 35)).append("\t").append(k2).append("\n");
        });
        return s.toString();
    }
}
