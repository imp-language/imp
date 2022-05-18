package org.imp.jvm;

import org.imp.runtime.ListWrapper;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Helpful constants that can be statically determined.
 */
public class Constants {
    public static final Type ListWrapperType = Type.getType(ListWrapper.class);
    public static final Type ArrayListType = Type.getType(ArrayList.class);
    public static final Type IteratorType = Type.getType(Iterator.class);
    public static final Type ObjectType = Type.getType(Object.class);
    public static final String Init = "<init>";
    public static final String Clinit = "<clinit>";
    // https://mariadb.com/kb/en/operating-system-error-codes/
    public static final int ENOENT = 2;

    public static final int PublicStatic = Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC;

}
