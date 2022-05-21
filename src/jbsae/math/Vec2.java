package jbsae.math;

import static jbsae.util.Mathf.*;

public class Vec2 implements Pos2{
    public float x, y;


    public Vec2(){
    }

    public Vec2(float x, float y){
        set(x, y);
    }

    public Vec2(Vec2 v){
        set(v);
    }


    public Vec2 set(float x, float y){
        this.x = x;
        this.y = y;
        return this;
    }

    public Vec2 set(Vec2 v){
        return set(v.x, v.y);
    }

    public Vec2 setr(float a, float l){
        return set(cos(a) * l, sin(a) * l);
    }

    public Vec2 add(float x, float y){
        this.x += x;
        this.y += y;
        return this;
    }

    public Vec2 add(Vec2 v){
        return add(v.x, v.y);
    }

    public Vec2 sub(float x, float y){
        return add(-x, -y);
    }

    public Vec2 sub(Vec2 v){
        return sub(v.x, v.y);
    }

    public Vec2 scl(float x, float y){
        this.x *= x;
        this.y *= y;
        return this;
    }

    public Vec2 scl(Vec2 v){
        return scl(v.x, v.y);
    }

    public Vec2 scl(float v){
        return scl(v, v);
    }

    public Vec2 inv(){
        return scl(-1);
    }

    public Vec2 rot(float a){
        return setr(ang() + a, len());
    }

    public Vec2 nor(){
        return scl(1 / len());
    }


    public float ang(){
        return eqlf(x, 0) ? (y > 0 ? 90 : 270) : mod((x > 0 ? 0 : 180) + atan(y / x), 360);
    }

    public float len(){
        return rt2(x * x + y * y);
    }


    public Vec2 cpy(){
        return new Vec2(this);
    }


    @Override
    public float x(){
        return x;
    }

    @Override
    public float y(){
        return y;
    }
}
