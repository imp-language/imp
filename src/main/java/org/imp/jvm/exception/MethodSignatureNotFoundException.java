package org.imp.jvm.exception;

import org.imp.jvm.domain.scope.Identifier;
import org.imp.jvm.domain.scope.Scope;

import java.util.List;

/**
 * Created by kuba on 09.04.16.
 */
public class MethodSignatureNotFoundException extends RuntimeException {
    public MethodSignatureNotFoundException(Scope scope, String methodName, List<Identifier> parameterTypes) {
        super("There is no method '" + methodName + "' with parameters " + parameterTypes);
    }
}