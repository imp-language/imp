package org.imp.jvm.exception;

import org.antlr.v4.runtime.Token;


public class SyntacticalException extends RuntimeException {

    public SyntacticalException(SemanticErrors error, Token token) {
        super(error.template(token));
    }

}