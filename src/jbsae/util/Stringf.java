package jbsae.util;

import jbsae.struct.prim.*;

public class Stringf{
    public static char lowerCase(char ch){
        return Character.toLowerCase(ch);
    }

    public static char upperCase(char ch){
        return Character.toUpperCase(ch);
    }

    public static String combine(char[] chars){
        return String.copyValueOf(chars);
    }

    public static String valToString(Object... arr){
        CharSeq str = (CharSeq)new CharSeq().add('(');
        for(Object o : arr) str.add(o.toString()).add(',');
        return str.substring(0, str.size - 1).add(')').toString();
    }

    public static String arrToString(Object... arr){
        CharSeq str = (CharSeq)new CharSeq().add('[');
        for(Object o : arr) str.add(o.toString()).add(", ");
        return str.substring(0, str.size - 2).add(']').toString();
    }

    public static <T> String itrToString(Iterable<T> arr){
        CharSeq str = (CharSeq)new CharSeq().add('[');
        for(T o : arr) str.add(o.toString()).add(", ");
        return str.substring(0, str.size - 2).add(']').toString();
    }
}
