package org.imp.jvm.compiler;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.imp.jvm.exception.SyntacticalException;
import org.imp.jvm.exception.SemanticErrors;

import java.util.ArrayList;
import java.util.List;

public class Logger {

    private static List<RuntimeException> syntaxErrors = new ArrayList<>();

    public static void syntaxError(SemanticErrors error, ParserRuleContext ctx) {
        syntaxErrors.add(new SyntacticalException(error, ctx));
    }

    public static List<RuntimeException> getSyntaxErrors() {
        return syntaxErrors;
    }
}
