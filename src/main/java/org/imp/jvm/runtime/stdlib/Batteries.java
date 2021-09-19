package org.imp.jvm.runtime.stdlib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * The _ prefix is removed in Imp and only exists to allow
 * this Java class to compile.
 */
public class Batteries {

    public static void log(Object... args) {
        String result = Arrays.stream(args).map(Object::toString).collect(Collectors.joining(" "));
        System.out.println(result);
    }

    public static void log_single(Object arg) {
        System.out.println(arg);
    }

//    public static void log_none() {
//        System.out.println();
//    }

    public static int _int(float f) {
        return (int) f;
    }

    public static int _int(boolean b) {
        if (b) {
            return 1;
        } else {
            return 0;
        }
    }

    public static int _int(String s) {
        int i = Integer.parseInt(s);
        return i;
    }

    public static float _float(int i) {
        return (float) i;
    }

    public static float _float(String s) {
        return Float.parseFloat(s);
    }

    public static String string(int i) {
        return i + "";
    }

    public static String string(float f) {
        return f + "";
    }

    public static String string(boolean b) {
        return b + "";
    }

    // Todo: not working yet
    public static String typeof(Object o) {
        return o.getClass().getName();
    }

    /**
     * Reads the entire file to a string.
     * Very primitive implementation.
     *
     * @return String
     */
    public static String read(String filename) throws IOException {
        Path fileName = Path.of(filename);
        String content = Files.readString(fileName);
        return content;
    }

    public static String read() throws IOException {
        Scanner in = new Scanner(System.in);
        String s = in.nextLine();
        in.close();
        return s;
    }

    /**
     * Writes content to a file.
     * Very primitive implementation.
     */
    public static void write(String filename, String content) throws IOException {
        Path fileName = Path.of(filename);
        Files.writeString(fileName, content);
    }
}
