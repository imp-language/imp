package org.imp.jvm.compiler;

import org.antlr.v4.runtime.ParserRuleContext;
import org.imp.jvm.exception.Errors;

import java.util.ArrayList;
import java.util.List;

public class Logger {

    private static final List<String> syntaxErrors = new ArrayList<>();

    public static void syntaxError(Errors error, String filename, ParserRuleContext ctx, Object... varargs) {
        String result = error.template(filename, ctx, varargs);
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
