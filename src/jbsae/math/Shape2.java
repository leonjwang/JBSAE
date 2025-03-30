package jbsae.math;

import jbsae.*;

import static jbsae.util.Stringf.*;

public class Shape2{
    public Vec2[] v;


    public Shape2(){
    }

    public Shape2(int n){
        v = new Vec2[n];
        for(int i = 0;i < n;i++) v[i] = new Vec2();
    }

    public Shape2(float... coords){
        this(coords.length / 2);
        for(int i = 0;i < coords.length;i += 2) v[i / 2] = new Vec2(coords[i], coords[i + 1]);
    }

    public Shape2(Pos2... pos){
        for(int i = 0;i < v.length;i++) v[i] = new Vec2(pos[i].x(), pos[i].y());
    }


    public Shape2 set(int i, float x, float y){
        v[i].set(x, y);
        return this;
    }

    public Shape2 add(Pos2 v){
        return add(v.x(), v.y());
    }

    public Shape2 add(float x, float y){
        for(int i = 0;i < v.length;i++) v[i].add(x, y);
        return this;
    }

    public Shape2 scl(float x, float y){
        for(int i = 0;i < v.length;i++) v[i].scl(x, y);
        return this;
    }

    public Shape2 scl(int s){
        return scl(s, s);
    }

    public Shape2 scl(Pos2 v){
        return scl(v.x(), v.y());
    }

    public Shape2 sclc(Pos2 v){
        return sclc(v.x(), v.y());
    }

    public Shape2 sclc(float x, float y){
        Vec2 c = center(Tmp.v1);
        for(int i = 0;i < v.length;i++) v[i].add(-c.x, -c.y).scl(x, y).add(c);
        return this;
    }

    public Shape2 rot(float r){
        Vec2 c = center(Tmp.v1);
        for(int i = 0;i < v.length;i++) v[i].add(-c.x, -c.y).rot(r).add(c);
        return this;
    }

    public Shape2 rot(float r, float x, float y){
        for(int i = 0;i < v.length;i++) v[i].add(-x, -y).rot(r).add(x, y);
        return this;
    }


    public Vec2 center(Vec2 c){
        c.set(0, 0);
        for(int i = 0;i < v.length;i++) c.add(v[i]);
        c.scl(1f / v.length);
        return c;
    }

    public Vec2 center(){
        return center(new Vec2());
    }


    @Override
    public String toString(){
        return arrToString(v);
    }
}
