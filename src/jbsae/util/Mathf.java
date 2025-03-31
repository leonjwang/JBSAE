package jbsae.util;

import jbsae.math.*;

import static jbsae.JBSAE.*;

public class Mathf{
    public static final float THRESHHOLD = 0.001f;

    public static final float PI = (float)Math.PI;
    public static final float DEG_TO_RAD = PI / 180;
    public static final float RAD_TO_DEG = 180 / PI;
    public static final float NAN = Float.NaN;

    public static final int[] QX = new int[]{1, 0, 0, 1};
    public static final int[] QY = new int[]{1, 1, 0, 0};
    public static final int[] D4X = new int[]{0, 1, 0, -1};
    public static final int[] D4Y = new int[]{1, 0, -1, 0};
    public static final int[] D8X = new int[]{0, 1, 1, 1, 0, -1, -1, -1};
    public static final int[] D8Y = new int[]{1, 1, 0, -1, -1, -1, 0, 1};

    public static final Rand RAND = new Rand();

    /** Random functions. */
    /** Seeds the random number generator. */
    public static void seed(long seed){
        RAND.seed(seed);
    }

    /** Returns a random float between 0 and 1. */
    public static float random(){
        return RAND.nextf();
    }

    /** Returns a random float between min and max. */
    public static float random(float min, float max){
        return min + random() * (max - min);
    }

    /** Returns a random integer between min and max. */
    public static int randInt(int min, int max){
        return (int)random(min, max + 1f - THRESHHOLD);
    }

    /** Returns true with a probability of c. */
    public static boolean chance(float c){
        return random() < c;
    }


    /** Directional adders. */
    /** Returns the x component of a 4-directional vector. */
    public static int d4x(int rot){
        return D4X[mod(rot, 4)];
    }

    /** Returns the y component of a 4-directional vector. */
    public static int d4y(int rot){
        return D4Y[mod(rot, 4)];
    }


    /** Returns the x component of an 8-directional vector. */
    public static int d8x(int rot){
        return D8X[mod(rot, 8)];
    }

    /** Returns the y component of an 8-directional vector. */
    public static int d8y(int rot){
        return D8Y[mod(rot, 8)];
    }


    /** Ported Math functions. */
    /** Rounds a float to the nearest integer. */
    public static int round(float a){
        return Math.round(a);
    }


    /** Returns the absolute value of a float. */
    public static float abs(float a){
        return a > 0 ? a : -a;
    }

    /** Returns the absolute value of an integer. */
    public static int abs(int a){
        return a > 0 ? a : -a;
    }


    /** Clamps a float between min and max. */
    public static float clamp(float n, float min, float max){
        return max(min(n, max), min);
    }

    /** Clamps an integer between min and max. */
    public static int clamp(int n, int min, int max){
        return max(min(n, max), min);
    }


    /** Returns the maximum of two floats. */
    public static float max(float a, float b){
        return a > b ? a : b;
    }

    /** Returns the maximum of three floats. */
    public static float max(float a, float b, float c){
        return max(max(a, b), c);
    }

    /** Returns the maximum of an array of floats. */
    public static float max(float... v){
        float max = v[0];
        for(int i = 1;i < v.length;i++) max = Math.max(max, v[i]);
        return max;
    }

    /** Returns the maximum of two integers. */
    public static int max(int a, int b){
        return a > b ? a : b;
    }

    /** Returns the maximum of three integers. */
    public static int max(int a, int b, int c){
        return max(max(a, b), c);
    }

    /** Returns the maximum of an array of integers. */
    public static int max(int... v){
        int max = v[0];
        for(int i = 1;i < v.length;i++) max = Math.max(max, v[i]);
        return max;
    }


    /** Returns the minimum of two floats. */
    public static float min(float a, float b){
        return a < b ? a : b;
    }

    /** Returns the minimum of three floats. */
    public static float min(float a, float b, float c){
        return min(min(a, b), c);
    }

    /** Returns the minimum of an array of floats. */
    public static float min(float... v){
        float min = v[0];
        for(int i = 1;i < v.length;i++) min = Math.min(min, v[i]);
        return min;
    }

    /** Returns the minimum of two integers. */
    public static int min(int a, int b){
        return a < b ? a : b;
    }

    /** Returns the minimum of three integers. */
    public static int min(int a, int b, int c){
        return min(min(a, b), c);
    }

    /** Returns the minimum of an array of integers. */
    public static int min(int... v){
        int min = v[0];
        for(int i = 1;i < v.length;i++) min = Math.min(min, v[i]);
        return min;
    }


    /** Returns the modulus of two floats. */
    public static float mod(float n, float m){
        return (n % m + m) % m;
    }

    /** Returns the modulus of two integers. */
    public static int mod(int n, int m){
        return (n % m + m) % m;
    }


    /** Returns the result of raising a float to a power. */
    public static float pow(float n, float d){
        return (float)Math.pow(n, d);
    }

    /** Returns the result of raising an integer to a power. */
    public static int pow(int n, int d){
        return (int)Math.pow(n, d);
    }

    /** Returns the square of a float. */
    public static float pow2(float n){
        return n * n;
    }

    /** Returns the square of an integer. */
    public static int pow2(int n){
        return n * n;
    }


    /** Returns the d-th root of a float. */
    public static float rt(float n, float d){
        if(n < 0) return (float)Double.NaN;
        if(n == 0) return 0;

        float a = n, b = n / d;
        while(abs(a - b) > THRESHHOLD){
            a = b;
            b = (float)((d - 1) * b + n / pow(b, d - 1)) / d;
        }
        return b;
    }

    /** Returns the d-th root of an integer. */
    public static int rt(int n, int d){
        return (int)rt((float)n, (float)d);
    }

    /** Returns the square root of a float. */
    public static float rt2(float n){
        return (float)Math.sqrt(n);
    }

    /** Returns the square root of an integer. */
    public static int rt2(int n){
        return (int)Math.sqrt(n);
    }


    /** Returns the logarithm of a float with base d. */
    public static float log(float n, float d){
        return (float)(Math.log10(n) / Math.log10(d));
    }

    /** Returns the logarithm of an integer with base d. */
    public static int log(int n, int d){
        return (int)(Math.log10(n) / Math.log10(d));
    }


    /** Distance functions. */
    /** Returns the distance between two points in 2D space. */
    public static float dst(float x1, float y1, float x2, float y2){
        return dst(x1, y1, 0, x2, y2, 0);
    }

    /** Returns the distance between two points in 3D space. */
    public static float dst(float x1, float y1, float z1, float x2, float y2, float z2){
        return rt2(dst2(x1, y1, z1, x2, y2, z2));
    }

    /** Returns the distance between points in n-dimensional space. */
    public static float dst(float... param){
        return rt2(dst2(param));
    }


    /** Returns the distance between two Pos2 objects. */
    public static float dst(Pos2 a, Pos2 b){
        return dst(a.x(), a.y(), b.x(), b.y());
    }

    /** Returns the distance between two Pos3 objects. */
    public static float dst(Pos3 a, Pos3 b){
        return dst(a.x(), a.y(), a.z(), b.x(), b.y(), b.z());
    }


    /** Returns the squared distance between two points in 2D space. */
    public static float dst2(float x1, float y1, float x2, float y2){
        return dst2(x1, y1, 0, x2, y2, 0);
    }

    /** Returns the squared distance between two points in 3D space. */
    public static float dst2(float x1, float y1, float z1, float x2, float y2, float z2){
        return pow(x1 - x2, 2) + pow(y1 - y2, 2) + pow(z1 - z2, 2);
    }

    /** Returns the squared distance between points in n-dimensional space. */
    public static float dst2(float... param){
        float total = 0;
        for(int i = 0;i < param.length / 2;i++) total += pow(param[i] - param[i + param.length / 2], 2);
        return total;
    }


    /** Returns the squared distance between two Pos2 objects. */
    public static float dst2(Pos2 a, Pos2 b){
        return dst2(a.x(), a.y(), b.x(), b.y());
    }

    /** Returns the squared distance between two Pos3 objects. */
    public static float dst2(Pos3 a, Pos3 b){
        return dst2(a.x(), a.y(), a.z(), b.x(), b.y(), b.z());
    }


    /** Comparison functions. */
    /** Returns true if the float value is NaN. */
    public static boolean nan(float v){
        return Float.isNaN(v);
    }

    /** Returns true if the float value is zero. */
    public static boolean zero(float v){
        return abs(v) < THRESHHOLD;
    }

    /** Returns true if two float values are equal within a threshold. */
    public static boolean eqlf(float a, float b){
        return abs(a - b) < THRESHHOLD;
    }


    /** Trigonometry functions. */
    /** Returns the sine of an angle in degrees. */
    public static float sin(float a){
        return sinr(a * DEG_TO_RAD);
    }

    /** Returns the sine of an angle in radians. */
    public static float sinr(float a){
        return (float)Math.sin(a);
    }


    /** Returns the cosine of an angle in degrees. */
    public static float cos(float a){
        return cosr(a * DEG_TO_RAD);
    }

    /** Returns the cosine of an angle in radians. */
    public static float cosr(float a){
        return (float)Math.cos(a);
    }


    /** Returns the tangent of an angle in degrees. */
    public static float tan(float a){
        return tanr(a * DEG_TO_RAD);
    }

    /** Returns the tangent of an angle in radians. */
    public static float tanr(float a){
        return (float)Math.tan(a);
    }


    /** Returns the arcsine of a value in degrees. */
    public static float asin(float v){
        return asinr(v) * RAD_TO_DEG;
    }

    /** Returns the arcsine of a value in radians. */
    public static float asinr(float v){
        return (float)Math.asin(v);
    }


    /** Returns the arccosine of a value in degrees. */
    public static float acos(float v){
        return acosr(v) * RAD_TO_DEG;
    }

    /** Returns the arccosine of a value in radians. */
    public static float acosr(float v){
        return (float)Math.acos(v);
    }


    /** Returns the arctangent of a value in degrees. */
    public static float atan(float v){
        return atanr(v) * RAD_TO_DEG;
    }

    /** Returns the arctangent of a value in radians. */
    public static float atanr(float v){
        return (float)Math.atan(v);
    }


    /** Trig values between 0 and 1. */
    /** Returns the absolute sine of an angle in degrees. */
    public static float absin(float a){
        return (sin(a) + 1) / 2f;
    }

    /** Returns the absolute sine of an angle in radians. */
    public static float absinr(float a){
        return (sinr(a) + 1) / 2f;
    }


    /** Returns the absolute cosine of an angle in degrees. */
    public static float abcos(float a){
        return (cos(a) + 1) / 2f;
    }

    /** Returns the absolute cosine of an angle in radians. */
    public static float abcosr(float a){
        return (cosr(a) + 1) / 2f;
    }


    /** Oscillation functions. */
    /** Returns the sine of the current time in milliseconds divided by i. */
    public static float sint(){
        return sint(1000);
    }

    /** Returns the sine of the current time in milliseconds divided by i. */
    public static float sint(float i){
        return sin((float)time.millis() / i * 360f);
    }

    /** Returns the absolute sine of the current time in milliseconds divided by i. */
    public static float absint(){
        return absint(1000);
    }

    /** Returns the absolute sine of the current time in milliseconds divided by i. */
    public static float absint(float i){
        return absin((float)time.millis() / i * 360f);
    }

    /** Returns the cosine of the current time in milliseconds divided by i. */
    public static float cost(){
        return cost(1000);
    }

    /** Returns the cosine of the current time in milliseconds divided by i. */
    public static float cost(float i){
        return cos((float)time.millis() / i * 360f);
    }

    /** Returns the absolute cosine of the current time in milliseconds divided by i. */
    public static float abcost(){
        return abcost(1000);
    }

    /** Returns the absolute cosine of the current time in milliseconds divided by i. */
    public static float abcost(float i){
        return abcos((float)time.millis() / i * 360f);
    }


    /** Ported class functions. */
    /** Returns the number of trailing zeros in the binary representation of an integer. */
    public static int trailZeros(int i){
        return Integer.numberOfTrailingZeros(i);
    }

    /** Returns the bit representation of a float. */
    public static int intBits(float f){
        return Float.floatToIntBits(f);
    }

    /** Returns the float representation of an integer bit pattern. */
    public static float bitFloat(int i){
        return Float.intBitsToFloat(i);
    }

    /** Returns the bit representation of a double. */
    public static long longBits(double d){
        return Double.doubleToLongBits(d);
    }

    /** Returns the double representation of a long bit pattern. */
    public static double bitDouble(long l){
        return Double.longBitsToDouble(l);
    }


    /** Angle related functions. */
    /** Returns the angle between the positive x-axis and the point (x, y) in degrees. */
    public static float angle(float x, float y){
        return zero(x) ? (y > 0 ? 90 : 270) : mod((x > 0 ? 0 : 180) + atan(y / x), 360);
    }

    /** Returns the angle between the positive x-axis and the point (x, y) in radians. */
    public static float angler(float x, float y){
        return angle(x, y) * DEG_TO_RAD;
    }


    /** Returns the smallest difference between two angles in degrees. */
    public static float dsta(float a, float b){
        return min((a - b) < 0 ? a - b + 360 : a - b, (b - a) < 0 ? b - a + 360 : b - a);
    }

    /** Returns the smallest difference between two angles in radians. */
    public static float dstar(float a, float b){
        return dsta(a * RAD_TO_DEG, b * RAD_TO_DEG) * DEG_TO_RAD;
    }

    /** Returns the difference in angle between a and b. */
    /** Returns the difference in angle between a and b in degrees. */
    public static float diffa(float a, float b){
        if(a > b) return (b + 360 - a > a - b) ? (a - b) : -(b + 360 - a);
        else return (a + 360 - b > b - a)  ? -(b - a) : (a + 360 - b);
    }

    /** Returns the difference in angle between a and b in radians. */
    public static float diffar(float a, float b){
        if(a > b) return (b + (2 * PI) - a > a - b) ? (a - b) : -(b + (2 * PI) - a);
        else return (a + (2 * PI) - b > b - a)  ? -(b - a) : (a + (2 * PI) - b);
    }


    /** Returns true if the angle a is within the cone angle of b in degrees. */
    public static boolean withina(float a, float b, float cone){
        return dsta(a, b) < cone;
    }

    /** Returns true if the angle a is within the cone angle of b in radians. */
    public static boolean withinar(float a, float b, float cone){
        return dstar(a, b) < cone;
    }


    /** Returns the angle a turned towards the angle b at a speed of speed in degrees. */
    public static float turn(float a, float to, float speed){
        if(abs(dsta(a, to)) < speed) return to;
        a = mod(a, 360);
        to = mod(to, 360);

        if(a > to == 360 - abs(a - to) > abs(a - to)) a -= speed;
        else a += speed;

        return a;
    }

    /** Returns the angle a turned towards the angle b at a speed of speed in radians. */
    public static float turnr(float a, float to, float speed){
        return turn(a * RAD_TO_DEG, to * RAD_TO_DEG, speed * RAD_TO_DEG) * DEG_TO_RAD;
    }
}