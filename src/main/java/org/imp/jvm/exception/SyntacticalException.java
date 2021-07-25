package org.imp.jvm.exception;

import org.antlr.v4.runtime.ParserRuleContext;


public class SyntacticalException extends RuntimeException {

    public SyntacticalException(SemanticErrors error, ParserRuleContext token) {
        super(error.template(token));
    }

}