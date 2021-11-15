package jbsae.math;

import static jbsae.util.Mathf.*;

public class Vec3{
    public float x, y, z;

    public Vec3(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3 scl(float s){
        x *= s;
        y *= s;
        z *= s;
        return this;
    }

    public Vec3 nor(){
        return scl(1f / len());
    }

    public float len(){
        return rt2(x * x + y * y + z * z);
    }
}
