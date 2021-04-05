package org.imp.jvm.domain.scope;

import org.imp.jvm.domain.types.Type;

public interface Variable {
    Type getType();

    String getName();
}