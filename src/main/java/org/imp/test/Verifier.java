package org.imp.test;

import name.fraser.neil.plaintext.diff_match_patch;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.compiler.Compiler;
import picocli.CommandLine;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Verifier {

    public static final String verificationPath = "verification";

    public static List<String> flawsList = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        System.out.println("Imp language tests.");

        File path = new File(verificationPath);

        var pathnames = path.list();

        long startTime = System.currentTimeMillis();


        // For each pathname in the pathnames array
        for (String pathname : pathnames) {
            // Print the names of files and directories
            String ext = FilenameUtils.getExtension(pathname);
            if (ext.equals("imp")) {
                validate(pathname);

            }
        }

        if (flawsList.size() > 0) {
            var flaws = String.join(", ", flawsList);
            System.out.println("Flaws found in " + flaws + ".");

        } else {
            System.out.println("No flaws found.");
        }
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        System.out.printf("Finished all tests in %.0f ms.", (float) duration);
    }

    private static void validate(String name) throws IOException {
        System.out.println("### File: " + name + " ###");
        System.out.println("--------------------------------------------");

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
        Process proc = compiler.spawn(classFilePath);


        // 3. Watch standard out
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));

//        System.out.println("Here is the standard output of the command:\n");
        List<String> stdOutLines = new ArrayList<>();
        String s = null;
        while ((s = stdInput.readLine()) != null) {
//            System.out.println(s);
            stdOutLines.add(s);
        }

        System.out.println("Here is the standard error of the command (if any):\n");
        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }
        System.out.println("");

        // 4. Compare results
        assert solutionLines.size() == stdOutLines.size();
        int flaws = 0;
        for (int i = 0; i < solutionLines.size(); i++) {
            String solution = solutionLines.get(i);
            String stdOut = stdOutLines.get(i); // Todo: this doesn't get caught by the above assertion

            if (solution.equals(stdOut)) {
                System.out.println(solution);

            } else {
                System.out.println(stdOut + " !! " + solution);
                flaws++;
            }

        }
        if (flaws > 0) {
            flawsList.add(name);
        }
        System.out.println("--------------------------------------------");
        System.out.println(flaws + " flaws found in " + name + ".");
        System.out.println();



        /*
        String solution = String.join("\n", solutionLines);
        String stdOut = String.join("\n", stdOutLines);

        diff_match_patch dmp = new diff_match_patch();
        LinkedList<diff_match_patch.Diff> diff = dmp.diff_main(solution, stdOut);
        // Result: [(-1, "Hell"), (1, "G"), (0, "o"), (1, "odbye"), (0, " World.")]
        dmp.diff_cleanupSemantic(diff);
        // Result: [(-1, "Hello"), (1, "Goodbye"), (0, " World.")]
        System.out.println(diff);

        System.out.println(diff_prettyStrikethrough(diff));
        */
    }

    private static String diff_prettyStrikethrough(List<diff_match_patch.Diff> diffs) {
        StringBuilder html = new StringBuilder();
        for (diff_match_patch.Diff aDiff : diffs) {
            String text = aDiff.text.replace("&", "&amp;").replace("<", "&lt;")
                    .replace(">", "&gt;").replace("\n", "&para;<br>");
            switch (aDiff.operation) {
                case INSERT:
                    html.append("<ins style=\"background:#e6ffe6;\">").append(text)
                            .append("</ins>");
                    break;
                case DELETE:
                    html.append("<del style=\"background:#ffe6e6;\">").append(text)
                            .append("</del>");
                    break;
                case EQUAL:
                    html.append("<span>").append(text).append("</span>");
                    break;
            }
        }
        return html.toString();
    }
}
