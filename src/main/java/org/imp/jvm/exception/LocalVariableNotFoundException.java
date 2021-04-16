package org.imp.jvm.exception;

import org.imp.jvm.domain.scope.Scope;

/**
 * Created by kuba on 06.04.16.
 */
public class LocalVariableNotFoundException extends RuntimeException {
    public LocalVariableNotFoundException(Scope scope, String variableName) {
        super("No local variable found for name '" + variableName + "' found in scope " + scope);
    }
}