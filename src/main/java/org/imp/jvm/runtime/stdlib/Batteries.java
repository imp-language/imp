package org.imp.jvm.runtime.stdlib;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * The _ prefix is removed in Imp and only exists to allow
 * this Java class to compile.
 */
@SuppressWarnings("unused")
public class Batteries {

    public static void log(Object... args) {
        String result = Arrays.stream(args).map(Object::toString).collect(Collectors.joining(" "));
        System.out.println(result);
    }

    public static void log(Object arg) {
        System.out.println(arg);
    }

    public static void log() {
        System.out.println();
    }


    // Int convertors
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
        return Integer.parseInt(s);
    }

    public static int _int(double d) {
        return (int) d;
    }


    // Float convertors
    public static float _float(int i) {
        return (float) i;
    }

    public static float _float(String s) {
        return Float.parseFloat(s);
    }

    public static float _float(double d) {
        return (float) d;
    }

    // Double convertors
    public static double _double(int i) {
        return i;
    }

    public static double _double(float f) {
        return f;
    }

    public static double _double(String s) {
        return Double.parseDouble(s);
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
    
    /**
     * Reads the entire file to a string.
     * Very primitive implementation.
     *
     * @return String
     */
    public static String read(String filename) throws IOException {
        Path fileName = Path.of(filename);
        return Files.readString(fileName);
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


    public static int length(String s) {
        return s.length();
    }
}
