package org.imp.jvm.runtime.stdlib;

import java.util.Random;

/**
 * The _ prefix is removed in Imp and only exists to allow
 * this Java class to compile.
 */
public class Process {
    // Todo: the error code does not propagate through the ImpAPI runner.
    // Not a big deal, just fix eventually.
    public static void exit(int code) {
        System.exit(code);
    }
}
