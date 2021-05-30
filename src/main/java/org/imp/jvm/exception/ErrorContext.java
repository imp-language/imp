package org.imp.jvm.exception;

public interface ErrorContext {

    int getLine();

    int getCol();

    String getText();

}
