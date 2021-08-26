package org.imp.jvm.compiler;

import org.antlr.v4.runtime.ParserRuleContext;
import org.imp.jvm.exception.SyntacticalException;
import org.imp.jvm.exception.SemanticErrors;

import java.util.ArrayList;
import java.util.List;

public class Logger {

    private static final List<RuntimeException> syntaxErrors = new ArrayList<>();

    public static void syntaxError(SemanticErrors error, ParserRuleContext ctx) {
//        throw new Error();
        syntaxErrors.add(new SyntacticalException(error, ctx));
    }

    public static List<RuntimeException> getSyntaxErrors() {
        return syntaxErrors;
    }

    public static boolean hasErrors() {
        return syntaxErrors.size() > 0;
    }
}
