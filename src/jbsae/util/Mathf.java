package jbsae.util;

public class Mathf{
    public static float random(){
        return (float)Math.random();
    }

    public static float random(float min, float max){
        return min + random() * (max - min);
    }

    public static int randInt(int min, int max){
        return (int)random(min, max + 0.99f);
    }

    public static float min(float a, float b){
        return Math.min(a, b);
    }

    public static int min(int a, int b){
        return (int)min((float)a, (float)b);
    }

    public static float max(float a, float b){
        return Math.max(a, b);
    }

    public static int max(int a, int b){
        return (int)max((float)a, (float)b);
    }
}
