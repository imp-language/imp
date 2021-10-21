package org.imp.jvm.compiler;

import org.imp.jvm.exception.Errors;
import org.imp.jvm.statement.Statement;

import java.util.ArrayList;
import java.util.List;

public class Logger {

    private static final List<String> syntaxErrors = new ArrayList<>();

    public static void syntaxError(Errors error, Statement statement, Object... varargs) {
        String result = error.template(statement, varargs);
        syntaxErrors.add(result);
    }

    public static List<String> getSyntaxErrors() {
        return syntaxErrors;
    }

    public static boolean hasErrors() {
        return syntaxErrors.size() > 0;
    }

    public static void killIfErrors(String message) {
        if (hasErrors()) {
            Logger.getSyntaxErrors().forEach(System.out::println);
            System.out.println(message);
            System.exit(1);
        }
    }
}
