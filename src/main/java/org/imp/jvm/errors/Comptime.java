package org.imp.jvm.errors;

import org.apache.commons.lang3.StringUtils;
import org.imp.jvm.Node;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public enum Comptime {
    ReturnTypeMismatch(22,
            "Function `{0}` has return type of `{1}`. Cannot have another return statement with type `{2}`.",
            "Make sure all return statements in your function are returning the same type.");


    public final String suggestion;
    public final int code;
    public final String templateString;

    Comptime(int code, String templateString, String suggestion) {
        this.suggestion = suggestion;
        this.code = code;
        this.templateString = templateString;
    }

    public void submit(File file, Node node, Object... varargs) {

        try {
            var loc = node.location();
            int line = loc.line() - 2;
            if (line < 0) line = 0;
            int limit = 3;

            Stream<String> lines = Files.lines(file.toPath());
            var selection = lines.skip(line).limit(limit)
                    .collect(Collectors.toList());

            int startLine = line;
            int endLine = line + limit;
            int padding = (int) Math.ceil(Math.log10(endLine + 1)) + 1;
            var result = IntStream.range(0, limit)
                    .mapToObj(i -> {
                        var s = StringUtils.leftPad((i + startLine + 1) + "|", padding) + " " + selection.get(i);
                        if (i + startLine + 1 == loc.line()) {
                            s += "\n" + "^".repeat(loc.col() + padding);
                        }
                        return s;
                    })
                    .collect(Collectors.joining("\n"));

            String s = file.getName() + "@" + loc.line() + ":" + loc.col() + " Error[" + code + ", " + name() + "] ";
            s += MessageFormat.format(templateString, varargs) + "\n";
            s += result + "\n";
            s += suggestion;

            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
