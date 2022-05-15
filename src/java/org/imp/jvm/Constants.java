package org.imp.jvm;

import org.imp.runtime.ListWrapper;
import org.objectweb.asm.Type;

/**
 * Helpful constants that can be statically determined.
 */
public class Constants {
    public static final Type ListWrapperType = Type.getType(ListWrapper.class);
}
