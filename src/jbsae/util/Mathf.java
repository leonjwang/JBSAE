package jbsae.util;

public class Mathf{
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
