package jbsae.util;

import java.io.*;
import java.util.*;

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

    public static String valToString(Object... values){
        StringBuilder str = new StringBuilder(values.length * 2).append('(');
        for(Object o : values) str.append(o.toString()).append(',');
        return str.deleteCharAt(str.length() - 1).append(')').toString();
    }

    public static String arrToString(Object... arr){
        StringBuilder str = new StringBuilder(arr.length * 2).append('[');
        for(Object o : arr) str.append(o.toString()).append(", ");
        return str.deleteCharAt(str.length() - 1).append(']').toString();
    }

    public static <T> String itrToString(Iterator<T> itr){
        StringBuilder str = new StringBuilder().append('[');
        while(itr.hasNext()) str.append(itr.next().toString()).append(", ");
        return str.deleteCharAt(str.length() - 1).append(']').toString();
    }

    public static String formatMillis(long time){
        return formatMillis(time, false);
    }

    public static String formatMillis(long time, boolean days){
        int ms = (int)(time % 1000);
        int s = (int)((time / 1000) % 60);
        int m = (int)((time / (1000 * 60)) % 60);
        int h = (int)(time / (1000 * 60 * 60));
        StringBuilder result = new StringBuilder(32);
        if(days){
            int d = h / 24;
            h = h % 24;
            result.append(d).append('d').append(' ');
        }
        if(h < 10) result.append('0');
        result.append(h).append('h').append(' ');
        if(m < 10) result.append('0');
        result.append(m).append('m').append(' ');
        if(s < 10) result.append('0');
        result.append(s).append('s').append(' ');
        if(ms < 10) result.append('0');
        if(ms < 100) result.append('0');
        result.append(ms).append('m').append('s');
        return result.toString();
    }

    public static String formatMillisCompact(long time){
        int ms = (int)(time % 1000);
        int s = (int)((time / 1000) % 60);
        int m = (int)((time / (1000 * 60)) % 60);
        int h = (int)(time / (1000 * 60 * 60));
        StringBuilder result = new StringBuilder(16);
        if(h < 10) result.append('0');
        result.append(h).append(':');
        if(m < 10) result.append('0');
        result.append(m).append(':');
        if(s < 10) result.append('0');
        result.append(s).append('.');
        if(ms < 10) result.append('0');
        if(ms < 100) result.append('0');
        result.append(ms);
        return result.toString();
    }

    public static String getStackTrace(Throwable error){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        error.printStackTrace(pw);
        return sw.toString();
    }
}
