package org.imp.jvm.runtime;

import org.imp.jvm.domain.scope.FunctionSignature;

public abstract class FirstClassFunction {

    public final String name;
    public final FunctionSignature signature;

    public FirstClassFunction(String name, FunctionSignature signature) {
        this.name = name;
        this.signature = signature;
    }
}
