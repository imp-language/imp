package org.imp.jvm.tool;

import java.io.IOException;

public class Runner {


    public static Process spawn(String className) throws IOException {
        String cmd = "java --enable-preview -cp .compile" + System.getProperty("path.separator") + "target/classes " + className;
        return Runtime.getRuntime().exec(cmd);
    }
}
