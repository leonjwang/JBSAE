package jbsae.util;

public class Mathf{
    public static final float threshhold = 0.001f;

    public static final float pi = (float)Math.PI;
    public static final float degToRad = pi / 180;
    public static final float radToDeg = 180 /pi;

    public static final int[] d4x = new int[] {0, 1, 0, -1};
    public static final int[] d4y = new int[] {1, 0, -1, 0};
    public static final int[] d8x = new int[] {0, 1, 1, 1, 0, -1, -1, -1};
    public static final int[] d8y = new int[] {1, 1, 0, -1, -1, -1, 0, 1};

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
        return Math.abs(a);
    }

    public static int abs(int a){
        return (int)abs((float)a);
    }

    public static float clamp(float n, float min, float max){
        return max(min(n, max), min);
    }

    public static int clamp(int n, int min, int max){
        return (int)clamp((float)n, (float)min, (float)max);
    }

    public static float max(float... v){
        float max = v[0];
        for(int i = 1;i < v.length;i++) max = Math.max(max, v[i]);
        return max;
    }

    public static int max(int... v){
        int max = v[0];
        for(int i = 1;i < v.length;i++) max = Math.max(max, v[i]);
        return max;
    }

    public static float min(float... v){
        float min = v[0];
        for(int i = 1;i < v.length;i++) min = Math.min(min, v[i]);
        return min;
    }

    public static int min(int... v){
        int min = v[0];
        for(int i = 1;i < v.length;i++) min = Math.min(min, v[i]);
        return min;
    }

    public static float mod(float n, float m){
        return (n + (int)(abs(n / m) + 1) * m) % m;
    }

    public static int mod(int n, int m){
        return (int)mod((float)n, (float)m);
    }

    public static float pow(float n, float d){
        return (float)Math.pow(n, d);
    }

    public static int pow(int n, int d){
        return (int)pow((float)n, (float)d);
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


    /** Comparisons */
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

    /** Ported class functions. */
    public static int trailZeros(int i){
        return Integer.numberOfTrailingZeros(i);
    }

    public static int intBits(float f){
        return Float.floatToIntBits(f);
    }
}
