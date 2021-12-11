package jbsae.math;

import static jbsae.util.Mathf.*;

public class Vec3 implements Pos3{
    public float x, y, z;

    public Vec3(){
    }

    public Vec3(float x, float y, float z){
        set(x, y, z);
    }

    public Vec3(Vec3 v){
        set(v);
    }

    public Vec3 set(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vec3 set(Vec3 v){
        return set(v.x, v.y, v.z);
    }

    public Vec3 add(float x, float y, float z){
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vec3 add(Vec3 v){
        return add(v.x, v.y, v.z);
    }

    public Vec3 scl(float x, float y, float z){
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    public Vec3 scl(float v){
        return scl(v, v, v);
    }

    public Vec3 nor(){
        return scl(1 / len());
    }

    public float len(){
        return rt2(x * x + y * y + z * z);
    }

    public Vec3 cpy(){
        return new Vec3(this);
    }

    @Override
    public float x(){
        return x;
    }

    @Override
    public float y(){
        return y;
    }

    @Override
    public float z(){
        return z;
    }
}
