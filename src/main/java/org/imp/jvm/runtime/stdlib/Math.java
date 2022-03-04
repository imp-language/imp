package org.imp.jvm.runtime.stdlib;

import java.util.Random;

/**
 * The _ prefix is removed in Imp and only exists to allow
 * this Java class to compile.
 */
@SuppressWarnings("unused")
public class Math {
    public static float rand() {
        Random rand = new Random();
        return rand.nextFloat();
    }
    
    public static float rand(int seed) {
        Random rand = new Random(seed);
        return rand.nextFloat();
    }

    public static float rand(int start, int end) {
        Random rand = new Random();
        return rand.nextInt(start, end);
    }
    
    public static float rand(int start, int end, int seed){
        Random rand = new Random(seed);
        return rand.nextInt(start, end);
    }

    public static float sqrt(float f) {
        return (float) java.lang.Math.sqrt(f);
    }

    public static float sqrt(int i) {
        return (float) java.lang.Math.sqrt((float) i);
    }

    public static int ceil(float f) {
        return (int) java.lang.Math.ceil(f);
    }

    public static int floor(float f) {
        return (int) java.lang.Math.floor(f);
    }

    public static float round(float f, int places) {
        float multiple = 10;
        for (int i = 0; i < places; i++) {
            multiple *= 10;
        }
        return java.lang.Math.round(f * multiple) / multiple;
    }

    public static float abs(float f) {
        return java.lang.Math.abs(f);
    }

    public static int abs(int i) {
        return java.lang.Math.abs(i);
    }

    // Todo: BigInteger support, the largest factorial calculated by this function is 12!
    public static int factorial(int n) {
        int fact = 1;
        for (int i = 2; i <= n; i++) {
            fact = fact * i;
        }
        return fact;
    }

    public static float exp(int x) {
        return (float) java.lang.Math.exp(x);
    }

    public static float exp(float f) {
        return (float) java.lang.Math.exp(f);
    }

    public static boolean similar(float a, float b, float EPSILON) {
        return (a >= b - EPSILON) && (a <= b + EPSILON);
    }

    public static boolean similar(float a, float b) {
        return similar(a, b, 0.0001f);
    }
    
    public static boolean multiple(int a, int b){
        return a % b == 0;
    }
    
    public static boolean even(int a){
        return a % 2 == 0;
    }
    
    public static boolean odd(int a){
        return a % 2 != 0;
    }
}
