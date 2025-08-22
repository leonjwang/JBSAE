package jbsae.math;

import java.util.*;

// TODO: Rewrite to understand myself
public class Rand{
    public static final double NORM_DOUBLE = 1.0 / (1L << 53);
    public static final double NORM_FLOAT = 1.0 / (1L << 24);

    public long seed0, seed1;

    public Rand(){
        seed(new Random().nextLong());
    }

    public Rand(long seed){
        seed(seed);
    }

    public Rand(long seed0, long seed1){
        seed(seed0, seed1);
    }

    public Rand seed(long seed){
        long seed0 = murmurHash3(seed == 0 ? Long.MIN_VALUE : seed);
        return seed(seed0, murmurHash3(seed0));
    }

    public Rand seed(long seed0, long seed1){
        this.seed0 = seed0;
        this.seed1 = seed1;
        return this;
    }

    public long nextl(){
        long s1 = this.seed0, s0 = this.seed1;
        this.seed0 = s0;
        s1 ^= s1 << 23;
        return (this.seed1 = (s1 ^ s0 ^ (s1 >>> 17) ^ (s0 >>> 26))) + s0;
    }

    public long nextl(final long n){
        while(true){
            final long bits = nextl() >>> 1;
            final long value = bits % n;
            if(bits - value + (n - 1) >= 0) return value;
        }
    }

    public int nexti(){
        return (int)nextl();
    }

    public int nexti(final int n){
        return (int)nextl(n);
    }

    public double nextd(){
        return (nextl() >>> 11) * NORM_DOUBLE;
    }

    public float nextf(){
        return (float)((nextl() >>> 40) * NORM_FLOAT);
    }

    public boolean nextb(){
        return (nextl() & 1) != 0;
    }

    public void next(byte[] bytes){
        int n;
        int i = bytes.length;
        while(i != 0){
            n = i < 8 ? i : 8;
            for(long bits = nextl();n-- != 0;bits >>= 8) bytes[--i] = (byte)bits;
        }
    }

    public static long murmurHash3(long x){
        x ^= x >>> 33;
        x *= 0xff51afd7ed558ccdL;
        x ^= x >>> 33;
        x *= 0xc4ceb9fe1a85ec53L;
        x ^= x >>> 33;

        return x;
    }
}
