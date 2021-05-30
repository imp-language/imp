package org.imp.jvm.compiler;

import org.antlr.v4.runtime.Token;
import org.imp.jvm.exception.SyntacticalException;
import org.imp.jvm.exception.SyntaxErrors;

import java.util.ArrayList;
import java.util.List;

public class Logger {

    private static List<RuntimeException> syntaxErrors = new ArrayList<>();

    public static void syntaxError(SyntaxErrors error, Token token) {
        syntaxErrors.add(new SyntacticalException(error, token));
    }

    public static List<RuntimeException> getSyntaxErrors() {
        return syntaxErrors;
    }
}
