package jbsae.math.interp;

public class Pow implements Interp{
    final int power;
    
    public Pow(int power){
        this.power = power;
    }
    
    @Override
    public float get(float f){
        if(f <= 0.5f) return (float)Math.pow(f * 2, power) / 2;
        return (float)Math.pow((f - 1) * 2, power) / (power % 2 == 0 ? -2 : 2) + 1;
    }
}
