package jbsae.math;

import static jbsae.util.Stringf.*;

public class Point2 implements Pos2{
    public int x, y;


    public Point2(){
    }

    public Point2(int x, int y){
        set(x, y);
    }

    public Point2(Point2 v){
        set(v);
    }

    public Point2(Pos2 v){
        set((int)v.x(), (int)v.y());
    }


    public Point2 set(int x, int y){
        this.x = x;
        this.y = y;
        return this;
    }

    public Point2 set(Point2 v){
        return set(v.x, v.y);
    }

    public Point2 set(Pos2 v){
        return set((int)v.x(), (int)v.y());
    }

    public Point2 add(int x, int y){
        this.x += x;
        this.y += y;
        return this;
    }

    public Point2 add(Point2 v){
        return add(v.x, v.y);
    }

    public Point2 add(Pos2 v){
        return add((int)v.x(), (int)v.y());
    }

    public Point2 sub(int x, int y){
        return add(-x, -y);
    }

    public Point2 sub(Point2 v){
        return sub(v.x, v.y);
    }

    public Point2 sub(Pos2 v){
        return sub((int)v.x(), (int)v.y());
    }

    public Point2 scl(float x, float y){
        this.x *= x;
        this.y *= y;
        return this;
    }

    public Point2 scl(Point2 v){
        return scl(v.x, v.y);
    }

    public Point2 scl(Pos2 v){
        return scl(v.x(), v.y());
    }

    public Point2 scl(float v){
        return scl(v, v);
    }


    public Point2 cpy(){
        return new Point2(this);
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
