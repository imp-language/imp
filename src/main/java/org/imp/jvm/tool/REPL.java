package org.imp.jvm.tool;

import org.imp.jvm.domain.ImpFile;

import java.io.IOException;
import java.util.*;

import static com.diogonunes.jcolor.Ansi.*;
import static com.diogonunes.jcolor.Attribute.*;


/**
 * Todo: support highlighting by default in Windows
 * <p>
 * Github Issue https://github.com/dialex/JColor/issues/54
 * Stackoverflow Thread https://stackoverflow.com/questions/51680709/colored-text-output-in-powershell-console-using-ansi-vt100-codes/51681675#51681675
 * <p>
 * https://github.com/scala/scala/blob/2.13.x/src/repl/scala/tools/nsc/interpreter/IMain.scala
 */
public class REPL {
    public final String START_MESSAGE = "Welcome to Imp v0.0.1 on JVM";
    public final String HELP_MESSAGE = "Type `help` for more information.";
    private final String PROMPT = "> ";
    private final String INDENT = ".... ";

    private final Scanner s;

    private final List<String> statementLines = new ArrayList<>();

    public REPL() {
        s = new Scanner(System.in);
    }

    public void start() {
        System.out.println(colorize(START_MESSAGE, BLUE_TEXT()));
        System.out.println(colorize(HELP_MESSAGE, BLUE_TEXT()));

        while (true) {

            var impFile = read();
            Map<String, ImpFile> compilationSet = new HashMap<>();
            compilationSet.put(impFile.packageName, impFile);

            impFile.validate();

            try {
                var program = API.createProgram(compilationSet);
                Runner.run("repl.Entry");
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private ImpFile read() {
//        List<String> statementLines = new ArrayList<>();
        System.out.print(colorize(PROMPT, YELLOW_TEXT()));

        ImpFile impFile = null;

        do {
            String line = s.nextLine();
            statementLines.add(line);

            String joinedLines = String.join("\n", statementLines);
            try {
                impFile = parse(joinedLines);
                Object b = null;
            } catch (Exception e) {
                System.out.print(colorize(INDENT, YELLOW_TEXT()));
            }


        } while (impFile == null);


        return impFile;
    }


    private ImpFile parse(String line) {
        return API.createReplFile(line);
    }
}
