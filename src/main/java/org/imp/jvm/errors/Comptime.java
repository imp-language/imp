package org.imp.jvm.errors;

import org.apache.commons.lang3.StringUtils;
import org.imp.jvm.Util;
import org.imp.jvm.parser.Node;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public enum Comptime {
    Implementation(-1, "{0}", "If you are seeing this message it indicates a regression in the Imp compiler. Please contact the developers."),

    TypeNotFound(1, "Type `{0}` does not exist in the current environment.",
            "Make sure all types are defined or builtin."),
    TypeNotResolved(2, "Variable `{0}` cannot be resolved to a type.",
            "Make sure all variables are properly spelled etc."),
    ModuleNotFound(3, "Module `{0}` is not found.", "Is the module misspelled?"),
    Redeclaration(4, "Redeclaration of variable `{0}`.",
            "You cannot redeclare variables."),
    ReturnTypeMismatch(5,
            "Function `{0}` has return type of `{1}`. Cannot have another return statement with type `{2}`.",
            "Make sure all return statements in your function are returning the same type."),
    PropertyNotFound(6, "Type `{0}` contains no field `{1}`.", null),
    CannotApplyOperator(7, "Operator `{0}` cannot be applied to types `{1}` and `{2}`", null),
    ExternNotFound(8, "External object `{0}` not found.", "Ensure the external type you are referencing actually exists."),
    MethodNotFound(9, "Method `{0}` not found.", null),

    MutabilityError(10, "Variable `{0}` is immutable and cannot receive assignment.",
            "Declare a variable with the `mut` keyword to allow mutability."),
    BadAssignment(11, "Variable `{0}` of type `{1}` cannot accept assignment of type `{2}`.",
            "Check that both sides of the assignment have the same type."),
    FunctionSignatureMismatch(12, "No function overloads exist on `{0}` that match the parameters `{1}`.", "Check the parameter positions and types of the called function."),
    IdentifierNotFound(13, "Identifier `{0}` not found.", null),
    ReservedWord(14, "Identifier `{0}` is a resered word.", null);

    // Todo: variable not found

    //


    static final List<String> errors = new ArrayList<>();
    private final String suggestion;
    private final int code;
    private final String templateString;


    Comptime(int code, String templateString, String suggestion) {
        this.suggestion = suggestion;
        this.code = code;
        this.templateString = templateString;
    }

    public static boolean hasErrors() {
        return errors.size() > 0;
    }

    public static void killIfErrors(String message) {
        if (hasErrors()) {
            errors.forEach(System.out::println);
            Util.println(message);
            System.exit(1);
        }
    }

    public void submit(File file, Node node, Object... varargs) {

        try {
            var loc = node.location();
            int line = loc.line() - 2;
            if (line < 0) line = 0;
            int limit = 3;

            Stream<String> lines = Files.lines(file.toPath());
            var selection = lines.skip(line).limit(limit).toList();

            int startLine = line;
            int endLine = line + limit;
            int padding = (int) Math.ceil(Math.log10(endLine + 1)) + 1;
            var result = IntStream.range(0, limit)
                    .mapToObj(i -> {
                        if (i < selection.size()) {

                            var s = StringUtils.leftPad((i + startLine + 1) + "|", padding) + " " + selection.get(i);
                            if (i + startLine + 1 == loc.line()) {
                                s += "\n" + "^".repeat(loc.col() + padding);
                            }
                            return s;
                        } else {
                            return (i + startLine + 1) + "|";
                        }
                    })
                    .collect(Collectors.joining("\n"));

            String s = file.getName() + "@" + loc.line() + ":" + loc.col() + " Error[" + code + ", " + name() + "] ";
            s += MessageFormat.format(templateString, varargs) + "\n";
            s += result + "\n";
            if (suggestion != null) {
                s += suggestion;
            }

            errors.add(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
