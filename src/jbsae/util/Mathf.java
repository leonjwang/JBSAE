package jbsae.util;

import jbsae.math.*;

import java.util.*;

import static jbsae.JBSAE.*;

public class Mathf{
    public static final float threshhold = 0.001f;

    public static final float pi = (float)Math.PI;
    public static final float degToRad = pi / 180;
    public static final float radToDeg = 180 / pi;
    public static final float nan = Float.NaN;

    public static final int[] qx = new int[]{1, 0, 0, 1};
    public static final int[] qy = new int[]{1, 1, 0, 0};
    public static final int[] d4x = new int[]{0, 1, 0, -1};
    public static final int[] d4y = new int[]{1, 0, -1, 0};
    public static final int[] d8x = new int[]{0, 1, 1, 1, 0, -1, -1, -1};
    public static final int[] d8y = new int[]{1, 1, 0, -1, -1, -1, 0, 1};


    /** Random functions. */
    public static float random(){
        return (float)Math.random();
    }

    public static float random(float min, float max){
        return min + random() * (max - min);
    }

    public static int randInt(int min, int max){
        return (int)random(min, max + 0.99f);
    }

    public static boolean chance(float c){
        return random() < c;
    }


    /** Directional adders. */
    public static int d4x(int rot){
        return d4x[mod(rot, 4)];
    }

    public static int d4y(int rot){
        return d4y[mod(rot, 4)];
    }


    public static int d8x(int rot){
        return d8x[mod(rot, 8)];
    }

    public static int d8y(int rot){
        return d8y[mod(rot, 8)];
    }


    /** Ported Math functions. */
    public static float abs(float a){
        return a > 0 ? a : -a;
    }

    public static int abs(int a){
        return a > 0 ? a : -a;
    }


    public static float clamp(float n, float min, float max){
        return max(min(n, max), min);
    }

    public static int clamp(int n, int min, int max){
        return max(min(n, max), min);
    }


    public static float max(float a, float b){
        return a > b ? a : b;
    }

    public static float max(float a, float b, float c){
        return max(max(a, b), c);
    }

    public static float max(float... v){
        float max = v[0];
        for(int i = 1;i < v.length;i++) max = Math.max(max, v[i]);
        return max;
    }

    public static int max(int a, int b){
        return a > b ? a : b;
    }

    public static int max(int a, int b, int c){
        return max(max(a, b), c);
    }

    public static int max(int... v){
        int max = v[0];
        for(int i = 1;i < v.length;i++) max = Math.max(max, v[i]);
        return max;
    }


    public static float min(float a, float b){
        return a < b ? a : b;
    }

    public static float min(float a, float b, float c){
        return min(min(a, b), c);
    }

    public static float min(float... v){
        float min = v[0];
        for(int i = 1;i < v.length;i++) min = Math.min(min, v[i]);
        return min;
    }

    public static int min(int a, int b){
        return a < b ? a : b;
    }

    public static int min(int a, int b, int c){
        return min(min(a, b), c);
    }

    public static int min(int... v){
        int min = v[0];
        for(int i = 1;i < v.length;i++) min = Math.min(min, v[i]);
        return min;
    }


    public static float mod(float n, float m){
        return (n % m + m) % m;
    }

    public static int mod(int n, int m){
        return (n % m + m) % m;
    }


    public static float pow(float n, float d){
        return (float)Math.pow(n, d);
    }

    public static int pow(int n, int d){
        return (int)Math.pow(n, d);
    }

    public static float pow2(float n){
        return n * n;
    }

    public static int pow2(int n){
        return n * n;
    }


    public static float rt(float n, float d){
        if(n < 0) return (float)Double.NaN;
        if(n == 0) return 0;

        float a = n, b = n / d;
        while(abs(a - b) > threshhold){
            a = b;
            b = (float)((d - 1) * b + n / pow(b, d - 1)) / d;
        }
        return b;
    }

    public static int rt(int n, int d){
        return (int)rt((float)n, (float)d);
    }

    public static float rt2(float n){
        return (float)Math.sqrt(n);
    }

    public static int rt2(int n){
        return (int)Math.sqrt(n);
    }


    public static float log(float n, float d){
        return (float)(Math.log10(n) / Math.log10(d));
    }

    public static int log(int n, int d){
        return (int)(Math.log10(n) / Math.log10(d));
    }


    /** Distance functions. */
    public static float dst(float x1, float y1, float x2, float y2){
        return dst(x1, y1, 0, x2, y2, 0);
    }

    public static float dst(float x1, float y1, float z1, float x2, float y2, float z2){
        return rt2(dst2(x1, y1, z1, x2, y2, z2));
    }

    public static float dst(float... param){
        return rt2(dst2(param));
    }


    public static float dst(Pos2 a, Pos2 b){
        return dst(a.x(), a.y(), b.x(), b.y());
    }

    public static float dst(Pos3 a, Pos3 b){
        return dst(a.x(), a.y(), a.z(), b.x(), b.y(), b.z());
    }


    public static float dst2(float x1, float y1, float x2, float y2){
        return dst2(x1, y1, 0, x2, y2, 0);
    }

    public static float dst2(float x1, float y1, float z1, float x2, float y2, float z2){
        return pow(x1 - x2, 2) + pow(y1 - y2, 2) + pow(z1 - z2, 2);
    }

    public static float dst2(float... param){
        float total = 0;
        for(int i = 0;i < param.length / 2;i++) total += pow(param[i] - param[i + param.length / 2], 2);
        return total;
    }


    public static float dst2(Pos2 a, Pos2 b){
        return dst2(a.x(), a.y(), b.x(), b.y());
    }

    public static float dst2(Pos3 a, Pos3 b){
        return dst2(a.x(), a.y(), a.z(), b.x(), b.y(), b.z());
    }


    /** Comparison functions. */
    public static boolean nan(float v){
        return Float.isNaN(v);
    }

    public static boolean zero(float v){
        return abs(v) < threshhold;
    }

    public static boolean eqlf(float a, float b){
        return abs(a - b) < threshhold;
    }


    /** Trigonometry functions. */
    public static float sin(float a){
        return sinr(a * degToRad);
    }

    public static float sinr(float a){
        return (float)Math.sin(a);
    }


    public static float cos(float a){
        return cosr(a * degToRad);
    }

    public static float cosr(float a){
        return (float)Math.cos(a);
    }


    public static float tan(float a){
        return tanr(a * degToRad);
    }

    public static float tanr(float a){
        return (float)Math.tan(a);
    }


    public static float asin(float v){
        return asinr(v) * radToDeg;
    }

    public static float asinr(float v){
        return (float)Math.asin(v);
    }


    public static float acos(float v){
        return acosr(v) * radToDeg;
    }

    public static float acosr(float v){
        return (float)Math.acos(v);
    }


    public static float atan(float v){
        return atanr(v) * radToDeg;
    }

    public static float atanr(float v){
        return (float)Math.atan(v);
    }


    /** Trig values between 0 and 1. */
    public static float absin(float a){
        return (sin(a) + 1) / 2f;
    }

    public static float absinr(float a){
        return (sinr(a) + 1) / 2f;
    }


    public static float abcos(float a){
        return (cos(a) + 1) / 2f;
    }

    public static float abcosr(float a){
        return (cosr(a) + 1) / 2f;
    }


    /** Oscillation functions. */
    public static float sint(){
        return sint(1000);
    }

    public static float sint(float i){
        return sin((float)time.millis() / i * 360f);
    }

    public static float absint(){
        return absint(1000);
    }

    public static float absint(float i){
        return absin((float)time.millis() / i * 360f);
    }

    public static float cost(){
        return cost(1000);
    }

    public static float cost(float i){
        return cos((float)time.millis() / i * 360f);
    }

    public static float abcost(){
        return abcost(1000);
    }

    public static float abcost(float i){
        return abcos((float)time.millis() / i * 360f);
    }


    /** Ported class functions. */
    public static int trailZeros(int i){
        return Integer.numberOfTrailingZeros(i);
    }

    public static int intBits(float f){
        return Float.floatToIntBits(f);
    }


    /** Angle related functions. */
    public static float dsta(float a, float b){
        return min((a - b) < 0 ? a - b + 360 : a - b, (b - a) < 0 ? b - a + 360 : b - a);
    }

    public static float dstar(float a, float b){
        return dsta(a * radToDeg, b * radToDeg) * degToRad;
    }


    public static boolean withina(float a, float b, float cone){
        return dsta(a, b) < cone;
    }

    public static boolean withinar(float a, float b, float cone){
        return dstar(a, b) < cone;
    }


    public static float turn(float a, float to, float speed){
        if(abs(dsta(a, to)) < speed) return to;
        a = mod(a, 360);
        to = mod(to, 360);

        if(a > to == 360 - abs(a - to) > abs(a - to)) a -= speed;
        else a += speed;

        return a;
    }

    public static float turnr(float a, float to, float speed){
        return turn(a * radToDeg, to * radToDeg, speed * radToDeg) * degToRad;
    }


    /** @author Inferno, davebaol */
    public static class Rand{
        /** Normalization constant for double. */
        private static final double NORM_DOUBLE = 1.0 / (1L << 53);
        /** Normalization constant for float. */
        private static final double NORM_FLOAT = 1.0 / (1L << 24);

        /** The first half of the internal state of this pseudo-random number generator. */
        public long seed0;
        /** The second half of the internal state of this pseudo-random number generator. */
        public long seed1;

        /**
         * Creates a new random number generator. This constructor sets the seed of the random number generator to a value very likely
         * to be distinct from any other invocation of this constructor.
         * <p>
         * This implementation creates a {@link Random} instance to generate the initial seed.
         */
        public Rand(){
            setSeed(new Random().nextLong());
        }

        /**
         * Creates a new random number generator using a single {@code long} seed.
         * @param seed the initial seed
         */
        public Rand(long seed){
            setSeed(seed);
        }

        /**
         * Creates a new random number generator using two {@code long} seeds.
         * @param seed0 the first part of the initial seed
         * @param seed1 the second part of the initial seed
         */
        public Rand(long seed0, long seed1){
            setState(seed0, seed1);
        }

        private static long murmurHash3(long x){
            x ^= x >>> 33;
            x *= 0xff51afd7ed558ccdL;
            x ^= x >>> 33;
            x *= 0xc4ceb9fe1a85ec53L;
            x ^= x >>> 33;

            return x;
        }

        /**
         * Returns the next pseudo-random, uniformly distributed {@code long} value from this random number generator's sequence.
         * <p>
         * Subclasses should override this, as this is used by all other methods.
         */
        public long nextLong(){
            long s1 = this.seed0;
            final long s0 = this.seed1;
            this.seed0 = s0;
            s1 ^= s1 << 23;
            return (this.seed1 = (s1 ^ s0 ^ (s1 >>> 17) ^ (s0 >>> 26))) + s0;
        }

        /** This protected method is final because, contrary to the superclass, it's not used anymore by the other methods. */
        protected final int next(int bits){
            return (int)(nextLong() & ((1L << bits) - 1));
        }

        /**
         * Returns the next pseudo-random, uniformly distributed {@code int} value from this random number generator's sequence.
         * <p>
         * This implementation uses {@link #nextLong()} internally.
         */
        public int nextInt(){
            return (int)nextLong();
        }

        /**
         * Returns a pseudo-random, uniformly distributed {@code int} value between 0 (inclusive) and the specified value (exclusive),
         * drawn from this random number generator's sequence.
         * <p>
         * This implementation uses {@link #nextLong()} internally.
         * @param n the positive bound on the random number to be returned.
         * @return the next pseudo-random {@code int} value between {@code 0} (inclusive) and {@code n} (exclusive).
         */
        public int nextInt(final int n){
            return (int)nextLong(n);
        }

        /**
         * Returns a pseudo-random, uniformly distributed {@code long} value between 0 (inclusive) and the specified value (exclusive),
         * drawn from this random number generator's sequence. The algorithm used to generate the value guarantees that the result is
         * uniform, provided that the sequence of 64-bit values produced by this generator is.
         * <p>
         * This implementation uses {@link #nextLong()} internally.
         * @param n the positive bound on the random number to be returned.
         * @return the next pseudo-random {@code long} value between {@code 0} (inclusive) and {@code n} (exclusive).
         */
        public long nextLong(final long n){
            if(n <= 0) throw new IllegalArgumentException("n must be positive");
            for(;;){
                final long bits = nextLong() >>> 1;
                final long value = bits % n;
                if(bits - value + (n - 1) >= 0) return value;
            }
        }

        /**
         * Returns a pseudo-random, uniformly distributed {@code double} value between 0.0 and 1.0 from this random number generator's
         * sequence.
         * <p>
         * This implementation uses {@link #nextLong()} internally.
         */
        public double nextDouble(){
            return (nextLong() >>> 11) * NORM_DOUBLE;
        }

        /**
         * Returns a pseudo-random, uniformly distributed {@code float} value between 0.0 and 1.0 from this random number generator's
         * sequence.
         * <p>
         * This implementation uses {@link #nextLong()} internally.
         */
        public float nextFloat(){
            return (float)((nextLong() >>> 40) * NORM_FLOAT);
        }

        /**
         * Returns a pseudo-random, uniformly distributed {@code boolean } value from this random number generator's sequence.
         * <p>
         * This implementation uses {@link #nextLong()} internally.
         */
        public boolean nextBoolean(){
            return (nextLong() & 1) != 0;
        }

        /**
         * Generates random bytes and places them into a user-supplied byte array. The number of random bytes produced is equal to the
         * length of the byte array.
         * <p>
         * This implementation uses {@link #nextLong()} internally.
         */
        public void nextBytes(final byte[] bytes){
            int n;
            int i = bytes.length;
            while(i != 0){
                n = i < 8 ? i : 8; // min(i, 8);
                for(long bits = nextLong(); n-- != 0; bits >>= 8)
                    bytes[--i] = (byte)bits;
            }
        }

        /**
         * Sets the internal seed of this generator based on the given {@code long} value.
         * <p>
         * The given seed is passed twice through a hash function. This way, if the user passes a small value we avoid the short
         * irregular transient associated with states having a very small number of bits set.
         * @param seed a nonzero seed for this generator (if zero, the generator will be seeded with {@link Long#MIN_VALUE}).
         */
        public void setSeed(final long seed){
            long seed0 = murmurHash3(seed == 0 ? Long.MIN_VALUE : seed);
            setState(seed0, murmurHash3(seed0));
        }

        public boolean chance(double chance){
            return nextDouble() < chance;
        }

        public float range(float amount){
            return nextFloat() * amount * 2 - amount;
        }

        public float random(float max){
            return nextFloat() * max;
        }

        /** Inclusive. */
        public int random(int max){
            return nextInt(max + 1);
        }

        public float random(float min, float max){
            return min + (max - min) * nextFloat();
        }

        public int range(int amount){
            return nextInt(amount * 2 + 1) - amount;
        }

        public int random(int min, int max){
            if(min >= max) return min;
            return min + nextInt(max - min + 1);
        }

        /**
         * Sets the internal state of this generator.
         * @param seed0 the first part of the internal state
         * @param seed1 the second part of the internal state
         */
        public void setState(final long seed0, final long seed1){
            this.seed0 = seed0;
            this.seed1 = seed1;
        }

        /**
         * Returns the internal seeds to allow state saving.
         * @param seed must be 0 or 1, designating which of the 2 long seeds to return
         * @return the internal seed that can be used in setState
         */
        public long getState(int seed){
            return seed == 0 ? seed0 : seed1;
        }
    }
}