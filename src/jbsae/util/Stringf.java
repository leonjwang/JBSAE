package jbsae.util;

import static jbsae.JBSAE.*;

public class Stringf{
    public static char lowerCase(char ch){
        return Character.toLowerCase(ch);
    }

    public static char upperCase(char ch){
        return Character.toUpperCase(ch);
    }

    public static String arrToString(char[] chars){
        return String.copyValueOf(chars);
    }

    public static void print(Object o){
        System.out.println(o.toString());
    }

    public static void printDebug(Object o){
        if(debug) print(o);
    }
}
