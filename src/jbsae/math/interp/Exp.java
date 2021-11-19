package jbsae.math.interp;

/** @author Nathan Sweet */
public class Exp implements Interp{
    final float value, power, min, scale;
    
    public Exp(float value, float power){
        this.value = value;
        this.power = power;
        min = (float)Math.pow(value, -power);
        scale = 1 / (1 - min);
    }
    
    @Override
    public float get(float f){
        if(f <= 0.5f) return ((float)Math.pow(value, power * (f * 2 - 1)) - min) * scale / 2;
        return (2 - ((float)Math.pow(value, -power * (f * 2 - 1)) - min) * scale) / 2;
    }
}
