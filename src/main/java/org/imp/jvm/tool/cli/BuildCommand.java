package org.imp.jvm.tool.cli;

import picocli.CommandLine;

@CommandLine.Command(
        name = "build",
        description = "Compile the current project."
)
class BuildCommand implements Runnable {


    @Override
    public void run() {
        System.out.println("Building project");
    }

}
