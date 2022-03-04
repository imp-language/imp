package org.imp.runtime;

import java.util.Random;

public class Math {
    public static float rand(){
        Random rand = new Random();
        return rand.nextFloat();
    }

    public static float rand(int seed){
        Random rand = new Random(seed);
        return rand.nextFloat();
    }

    public static float rand(int start, int end){
        Random rand = new Random();
        return rand.nextInt(start, end);
    }

    public static float rand(int start, int end, int seed){
        Random rand = new Random(seed);
        return rand.nextInt(start, end);
    }

    public static float sqrt(float f){
        return (float) java.lang.Math.sqrt(f);
    }

    public static float sqrt(int i) {
        return (float) java.lang.Math.sqrt((float) i);
    }
}
