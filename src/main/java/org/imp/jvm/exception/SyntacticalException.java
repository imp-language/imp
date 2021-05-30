package org.imp.jvm.exception;

import org.antlr.v4.runtime.Token;
import org.imp.jvm.statement.Statement;


public class SyntacticalException extends RuntimeException {

    public SyntacticalException(SyntaxErrors error, Token token) {
        super(error.template(token));
    }

}