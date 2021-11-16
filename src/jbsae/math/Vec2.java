package jbsae.math;

import static jbsae.util.Mathf.*;

public class Vec2{
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

    public Vec2 add(float x, float y){
        this.x += x;
        this.y += y;
        return this;
    }

    public Vec2 add(Vec2 v){
        return add(v.x, v.y);
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

    public Vec2 rot(float a){
        //TODO: rot
        return null;
    }

    public Vec2 nor(){
        return scl(1 / len());
    }

    public float len(){
        return rt2(x * x + y * y);
    }

    public Vec2 cpy(){
        return new Vec2(this);
    }

    @Override
    public String toString(){
        return "(" + x + ", " + y + ")";
    }
}
