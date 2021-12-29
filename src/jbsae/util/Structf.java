package jbsae.util;

import jbsae.func.prim.*;

import java.lang.reflect.*;
import java.util.*;

import static jbsae.util.Mathf.*;

@SuppressWarnings("all")
public class Structf{
    public static final int prime1 = 0xbe1f14b1;
    public static final int prime2 = 0xb4b82e39;
    public static final int prime3 = 0xced1c241;


    /** Generic array creation. */
    public static <T> T[] create(int size){
        return (T[])new Object[size];
    }

    public static <T> T[] create(int size, T[] base){
        return (T[])(base.getClass() == Object[].class ? new Object[size] :
        Array.newInstance(base.getClass().getComponentType(), size));
    }


    /** Array copying. */
    public static <T> T[] copy(T[] arr){
        T[] newArr = create(arr.length);
        copy(arr, newArr);
        return newArr;
    }

    public static <T> void copy(T[] from, T[] to){
        copy(from, to, min(from.length, to.length));
    }

    public static <T> void copy(T[] from, T[] to, int size){
        copy(from, 0, size, to, 0);
    }

    public static <T> void copy(T[] from, int fromStart, int fromEnd, T[] to, int toStart){
        copy(from, fromStart, fromEnd, to, toStart, toStart + (fromEnd - fromStart));
    }

    public static <T> void copy(T[] from, int fromStart, int fromEnd, T[] to, int toStart, int toEnd){
        for(int i = 0;i < min(abs(fromEnd - fromStart), abs(toEnd - toStart));i++){
            int toIndex = toStart + (toStart < toEnd ? i : -i), fromIndex = fromStart + (fromStart < fromEnd ? i : -i);
            if(toIndex < 0 || toIndex >= to.length || fromIndex < 0 || fromIndex >= from.length) continue;
            to[toIndex] = from[fromIndex];
        }
    }


    public static float[] copy(float[] arr){
        float[] newArr = new float[arr.length];
        copy(arr, newArr);
        return newArr;
    }

    public static void copy(float[] from, float[] to){
        copy(from, to, min(from.length, to.length));
    }

    public static void copy(float[] from, float[] to, int size){
        copy(from, 0, size, to, 0);
    }

    public static void copy(float[] from, int fromStart, int fromEnd, float[] to, int toStart){
        copy(from, fromStart, fromEnd, to, toStart, toStart + (fromEnd - fromStart));
    }

    public static void copy(float[] from, int fromStart, int fromEnd, float[] to, int toStart, int toEnd){
        for(int i = 0;i < min(abs(fromEnd - fromStart), abs(toEnd - toStart));i++){
            int toIndex = toStart + (toStart < toEnd ? i : -i), fromIndex = fromStart + (fromStart < fromEnd ? i : -i);
            if(toIndex < 0 || toIndex >= to.length || fromIndex < 0 || fromIndex >= from.length) continue;
            to[toIndex] = from[fromIndex];
        }
    }


    public static boolean[] copy(boolean[] arr){
        boolean[] newArr = new boolean[arr.length];
        copy(arr, newArr);
        return newArr;
    }

    public static void copy(boolean[] from, boolean[] to){
        copy(from, to, min(from.length, to.length));
    }

    public static void copy(boolean[] from, boolean[] to, int size){
        copy(from, 0, size, to, 0);
    }

    public static void copy(boolean[] from, int fromStart, int fromEnd, boolean[] to, int toStart){
        copy(from, fromStart, fromEnd, to, toStart, toStart + (fromEnd - fromStart));
    }

    public static void copy(boolean[] from, int fromStart, int fromEnd, boolean[] to, int toStart, int toEnd){
        for(int i = 0;i < min(abs(fromEnd - fromStart), abs(toEnd - toStart));i++){
            int toIndex = toStart + (toStart < toEnd ? i : -i), fromIndex = fromStart + (fromStart < fromEnd ? i : -i);
            if(toIndex < 0 || toIndex >= to.length || fromIndex < 0 || fromIndex >= from.length) continue;
            to[toIndex] = from[fromIndex];
        }
    }


    public static int[] copy(int[] arr){
        int[] newArr = new int[arr.length];
        copy(arr, newArr);
        return newArr;
    }

    public static void copy(int[] from, int[] to){
        copy(from, to, min(from.length, to.length));
    }

    public static void copy(int[] from, int[] to, int size){
        copy(from, 0, size, to, 0);
    }

    public static void copy(int[] from, int fromStart, int fromEnd, int[] to, int toStart){
        copy(from, fromStart, fromEnd, to, toStart, toStart + (fromEnd - fromStart));
    }

    public static void copy(int[] from, int fromStart, int fromEnd, int[] to, int toStart, int toEnd){
        for(int i = 0;i < min(abs(fromEnd - fromStart), abs(toEnd - toStart));i++){
            int toIndex = toStart + (toStart < toEnd ? i : -i), fromIndex = fromStart + (fromStart < fromEnd ? i : -i);
            if(toIndex < 0 || toIndex >= to.length || fromIndex < 0 || fromIndex >= from.length) continue;
            to[toIndex] = from[fromIndex];
        }
    }

    /** Array shifting. */
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


    public static void shift(boolean[] arr, int amount){
        shift(arr, 0, arr.length, amount);
    }

    public static void shift(boolean[] arr, int start, int end, int amount){
        copy(arr, amount > 0 ? (end - 1) : start, amount > 0 ? (start - 1) : end, arr, amount > 0 ? (end + amount - 1) : (start + amount));
    }


    public static void shift(int[] arr, int amount){
        shift(arr, 0, arr.length, amount);
    }

    public static void shift(int[] arr, int start, int end, int amount){
        copy(arr, amount > 0 ? (end - 1) : start, amount > 0 ? (start - 1) : end, arr, amount > 0 ? (end + amount - 1) : (start + amount));
    }


    /** Array sorting. */
    public static <T> void sortArr(T[] arr){
        Arrays.sort(arr);
    }

    public static <T> void sortArr(T[] arr, Floatf<T> value){
        Arrays.sort(arr, new Comparator<T>(){
            public int compare(T a, T b){
                return (int)(value.get(a) - value.get(b));
            }
        });
    }

    public static void sortArr(float[] arr){
        Arrays.sort(arr);
    }

    public static void sortArr(int[] arr){
        Arrays.sort(arr);
    }

    /** Comparison functions. */
    public static <T> boolean eql(T a, T b){
        return (a != null && a.equals(b)) || a == b;
    }

    public static <T> boolean eql(T[] a, T[] b){
        if(a.length != b.length) return false;
        for(int i = 0;i < a.length;i++) if(!eql(a[i], b[i])) return false;
        return true;
    }

    public static <T> boolean eql(T[][] a, T[][] b){
        if(a.length != b.length) return false;
        for(int i = 0;i < a.length;i++){
            if(a[i].length != b[i].length) return false;
            for(int j = 0;j < a[i].length;j++) if(!eql(a[i][j], b[i][j])) return false;
        }
        return true;
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


    /** Hashing integers. */
    public static int[] hashes(int h, int l){
        return new int[]{hash(h, prime1, l), hash(h, prime2, l), hash(h, prime3, l)};
    }

    public static int hash(int h, int prime, int l){
        h *= prime;
        return (h ^ h >>> (31 - trailZeros(l))) & (l - 1);
    }
}
