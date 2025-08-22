package jbsae.util;

import jbsae.func.*;
import jbsae.func.prim.*;
import jbsae.struct.*;
import jbsae.struct.List;

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

    public static <T> void fill(T[][] arr, T value){
        fill(arr, value, 0, arr.length, 0, arr[0].length);
    }

    public static <T> void fill(T[][] arr, T value, int start, int end, int bottom, int top){
        for(int i = start;i < end;i++) fill(arr[i], value, bottom, top);
    }


    public static <T> void fill(List<T> arr, T value){
        fill(arr, value, 0, arr.size());
    }

    public static <T> void fill(List<T> arr, T value, int start, int end){
        for(int i = start;i < end;i++) arr.set(i, value);
    }


    public static void fill(float[] arr, float value){
        fill(arr, value, 0, arr.length);
    }

    public static void fill(float[] arr, float value, int start, int end){
        for(int i = start;i < end;i++) arr[i] = value;
    }

    public static void fill(float[][] arr, float value){
        fill(arr, value, 0, arr.length, 0, arr[0].length);
    }

    public static void fill(float[][] arr, float value, int start, int end, int bottom, int top){
        for(int i = start;i < end;i++) fill(arr[i], value, bottom, top);
    }


    public static void fill(int[] arr, int value){
        fill(arr, value, 0, arr.length);
    }

    public static void fill(int[] arr, int value, int start, int end){
        for(int i = start;i < end;i++) arr[i] = value;
    }

    public static void fill(int[][] arr, int value){
        fill(arr, value, 0, arr.length, 0, arr[0].length);
    }

    public static void fill(int[][] arr, int value, int start, int end, int bottom, int top){
        for(int i = start;i < end;i++) fill(arr[i], value, bottom, top);
    }


    public static void fill(boolean[] arr, boolean value){
        fill(arr, value, 0, arr.length);
    }

    public static void fill(boolean[] arr, boolean value, int start, int end){
        for(int i = start;i < end;i++) arr[i] = value;
    }

    public static void fill(boolean[][] arr, boolean value){
        fill(arr, value, 0, arr.length, 0, arr[0].length);
    }

    public static void fill(boolean[][] arr, boolean value, int start, int end, int bottom, int top){
        for(int i = start;i < end;i++) fill(arr[i], value, bottom, top);
    }


    /** Array iteration and eaching. */
    public static <T> void each(T[] arr, Cons<T> cons){
        each(arr, cons, 0, arr.length);
    }

    public static <T> void each(T[] arr, Cons<T> cons, int start, int end){
        for(int i = start;i < end;i++) cons.get(arr[i]);
    }

    public static <T> void each(T[][] arr, Cons<T> cons){
        each(arr, cons, 0, arr.length, 0, arr[0].length);
    }

    public static <T> void each(T[][] arr, Cons<T> cons, int start, int end, int bottom, int top){
        for(int i = start;i < end;i++) each(arr[i], cons, bottom, top);
    }


    public static <T> void each(List<T> arr, Cons<T> cons){
        each(arr, cons, 0, arr.size());
    }

    public static <T> void each(List<T> arr, Cons<T> cons, int start, int end){
        for(int i = start;i < end;i++) cons.get(arr.get(i));
    }


    public static void each(float[] arr, Floatc cons){
        each(arr, cons, 0, arr.length);
    }

    public static void each(float[] arr, Floatc cons, int start, int end){
        for(int i = start;i < end;i++) cons.get(arr[i]);
    }

    public static void each(float[][] arr, Floatc cons){
        each(arr, cons, 0, arr.length, 0, arr[0].length);
    }

    public static void each(float[][] arr, Floatc cons, int start, int end, int bottom, int top){
        for(int i = start;i < end;i++) each(arr[i], cons, bottom, top);
    }


    public static void each(int[] arr, Intc cons){
        each(arr, cons, 0, arr.length);
    }

    public static void each(int[] arr, Intc cons, int start, int end){
        for(int i = start;i < end;i++) cons.get(arr[i]);
    }

    public static void each(int[][] arr, Intc cons){
        each(arr, cons, 0, arr.length, 0, arr[0].length);
    }

    public static void each(int[][] arr, Intc cons, int start, int end, int bottom, int top){
        for(int i = start;i < end;i++) each(arr[i], cons, bottom, top);
    }


    public static void each(boolean[] arr, Boolc cons){
        each(arr, cons, 0, arr.length);
    }

    public static void each(boolean[] arr, Boolc cons, int start, int end){
        for(int i = start;i < end;i++) cons.get(arr[i]);
    }

    public static void each(boolean[][] arr, Boolc cons){
        each(arr, cons, 0, arr.length, 0, arr[0].length);
    }

    public static void each(boolean[][] arr, Boolc cons, int start, int end, int bottom, int top){
        for(int i = start;i < end;i++) each(arr[i], cons, bottom, top);
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


    public static <T> Seq<T> copy(List<T> arr){
        return new Seq<>(arr);
    }

    public static <T> void copy(List<T> from, List<T> to){
        copy(from, to, min(from.size(), to.size()));
    }

    public static <T> void copy(List<T> from, List<T> to, int size){
        copy(from, 0, size, to, 0);
    }

    public static <T> void copy(List<T> from, int fromStart, int fromEnd, List<T> to, int toStart){
        copy(from, fromStart, fromEnd, to, toStart, toStart + (fromEnd - fromStart));
    }

    public static <T> void copy(List<T> from, int fromStart, int fromEnd, List<T> to, int toStart, int toEnd){
        for(int i = 0;i < min(abs(fromEnd - fromStart), abs(toEnd - toStart));i++){
            int toIndex = toStart + (toStart < toEnd ? i : -i), fromIndex = fromStart + (fromStart < fromEnd ? i : -i);
            if(toIndex < 0 || toIndex >= to.size() || fromIndex < 0 || fromIndex >= from.size()) continue;
            to.set(from.get(fromIndex), toIndex);
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


    /** Array flipping. */
    public static <T> void flip(T[] arr){
        flip(arr, 0, arr.length);
    }

    public static <T> void flip(T[] arr, int start, int end){
        for(int i = start;i < (start + end) / 2;i++) arr[i] = arr[end - i - 1];
    }


    public static <T> void flip(List<T> arr){
        flip(arr, 0, arr.size());
    }

    public static <T> void flip(List<T> arr, int start, int end){
        for(int i = start;i < (start + end) / 2;i++) arr.set(i, arr.get(end - i - 1));
    }


    public static void flip(float[] arr){
        flip(arr, 0, arr.length);
    }

    public static void flip(float[] arr, int start, int end){
        for(int i = start;i < (start + end) / 2;i++) arr[i] = arr[end - i - 1];
    }


    public static void flip(boolean[] arr){
        flip(arr, 0, arr.length);
    }

    public static void flip(boolean[] arr, int start, int end){
        for(int i = start;i < (start + end) / 2;i++) arr[i] = arr[end - i - 1];
    }


    public static void flip(int[] arr){
        flip(arr, 0, arr.length);
    }

    public static void flip(int[] arr, int start, int end){
        for(int i = start;i < (start + end) / 2;i++) arr[i] = arr[end - i - 1];
    }


    /** Array sorting. */
    public static <T> void sortArr(T[] arr){
        Arrays.sort(arr);
    }

    public static <T> void sortArr(T[] arr, Floatf<T> value){
        Arrays.sort(arr, new Comparator<T>(){ // Maybe not create a new one every time something is sorted
            public int compare(T a, T b){
                return value.get(a) > value.get(b) ? 1 : -1;
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


    public static boolean inside(Object[] arr, int i){
        return i >= 0 && i < arr.length;
    }

    public static boolean inside(Object[][] arr, int i, int j){
        return i >= 0 && i < arr.length && j >= 0 && j < arr[i].length;
    }

    public static boolean inside(float[] arr, int i){
        return i >= 0 && i < arr.length;
    }

    public static boolean inside(float[][] arr, int i, int j){
        return i >= 0 && i < arr.length && j >= 0 && j < arr[i].length;
    }

    public static boolean inside(boolean[] arr, int i){
        return i >= 0 && i < arr.length;
    }

    public static boolean inside(boolean[][] arr, int i, int j){
        return i >= 0 && i < arr.length && j >= 0 && j < arr[i].length;
    }

    public static boolean inside(int[] arr, int i){
        return i >= 0 && i < arr.length;
    }

    public static boolean inside(int[][] arr, int i, int j){
        return i >= 0 && i < arr.length && j >= 0 && j < arr[i].length;
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

    public static boolean choose(boolean[] arr){
        return arr[randInt(0, arr.length - 1)];
    }

    public static int choose(int[] arr){
        return arr[randInt(0, arr.length - 1)];
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
    public static int[] hash3(int h, int n){
        return hash3(h, n, new int[3]);
    }

    public static int[] hash3(int h, int n, int[] arr){
        arr[0] = hash(h, prime1, n);
        arr[1] = hash(h, prime2, n);
        arr[2] = hash(h, prime3, n);
        return arr;
    }

    public static int hash(int h, int prime, int n){
        h *= prime;
        return (h ^ h >>> (31 - trailZeros(n))) & (n - 1);
    }
}
