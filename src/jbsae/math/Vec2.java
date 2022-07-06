package jbsae.math;

import static jbsae.util.Mathf.*;
import static jbsae.util.Stringf.*;

public class Vec2 implements Pos2{
    public float x, y;


    public Vec2(){
    }

    public Vec2(float x, float y){
        set(x, y);
    }

    public Vec2(Pos2 v){
        set(v);
    }


    public Vec2 set(float x, float y){
        this.x = x;
        this.y = y;
        return this;
    }

    public Vec2 set(Pos2 v){
        return set(v.x(), v.y());
    }

    public Vec2 setr(float l, float a){
        return set(cos(a) * l, sin(a) * l);
    }

    public Vec2 add(float x, float y){
        this.x += x;
        this.y += y;
        return this;
    }

    public Vec2 add(Pos2 v){
        return add(v.x(), v.y());
    }

    public Vec2 sub(float x, float y){
        return add(-x, -y);
    }

    public Vec2 sub(Pos2 v){
        return sub(v.x(), v.y());
    }

    public Vec2 scl(float x, float y){
        this.x *= x;
        this.y *= y;
        return this;
    }

    public Vec2 scl(Pos2 v){
        return scl(v.x(), v.y());
    }

    public Vec2 scl(float v){
        return scl(v, v);
    }

    public Vec2 inv(){
        return scl(-1);
    }

    public Vec2 rot(float a){
        return setr(len(), ang() + a);
    }

    public Vec2 nor(){
        return scl(1 / len());
    }


    public float ang(){
        return zero(x) ? (y > 0 ? 90 : 270) : mod((x > 0 ? 0 : 180) + atan(y / x), 360);
    }

    public float len(){
        return rt2(len2());
    }

    public float len2(){
        return x * x + y * y;
    }

    public boolean eql(Vec2 v){
        return eqlf(x, v.x) && eqlf(y, v.y);
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

    @Override
    public String toString(){
        return valToString(x, y);
    }
}
