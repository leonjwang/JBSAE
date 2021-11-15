package jbsae.math;

public class Vec4{
    public float x, y, z, w;

    public Vec4(Vec3 v, float w){
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = w;
    }

    public Vec4(float x, float y, float z, float w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }
}
