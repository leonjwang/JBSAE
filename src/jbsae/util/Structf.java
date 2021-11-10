package jbsae.util;

import java.lang.reflect.*;

import static jbsae.util.Mathf.*;

@SuppressWarnings("all")
public class Structf{
    public static final int prime1 = 0xbe1f14b1;
    public static final int prime2 = 0xb4b82e39;
    public static final int prime3 = 0xced1c241;

    public static <T> T[] create(int size){
        return (T[])new Object[size];
    }

    public static <T> T[] create(int size, T[] base){
        return (T[])(base.getClass() == Object[].class ? new Object[size] :
        Array.newInstance(base.getClass().getComponentType(), size));
    }

    public static <T> boolean eql(T a, T b){
        return (a != null && a.equals(b)) || a == b;
    }

    public static int[] hashes(int h, int l){
        return new int[] {hash(h, prime1, l), hash(h, prime2, l), hash(h, prime3, l)};
    }

    public static int hash(int h, int prime, int l){
        h *= prime;
        return (h ^ h >>> (31 - trailZeros(l))) & (l - 1);
    }
}
