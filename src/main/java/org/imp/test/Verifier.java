package org.imp.test;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.compiler.Compiler;
import picocli.CommandLine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Verifier {

    public static final String verificationPath = "verification";


    public static void main(String[] args) throws IOException {
        System.out.println("Imp language tests.");

        File path = new File(verificationPath);

        var pathnames = path.list();

        // For each pathname in the pathnames array
        for (String pathname : pathnames) {
            // Print the names of files and directories
            String ext = FilenameUtils.getExtension(pathname);
            if (ext.equals("imp")) {
                validate(pathname);

            }
        }
    }

    private static void validate(String name) throws IOException {
        String sourcePath = FilenameUtils.concat(verificationPath, name);
        String solutionPath = FilenameUtils.concat(verificationPath, FilenameUtils.getBaseName(name) + ".txt");


        // 0. Load result file line-by-line
        Scanner solutionScanner = new Scanner(new File(solutionPath));
        List<String> solutionLines = FileUtils.readLines(new File(solutionPath), StandardCharsets.UTF_8);

        // 1. Compile imp source file
        String[] args = {sourcePath};
        var compiler = CommandLine.populateCommand(new Compiler(), args);
        String classFilePath = compiler.compile();

        // 2. Execute compiled imp file
        compiler.run(classFilePath);


        // 3. Compare results
    }
}
