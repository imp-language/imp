package org.imp.jvm.runtime.stdlib;

/**
 * The _ prefix is removed in Imp and only exists to allow
 * this Java class to compile.
 */
/** Please don't put this class in any documentation(I want to keep it kinda obscure). I mainly just made this cause I was bored
 *  Maybe I could obfuscate the code for this, just to make it more of a secret
 *
 */

@SuppressWarnings("unused")
public class EasterEggs {
  
    public static void wait6502(int amount) {   //this is a reference to the easter egg mentioned in this article: https://www.pagetable.com/?p=43
        for (int i = 0 ; i < amount ; i++){
            System.out.println("MICROSFT!");
        }
    }
    
    public static void poke59458(int operand){  //this is a reference to the "killer poke", a command you could do on the commodore PET that would throw the crt controller out of whack
        if (operand == 62){
            System.out.println("Congrats! You just ruined a perfectly good commodore PET");
        }
    }
}
