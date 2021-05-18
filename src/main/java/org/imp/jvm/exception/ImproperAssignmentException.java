package org.imp.jvm.exception;

import org.imp.jvm.domain.scope.Scope;


public class ImproperAssignmentException extends Exception {
    public ImproperAssignmentException(Scope scope, String structName) {
        super("Improper assignment.");
    }
}