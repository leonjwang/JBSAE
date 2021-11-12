package jbsae.util;

import static jbsae.JBSAE.*;

public class Stringf{
    public static void print(Object o){
        System.out.println(o.toString());
    }

    public static void printDebug(Object o){
        if(debug) print(o);
    }
}
