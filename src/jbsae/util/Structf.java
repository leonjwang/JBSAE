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

    @Deprecated
    public static int[] hash3(int hash, int n){
        int[] arr = new int[3];
        arr[0] = hash % n;
        arr[1] = hashn(hash, PRIME1, n);
        arr[2] = hashn(hash, PRIME2, n);
        return arr;
    }

    public static int hash(int h, int shift, int prime){
        h *= prime;
        return h ^ (h >>> shift);
    }

    public static int hashn(int h, int prime, int n){
        h *= prime;
        return (h ^ h >>> (31 - trailZeros(n))) & (n - 1);
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

    public static void fill(int[][] arr, char value){
        for(int i = 0;i < arr.length;i++) fill(arr[i], value);
    }

    public static void fill(char[] arr, char value){
        fill(arr, 0, arr.length, value);
    }

    public static void fill(char[] arr, int start, int end, char value){
        for(int i = start;i < end;i++) arr[i] = value;
    }

    public static void fill(char[][] arr, char value){
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

    // REWRITE THESE COPIES

    public static <T> T[] copy(T[] arr){
        T[] res = create(arr.length, arr);
        copy(arr, res);
        return res;
    }

    public static <T> void copy(T[] from, T[] to){
        copy(from, to, min(from.length, to.length));
    }

    public static <T> void copy(T[] from, T[] to, int size){
        copy(from, 0, to, 0, size);
    }

    public static <T> void copy(T[] from, int fromStart, T[] to, int toStart, int size){
        System.arraycopy(from, fromStart, to, toStart, size);
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
        copy(from, 0, to, 0, size);
    }

    public static void copy(float[] from, int fromStart, float[] to, int toStart, int size){
        System.arraycopy(from, fromStart, to, toStart, size);
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
        copy(from, 0, to, 0, size);
    }

    public static void copy(int[] from, int fromStart, int[] to, int toStart, int size){
        System.arraycopy(from, fromStart, to, toStart, size);
    }

    public static char[] copy(char[] arr){
        char[] res = new char[arr.length];
        copy(arr, res);
        return res;
    }

    public static void copy(char[] from, char[] to){
        copy(from, to, min(from.length, to.length));
    }

    public static void copy(char[] from, char[] to, int size){
        copy(from, 0, to, 0, size);
    }

    public static void copy(char[] from, int fromStart, char[] to, int toStart, int size){
        System.arraycopy(from, fromStart, to, toStart, size);
    }


    public static <T> void shift(T[] arr, int amount){
        shift(arr, 0, arr.length, amount);
    }

    public static <T> void shift(T[] arr, int start, int end, int amount){
        if(amount < 0) for(int i = start - amount;i < end;i++) arr[i] = arr[i - amount];
        else for(int i = end - 1 + amount;i >= start;i--) arr[i] = arr[i - amount];
    }


    public static void shift(float[] arr, int amount){
        shift(arr, 0, arr.length, amount);
    }

    public static void shift(float[] arr, int start, int end, int amount){
        if(amount < 0) for(int i = start - amount;i < end;i++) arr[i] = arr[i - amount];
        else for(int i = end - 1 + amount;i >= start;i--) arr[i] = arr[i - amount];
    }


    public static void shift(int[] arr, int amount){
        shift(arr, 0, arr.length, amount);
    }

    public static void shift(int[] arr, int start, int end, int amount){
        if(amount < 0) for(int i = start - amount;i < end;i++) arr[i] = arr[i - amount];
        else for(int i = end - 1 + amount;i >= start;i--) arr[i] = arr[i - amount];
    }

    public static void shift(char[] arr, int amount){
        shift(arr, 0, arr.length, amount);
    }

    public static void shift(char[] arr, int start, int end, int amount){
        if(amount < 0) for(int i = start - amount;i < end;i++) arr[i] = arr[i - amount];
        else for(int i = end - 1 + amount;i >= start;i--) arr[i] = arr[i - amount];
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
