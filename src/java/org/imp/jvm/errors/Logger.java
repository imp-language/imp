package org.imp.jvm.errors;

import org.imp.jvm.Util;

import java.util.ArrayList;
import java.util.List;

public class Logger {

    private static final List<String> syntaxErrors = new ArrayList<>();


    public static List<String> getSyntaxErrors() {
        return syntaxErrors;
    }

    public static boolean hasErrors() {
        return syntaxErrors.size() > 0;
    }

    public static void killIfErrors(String message) {
        if (hasErrors()) {
            Logger.getSyntaxErrors().forEach(System.out::println);
            Util.println(message);
            System.exit(1);
        }
    }
}
