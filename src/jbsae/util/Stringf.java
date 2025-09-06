package jbsae.util;

import jbsae.struct.prim.*;

import java.io.*;

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
        return str.size == 1 ? "[]" : str.substring(0, str.size - 2).add(']').toString();
    }

    public static String formatMillis(long time){
        return formatMillis(time, false);
    }

    public static String formatMillis(long time, boolean days){
        int ms = (int)(time % 1000);
        int s = (int)((time / 1000) % 60);
        int m = (int)((time / (1000 * 60)) % 60);
        int h = (int)(time / (1000 * 60 * 60));
        CharSeq result = new CharSeq();
        if(days){
            int d = h / 24;
            h = h % 24;
            result.add(d).add('d').add(' ');
        }
        if(h < 10) result.add('0');
        result.add(h).add('h').add(' ');
        if(m < 10) result.add('0');
        result.add(m).add('m').add(' ');
        if(s < 10) result.add('0');
        result.add(s).add('s').add(' ');
        if(ms < 10) result.add('0');
        if(ms < 100) result.add('0');
        result.add(ms).add('m').add('s');
        return result.toString();
    }

    public static String formatMillisCompact(long time){
        int ms = (int)(time % 1000);
        int s = (int)((time / 1000) % 60);
        int m = (int)((time / (1000 * 60)) % 60);
        int h = (int)(time / (1000 * 60 * 60));
        CharSeq result = new CharSeq();
        if(h < 10) result.add('0');
        result.add(h).add(':');
        if(m < 10) result.add('0');
        result.add(m).add(':');
        if(s < 10) result.add('0');
        result.add(s).add('.');
        if(ms < 10) result.add('0');
        if(ms < 100) result.add('0');
        result.add(ms);
        return result.toString();
    }

    public static String getStackTrace(Throwable error){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        error.printStackTrace(pw);
        return sw.toString();
    }
}
