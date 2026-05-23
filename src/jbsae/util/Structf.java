package jbsae.util;

import jbsae.func.prim.*;

import java.util.*;

import static jbsae.util.Mathf.*;


// TODO: Delete this whole class
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

    public static <T> Iterable<T> wrap(Iterator<T> itr){
        return () -> itr;
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
}
