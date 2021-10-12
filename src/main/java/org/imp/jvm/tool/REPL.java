package org.imp.jvm.tool;

import java.util.Scanner;

import static com.diogonunes.jcolor.Ansi.*;
import static com.diogonunes.jcolor.Attribute.*;


/**
 * Todo: support highlighting by default in Windows
 *
 * @see issue https://github.com/dialex/JColor/issues/54
 * @see thread https://stackoverflow.com/questions/51680709/colored-text-output-in-powershell-console-using-ansi-vt100-codes/51681675#51681675
 */
public class REPL {
    public final String START_MESSAGE = "Welcome to Imp v0.0.1 on JVM";
    public final String HELP_MESSAGE = "Type `help` for more information.";
    private final String PROMPT = "> ";

    private final Scanner s;

    public REPL() {
        s = new Scanner(System.in);
    }

    public void start() {
        System.out.println(colorize(START_MESSAGE, BLUE_TEXT()));
        System.out.println(colorize(HELP_MESSAGE, BLUE_TEXT()));

        while (true) {

            next();

        }

    }

    private String next() {
        System.out.print(colorize(PROMPT, YELLOW_TEXT()));
        String line = s.nextLine();
        return line;
    }
}
