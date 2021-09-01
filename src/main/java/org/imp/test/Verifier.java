package org.imp.test;

import name.fraser.neil.plaintext.diff_match_patch;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.imp.jvm.tool.Imp;
import org.imp.jvm.tool.ImpAPI;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Verifier {

    public static final String verificationPath = "verification";

    public static final List<String> flawsList = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        System.out.println("Imp language tests.");

        File path = new File(verificationPath);

        var pathnames = path.list();

        long startTime = System.currentTimeMillis();

        int tests = 0;

        // For each pathname in the pathnames array
        for (String pathname : pathnames) {
            // Print the names of files and directories
            String ext = FilenameUtils.getExtension(pathname);
            if (ext.equals("imp")) {
                validate(pathname);
                tests++;

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
        float seconds = duration / 1000f;
        System.out.printf("Finished %d tests in %.3f s.", tests, seconds);
    }

    private static void validate(String name) throws IOException {
        System.out.println("### File: " + name + " ###");
        System.out.println("--------------------------------------------");

        String sourcePath = FilenameUtils.concat(verificationPath, name);
        String solutionPath = FilenameUtils.concat(verificationPath, FilenameUtils.getBaseName(name) + ".txt");


        // 0. Load result file line-by-line
        List<String> solutionLines = FileUtils.readLines(new File(solutionPath), StandardCharsets.UTF_8);

        // 1. Compile imp source file
        var imp = new Imp(sourcePath);
        String classFilePath = imp.compile();

        // 2. Execute compiled imp file
        Process proc = ImpAPI.spawn(classFilePath);


        // 3. Watch standard out
        BufferedReader stdInput = new BufferedReader(new
                InputStreamReader(proc.getInputStream()));

        BufferedReader stdError = new BufferedReader(new
                InputStreamReader(proc.getErrorStream()));

        List<String> stdOutLines = new ArrayList<>();
        String s = null;
        while ((s = stdInput.readLine()) != null) {
            stdOutLines.add(s);
        }

        while ((s = stdError.readLine()) != null) {
            System.out.println(s);
        }
        System.out.println("");

        // 4. Compare results
        if (solutionLines.size() > stdOutLines.size()) {
            flawsList.add(name);
            System.out.println("Verifier: Solution output is longer than execution output.");
            System.out.println();
            return;
        }
        int flaws = 0;
        for (int i = 0; i < stdOutLines.size(); i++) {
            String solution = solutionLines.get(i);
            String stdOut = stdOutLines.get(i);

            if (solution.equals(stdOut)) {
                System.out.println(solution);

            } else {
                System.out.println(stdOut + " !! " + solution);
                flaws++;
            }

        }

        stdError.close();
        stdInput.close();

        if (flaws > 0) {
            flawsList.add(name);
        }
        System.out.println("--------------------------------------------");
        System.out.println(flaws + " flaws found in " + name + ".");
        System.out.println();


    }

    private static String diff_prettyStrikethrough(List<diff_match_patch.Diff> diffs) {
        StringBuilder html = new StringBuilder();
        for (diff_match_patch.Diff aDiff : diffs) {
            String text = aDiff.text.replace("&", "&amp;").replace("<", "&lt;")
                    .replace(">", "&gt;").replace("\n", "&para;<br>");
            switch (aDiff.operation) {
                case INSERT -> html.append("<ins style=\"background:#e6ffe6;\">").append(text)
                        .append("</ins>");
                case DELETE -> html.append("<del style=\"background:#ffe6e6;\">").append(text)
                        .append("</del>");
                case EQUAL -> html.append("<span>").append(text).append("</span>");
            }
        }
        return html.toString();
    }
}
