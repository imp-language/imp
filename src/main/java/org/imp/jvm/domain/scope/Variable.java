package org.imp.jvm.domain.scope;

import org.imp.jvm.types.Type;

public interface Variable {
    Type getType();

    String getName();
}