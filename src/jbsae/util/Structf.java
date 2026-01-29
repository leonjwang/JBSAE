package jbsae.util;

import jbsae.func.*;
import jbsae.func.prim.*;
import jbsae.struct.List;

import java.lang.reflect.*;
import java.util.*;

import static jbsae.util.Mathf.*;


@SuppressWarnings("all")
public class Structf{
    public static final int PRIME1 = 0xbe1f14b1;
    public static final int PRIME2 = 0xb4b82e39;
    public static final int PRIME3 = 0xced1c241;

    public static int hash(int h, int prime){
        h *= prime;
        return h ^ h;
    }

    public static int hash(int h, int prime, int n){
        return (hash(h, prime) >>> (31 - trailZeros(n))) & (n - 1);
    }


    public static <T> T[] create(int size){
        return (T[])new Object[size];
    }

    public static <T> T[] create(int size, T[] base){
        return (T[])(base.getClass() == Object[].class ? new Object[size] :
        Array.newInstance(base.getClass().getComponentType(), size));
    }


    public static <T> void fill(T[] arr, T value){
        fill(arr, 0, arr.length, value);
    }

    public static <T> void fill(T[] arr, int start, int end, T value){
        for(int i = start;i < end;i++) arr[i] = value;
    }

    public static <T> void fill(T[][] arr, T value){
        for(int i = 0;i < arr.length;i++) fill(arr[i], value);
    }

    public static <T> void fill(List<T> arr, T value){
        fill(arr, 0, arr.size(), value);
    }

    public static <T> void fill(List<T> arr, int start, int end, T value){
        for(int i = start;i < end;i++) arr.set(i, value);
    }

    public static void fill(float[] arr, float value){
        fill(arr, 0, arr.length, value);
    }

    public static void fill(float[] arr, int start, int end, float value){
        for(int i = start;i < end;i++) arr[i] = value;
    }

    public static void fill(float[][] arr, float value){
        for(int i = 0;i < arr.length;i++) fill(arr[i], value);
    }

    public static void fill(int[] arr, int value){
        fill(arr, 0, arr.length, value);
    }

    public static void fill(int[] arr, int start, int end, int value){
        for(int i = start;i < end;i++) arr[i] = value;
    }

    public static void fill(int[][] arr, int value){
        for(int i = 0;i < arr.length;i++) fill(arr[i], value);
    }


    public static <T> void each(T[] arr, Cons<T> cons){
        each(arr, 0, arr.length, cons);
    }

    public static <T> void each(T[] arr, int start, int end, Cons<T> cons){
        for(int i = start;i < end;i++) cons.get(arr[i]);
    }

    public static <T> void each(T[][] arr, Cons<T> cons){
        for(int i = 0;i < arr.length;i++) each(arr[i], cons);
    }

    public static void each(float[] arr, Floatc cons){
        each(arr, 0, arr.length, cons);
    }

    public static void each(float[] arr, int start, int end, Floatc cons){
        for(int i = start;i < end;i++) cons.get(arr[i]);
    }

    public static void each(float[][] arr, Floatc cons){
        for(int i = 0;i < arr.length;i++) each(arr[i], cons);
    }

    public static void each(int[] arr, Intc cons){
        each(arr, 0, arr.length, cons);
    }

    public static void each(int[] arr, int start, int end, Intc cons){
        for(int i = start;i < end;i++) cons.get(arr[i]);
    }

    public static void each(int[][] arr, Intc cons){
        for(int i = 0;i < arr.length;i++) each(arr[i], cons);
    }


    public static <T> T[] copy(T[] arr){
        T[] res = create(arr.length);
        copy(arr, res);
        return res;
    }

    public static <T> void copy(T[] from, T[] to){
        copy(from, to, min(from.length, to.length));
    }

    public static <T> void copy(T[] from, T[] to, int size){
        if(size > from.length || size > to.length) throw new ArrayIndexOutOfBoundsException("Invalid size: " + size);
        System.arraycopy(from, 0, to, 0, size);
    }

    public static <T> void copy(T[] from, int fromStart, int fromEnd, T[] to, int toStart){
        copy(from, fromStart, fromEnd, to, toStart, toStart + (fromEnd - fromStart));
    }

    public static <T> void copy(T[] from, int fromStart, int fromEnd, T[] to, int toStart, int toEnd){
        int fromDir = Integer.compare(fromEnd, fromStart);
        int toDir = Integer.compare(toEnd, toStart);

        if(fromDir == 0 || toDir == 0) return;

        int fromLength = abs(fromEnd - fromStart);
        int toLength = abs(toEnd - toStart);
        int copyLength = min(fromLength, toLength);

        int fromMin = min(fromStart, fromEnd);
        int fromMax = max(fromStart, fromEnd);
        int toMin = min(toStart, toEnd);
        int toMax = max(toStart, toEnd);

        if(fromMin < 0 || fromMax > from.length || toMin < 0 || toMax > to.length){
            throw new ArrayIndexOutOfBoundsException(
            String.format("Invalid range: from[%d:%d] len=%d, to[%d:%d] len=%d",
            fromStart, fromEnd, from.length, toStart, toEnd, to.length));
        }

        if(fromDir == toDir){
            int srcPos = fromDir > 0 ? fromStart : fromEnd;
            int destPos = toDir > 0 ? toStart : toEnd;
            System.arraycopy(from, srcPos, to, destPos, copyLength);
        }else{
            for(int i = 0;i < copyLength;i++){
                int fromIdx = fromDir > 0 ? (fromStart + i) : (fromStart - i);
                int toIdx = toDir > 0 ? (toStart + i) : (toStart - i);
                to[toIdx] = from[fromIdx];
            }
        }
    }


    public static float[] copy(float[] arr){
        float[] res = new float[arr.length];
        copy(arr, res);
        return res;
    }

    public static void copy(float[] from, float[] to){
        copy(from, to, min(from.length, to.length));
    }

    public static void copy(float[] from, float[] to, int size){
        if(size > from.length || size > to.length) throw new ArrayIndexOutOfBoundsException("Invalid size: " + size);
        System.arraycopy(from, 0, to, 0, size);
    }

    public static void copy(float[] from, int fromStart, int fromEnd, float[] to, int toStart){
        copy(from, fromStart, fromEnd, to, toStart, toStart + (fromEnd - fromStart));
    }

    public static void copy(float[] from, int fromStart, int fromEnd, float[] to, int toStart, int toEnd){
        int fromDir = Integer.compare(fromEnd, fromStart);
        int toDir = Integer.compare(toEnd, toStart);

        if(fromDir == 0 || toDir == 0) return;

        int fromLength = abs(fromEnd - fromStart);
        int toLength = abs(toEnd - toStart);
        int copyLength = min(fromLength, toLength);

        int fromMin = min(fromStart, fromEnd);
        int fromMax = max(fromStart, fromEnd);
        int toMin = min(toStart, toEnd);
        int toMax = max(toStart, toEnd);

        if(fromMin < 0 || fromMax > from.length || toMin < 0 || toMax > to.length){
            throw new ArrayIndexOutOfBoundsException(
            String.format("Invalid range: from[%d:%d] len=%d, to[%d:%d] len=%d",
            fromStart, fromEnd, from.length, toStart, toEnd, to.length));
        }

        if(fromDir == toDir){
            int srcPos = fromDir > 0 ? fromStart : fromEnd;
            int destPos = toDir > 0 ? toStart : toEnd;
            System.arraycopy(from, srcPos, to, destPos, copyLength);
        }else{
            for(int i = 0;i < copyLength;i++){
                int fromIdx = fromDir > 0 ? (fromStart + i) : (fromStart - i);
                int toIdx = toDir > 0 ? (toStart + i) : (toStart - i);
                to[toIdx] = from[fromIdx];
            }
        }
    }


    public static int[] copy(int[] arr){
        int[] res = new int[arr.length];
        copy(arr, res);
        return res;
    }

    public static void copy(int[] from, int[] to){
        copy(from, to, min(from.length, to.length));
    }

    public static void copy(int[] from, int[] to, int size){
        if(size > from.length || size > to.length) throw new ArrayIndexOutOfBoundsException("Invalid size: " + size);
        System.arraycopy(from, 0, to, 0, size);
    }

    public static void copy(int[] from, int fromStart, int fromEnd, int[] to, int toStart){
        copy(from, fromStart, fromEnd, to, toStart, toStart + (fromEnd - fromStart));
    }

    public static void copy(int[] from, int fromStart, int fromEnd, int[] to, int toStart, int toEnd){
        int fromDir = Integer.compare(fromEnd, fromStart);
        int toDir = Integer.compare(toEnd, toStart);

        if(fromDir == 0 || toDir == 0) return;

        int fromLength = abs(fromEnd - fromStart);
        int toLength = abs(toEnd - toStart);
        int copyLength = min(fromLength, toLength);

        int fromMin = min(fromStart, fromEnd);
        int fromMax = max(fromStart, fromEnd);
        int toMin = min(toStart, toEnd);
        int toMax = max(toStart, toEnd);

        if(fromMin < 0 || fromMax > from.length || toMin < 0 || toMax > to.length){
            throw new ArrayIndexOutOfBoundsException(
            String.format("Invalid range: from[%d:%d] len=%d, to[%d:%d] len=%d",
            fromStart, fromEnd, from.length, toStart, toEnd, to.length));
        }

        if(fromDir == toDir){
            int srcPos = fromDir > 0 ? fromStart : fromEnd;
            int destPos = toDir > 0 ? toStart : toEnd;
            System.arraycopy(from, srcPos, to, destPos, copyLength);
        }else{
            for(int i = 0;i < copyLength;i++){
                int fromIdx = fromDir > 0 ? (fromStart + i) : (fromStart - i);
                int toIdx = toDir > 0 ? (toStart + i) : (toStart - i);
                to[toIdx] = from[fromIdx];
            }
        }
    }


    public static <T> void shift(T[] arr, int amount){
        shift(arr, 0, arr.length, amount);
    }

    public static <T> void shift(T[] arr, int start, int end, int amount){
        copy(arr, amount > 0 ? (end - 1) : start, amount > 0 ? (start - 1) : end, arr, amount > 0 ? (end + amount - 1) : (start + amount));
    }


    public static void shift(float[] arr, int amount){
        shift(arr, 0, arr.length, amount);
    }

    public static void shift(float[] arr, int start, int end, int amount){
        copy(arr, amount > 0 ? (end - 1) : start, amount > 0 ? (start - 1) : end, arr, amount > 0 ? (end + amount - 1) : (start + amount));
    }


    public static void shift(int[] arr, int amount){
        shift(arr, 0, arr.length, amount);
    }

    public static void shift(int[] arr, int start, int end, int amount){
        copy(arr, amount > 0 ? (end - 1) : start, amount > 0 ? (start - 1) : end, arr, amount > 0 ? (end + amount - 1) : (start + amount));
    }


    public static <T> void sortArr(T[] arr, Floatf<T> value){
        sortArr(arr, 0, arr.length, value);
    }

    public static <T> void sortArr(T[] arr, int start, int end, Floatf<T> value){
        Arrays.sort(arr, start, end, new Comparator<T>(){
            public int compare(T a, T b){
                return value.get(a) > value.get(b) ? 1 : -1;
            }
        });
    }


    public static boolean eql(Object a, Object b){
        return (a != null && a.equals(b)) || a == b;
    }

    public static boolean eql(Object[] a, Object[] b){
        if(a.length != b.length) return false;
        for(int i = 0;i < a.length;i++) if(!eql(a[i], b[i])) return false;
        return true;
    }

    public static boolean eql(Object[][] a, Object[][] b){
        if(a.length != b.length) return false;
        for(int i = 0;i < a.length;i++){
            if(a[i].length != b[i].length) return false;
            for(int j = 0;j < a[i].length;j++) if(!eql(a[i][j], b[i][j])) return false;
        }
        return true;
    }


    public static <T> boolean eql(Iterable<T> a, Iterable<T> b){
        Iterator<T> bi = b.iterator();
        for(T t : a) if(!eql(t, bi.next())) return false;
        return !bi.hasNext();
    }


    public static boolean eql(float[] a, float[] b){
        if(a.length != b.length) return false;
        for(int i = 0;i < a.length;i++) if(!eqlf(a[i], b[i])) return false;
        return true;
    }

    public static boolean eql(float[][] a, float[][] b){
        if(a.length != b.length) return false;
        for(int i = 0;i < a.length;i++){
            if(a[i].length != b[i].length) return false;
            for(int j = 0;j < a[i].length;j++) if(!eqlf(a[i][j], b[i][j])) return false;
        }
        return true;
    }


    public static boolean eql(int[] a, int[] b){
        if(a.length != b.length) return false;
        for(int i = 0;i < a.length;i++) if(a[i] != b[i]) return false;
        return true;
    }

    public static boolean eql(int[][] a, int[][] b){
        if(a.length != b.length) return false;
        for(int i = 0;i < a.length;i++){
            if(a[i].length != b[i].length) return false;
            for(int j = 0;j < a[i].length;j++) if(a[i][j] != b[i][j]) return false;
        }
        return true;
    }


    public static boolean eql(boolean[] a, boolean[] b){
        if(a.length != b.length) return false;
        for(int i = 0;i < a.length;i++) if(a[i] != b[i]) return false;
        return true;
    }

    public static boolean eql(boolean[][] a, boolean[][] b){
        if(a.length != b.length) return false;
        for(int i = 0;i < a.length;i++){
            if(a[i].length != b[i].length) return false;
            for(int j = 0;j < a[i].length;j++) if(a[i][j] != b[i][j]) return false;
        }
        return true;
    }


    public static boolean eql(char[] a, char[] b){
        if(a.length != b.length) return false;
        for(int i = 0;i < a.length;i++) if(a[i] != b[i]) return false;
        return true;
    }

    public static boolean eql(char[][] a, char[][] b){
        if(a.length != b.length) return false;
        for(int i = 0;i < a.length;i++){
            if(a[i].length != b[i].length) return false;
            for(int j = 0;j < a[i].length;j++) if(a[i][j] != b[i][j]) return false;
        }
        return true;
    }


    public static boolean eqlc(Object[] a, Object[] b){
        for(int i = 0;i < a.length;i++) if(!contains(b, a[i])) return false;
        for(int i = 0;i < b.length;i++) if(!contains(a, b[i])) return false;
        return true;
    }

    public static <T> boolean eqlc(Iterable<T> a, Iterable<T> b){
        for(T t : a) if(!contains(b, t)) return false;
        for(T t : b) if(!contains(a, t)) return false;
        return true;
    }

    public static boolean eqlc(float[] a, float[] b){
        for(int i = 0;i < a.length;i++) if(!contains(b, a[i])) return false;
        for(int i = 0;i < b.length;i++) if(!contains(a, b[i])) return false;
        return true;
    }

    public static boolean eqlc(int[] a, int[] b){
        for(int i = 0;i < a.length;i++) if(!contains(b, a[i])) return false;
        for(int i = 0;i < b.length;i++) if(!contains(a, b[i])) return false;
        return true;
    }


    public static boolean contains(Object[] arr, Object value){
        for(int i = 0;i < arr.length;i++) if(eql(arr[i], value)) return true;
        return false;
    }

    public static <T> boolean contains(Iterable<T> arr, T value){
        for(T t : arr) if(eql(t, value)) return true;
        return false;
    }

    public static boolean contains(float[] arr, float value){
        for(int i = 0;i < arr.length;i++) if(eqlf(arr[i], value)) return true;
        return false;
    }

    public static boolean contains(int[] arr, int value){
        for(int i = 0;i < arr.length;i++) if(arr[i] == value) return true;
        return false;
    }


    public static Object choose(Object[] arr){
        return arr[randInt(0, arr.length - 1)];
    }

    public static <T> Object choose(List<T> arr){
        return arr.get(randInt(0, arr.size() - 1));
    }

    public static float choose(float[] arr){
        return arr[randInt(0, arr.length - 1)];
    }

    public static int choose(int[] arr){
        return arr[randInt(0, arr.length - 1)];
    }


    public static float[] objf(Object... arr){
        float[] res = new float[arr.length];
        for(int i = 0;i < arr.length;i++) res[i] = (Float)arr[i];
        return res;
    }

    public static int[] obji(Object... arr){
        int[] res = new int[arr.length];
        for(int i = 0;i < arr.length;i++) res[i] = (Integer)arr[i];
        return res;
    }

    public static boolean[] objb(Object... arr){
        boolean[] res = new boolean[arr.length];
        for(int i = 0;i < arr.length;i++) res[i] = (Boolean)arr[i];
        return res;
    }

    public static char[] objc(Object... arr){
        char[] res = new char[arr.length];
        for(int i = 0;i < arr.length;i++) res[i] = (Character)arr[i];
        return res;
    }
}
