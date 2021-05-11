package org.imp.jvm.exception;

import org.imp.jvm.domain.scope.Scope;

/**
 * Created by kuba on 06.04.16.
 */
public class StructNotFoundException extends RuntimeException {
    public StructNotFoundException(Scope scope, String structName) {
        super("No struct found for name '" + structName + "' found in scope " + scope);
    }
}