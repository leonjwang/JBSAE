package jbsae.util;

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
}
