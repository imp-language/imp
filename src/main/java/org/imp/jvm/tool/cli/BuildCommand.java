package org.imp.jvm.tool.cli;

import org.imp.jvm.Util;
import picocli.CommandLine;

@CommandLine.Command(
        name = "build",
        description = "Compile the current project."
)
class BuildCommand implements Runnable {


    @Override
    public void run() {
        Util.println("Building project");
    }

}
