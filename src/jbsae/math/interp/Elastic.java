package jbsae.math.interp;

import static jbsae.util.Mathf.*;

/** @author Nathan Sweet */
public class Elastic implements Interp{
    final float value, power, scale, bounces;
    
    public Elastic(float value, float power, int bounces, float scale){
        this.value = value;
        this.power = power;
        this.scale = scale;
        this.bounces = (float)(bounces * pi * (bounces % 2 == 0 ? 1 : -1));
    }
    
    @Override
    public float get(float f){
        if(f <= 0.5f){
            f *= 2;
            return (float)Math.pow(value, power * (f - 1)) * sinr(f * bounces) * scale / 2;
        }
        f = 1 - f;
        f *= 2;
        return 1 - (float)Math.pow(value, power * (f - 1)) * sinr((f) * bounces) * scale / 2;
    }
}
