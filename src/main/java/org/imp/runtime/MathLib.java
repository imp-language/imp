package org.imp.runtime;

public class MathLib {
    
    public static final int intMax = 2147483647;
    public static final int intMin = -2147483648;
    
    //I am fully aware that there is currently no long datatype, this is simply here so that i dont forget to add the ranges if we do add longs
    public static final long longMax = 9223372036854775807;
    public static final long longMin = -9223372036854775808;

    public static int mod(int a, int b){
        return a % b;
    }
}
