package org.imp.jvm.domain.scope;

import org.imp.jvm.types.Type;

// Todo: deprecate
public interface Variable {
    Type getType();

    String getName();
}