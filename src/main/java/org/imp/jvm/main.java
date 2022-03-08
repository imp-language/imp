package org.imp.jvm;

import org.imp.jvm.lib.date;
import org.imp.jvm.runtime.stdlib.Batteries;

public class main {
    public static void main(String[] var0) {
        var d = date.instance.new Date(1, 2, 3);
//        d.day = 4;
//        System.out.println(d);
        Batteries.log(d);
    }
}
