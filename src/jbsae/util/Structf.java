package jbsae.util;

import jbsae.*;
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


    /** Array filling. */
    public static <T> void fill(T[] arr, T value){
        fill(arr, value, 0, arr.length);
    }

    public static <T> void fill(T[] arr, T value, int start, int end){
        for(int i = start;i < end;i++) arr[i] = value;
    }


    public static void fill(float[] arr, float value){
        fill(arr, value, 0, arr.length);
    }

    public static void fill(float[] arr, float value, int start, int end){
        for(int i = start;i < end;i++) arr[i] = value;
    }


    public static void fill(int[] arr, int value){
        fill(arr, value, 0, arr.length);
    }

    public static void fill(int[] arr, int value, int start, int end){
        for(int i = start;i < end;i++) arr[i] = value;
    }


    public static void fill(boolean[] arr, boolean value){
        fill(arr, value, 0, arr.length);
    }

    public static void fill(boolean[] arr, boolean value, int start, int end){
        for(int i = start;i < end;i++) arr[i] = value;
    }


    /** Array copying. */
    public static <T> T[] copy(T[] arr){
        T[] res = create(arr.length);
        copy(arr, res);
        return res;
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
        float[] res = new float[arr.length];
        copy(arr, res);
        return res;
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
        boolean[] res = new boolean[arr.length];
        copy(arr, res);
        return res;
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
        int[] res = new int[arr.length];
        copy(arr, res);
        return res;
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

    public static boolean eqlc(boolean[] a, boolean[] b){
        for(int i = 0;i < a.length;i++) if(!contains(b, a[i])) return false;
        for(int i = 0;i < b.length;i++) if(!contains(a, b[i])) return false;
        return true;
    }

    public static boolean eqlc(int[] a, int[] b){
        for(int i = 0;i < a.length;i++) if(!contains(b, a[i])) return false;
        for(int i = 0;i < b.length;i++) if(!contains(a, b[i])) return false;
        return true;
    }


    /** Utility methods. */
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

    public static boolean contains(boolean[] arr, boolean value){
        for(int i = 0;i < arr.length;i++) if(arr[i] == value) return true;
        return false;
    }

    public static boolean contains(int[] arr, int value){
        for(int i = 0;i < arr.length;i++) if(arr[i] == value) return true;
        return false;
    }


    /** Object array conversions (Should not be needed with JBSAE). */
    public static float[] objf(Object... arr){
        float[] res = new float[arr.length];
        for(int i = 0;i < arr.length;i++) res[i] = (Float)arr[i];
        return res;
    }

    public static boolean[] objb(Object... arr){
        boolean[] res = new boolean[arr.length];
        for(int i = 0;i < arr.length;i++) res[i] = (Boolean)arr[i];
        return res;
    }

    public static int[] obji(Object... arr){
        int[] res = new int[arr.length];
        for(int i = 0;i < arr.length;i++) res[i] = (Integer)arr[i];
        return res;
    }


    /** Hashing integers. */
    public static int[] hash3(int h, int l){
        return hash3(h, l, new int[3]);
    }

    public static int[] hash3(int h, int l, int[] arr){
        arr[0] = hash(h, prime1, l);
        arr[1] = hash(h, prime2, l);
        arr[2] = hash(h, prime3, l);
        return arr;
    }

    public static int hash(int h, int prime, int l){
        h *= prime;
        return (h ^ h >>> (31 - trailZeros(l))) & (l - 1);
    }
}
