package org.imp.test;

import org.imp.jvm.Util;
import org.imp.jvm.errors.Comptime;
import org.imp.jvm.tool.Compiler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.stream.Collectors;

// Todo: move to BaseTest class an delete this
public class Load {

    static final Compiler compiler = new Compiler();

    public static Map<Integer, Long> checkForErrors(String testPath, String projectRoot) throws IOException {
        try {
            new Compiler().compile(projectRoot, testPath + ".imp");
        } catch (Comptime.CompilerError e) {
            var errorSet = e.errorData.stream().map(Comptime.Data::code).collect(Collectors.groupingBy(s -> s,
                    Collectors.counting()));
            System.err.println(errorSet);
            return errorSet;
        }

        return null;
    }

    public static String gold(String goldPath) {
        try {
            return Files.readString(Path.of(goldPath)).replaceAll("\\r\\n?", "\n");
        } catch (IOException e) {
            Util.exit("File at " + Path.of(goldPath) + " does not exist.", 1);
        }
        return null;
    }
}
