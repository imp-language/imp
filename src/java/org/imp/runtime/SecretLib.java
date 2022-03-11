package org.imp.runtime;

public class SecretLib {

    public static void wait6502(int amount){
        for (int i = 0 ; i < amount ; i++){
            System.out.println("MICROSOFT!");
        }
    }
    
    public static void poke59458(int argument){
        if (argument == 62){
            System.out.println("THE KILLER POKE!");
        } else {
            System.out.println("I wonder what this does?");
        }
    }
}
