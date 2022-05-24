package jbsae.util;

import jbsae.math.*;

import static jbsae.JBSAE.*;

public class Mathf{
    public static final float threshhold = 0.001f;

    public static final float pi = (float)Math.PI;
    public static final float degToRad = pi / 180;
    public static final float radToDeg = 180 / pi;

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
    public static float dst(float x1, float y1, float z1, float x2, float y2, float z2){
        return rt2(pow(x1 - x2, 2) + pow(y1 - y2, 2) + pow(z1 - z2, 2));
    }

    public static float dst(float x1, float y1, float x2, float y2){
        return dst(x1, y1, 0, x2, y2, 0);
    }

    public static float dst(float... param){
        float total = 0;
        for(int i = 0;i < param.length / 2;i++) total += pow(param[i] - param[i + param.length / 2], 2);
        return rt2(total);
    }

    public static int dst(int x1, int y1, int z1, int x2, int y2, int z2){
        return rt2(pow(x1 - x2, 2) + pow(y1 - y2, 2) + pow(z1 - z2, 2));
    }

    public static int dst(int x1, int y1, int x2, int y2){
        return dst(x1, y1, 0, x2, y2, 0);
    }

    public static int dst(int... param){
        int total = 0;
        for(int i = 0;i < param.length / 2;i++) total += pow(param[i] - param[i + param.length / 2], 2);
        return rt2(total);
    }


    public static float dst(Pos2 a, Pos2 b){
        return dst(a.x(), a.y(), b.x(), b.y());
    }

    public static float dst(Pos3 a, Pos3 b){
        return dst(a.x(), a.y(), a.z(), b.x(), b.y(), b.z());
    }


    /** Comparison functions. */
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
}