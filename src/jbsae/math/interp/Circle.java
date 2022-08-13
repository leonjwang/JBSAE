package jbsae.math.interp;


public class Circle implements Interp{
    public Circle(){
    }

    @Override
    public float get(float f){
        if(f <= 0.5f) return (1 - (float)Math.sqrt(1 - f * f * 4)) / 2;
        return (((float)Math.sqrt(1 - (f - 1) * (f - 1) * 4)) + 1) / 2;
    }
}
