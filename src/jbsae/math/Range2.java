package jbsae.math;

import static jbsae.util.Mathf.*;
import static jbsae.util.Stringf.*;

public class Range2{
    public float x, y, w, h;


    public Range2(){
    }

    public Range2(float x, float y, float w, float h){
        set(x, y, w, h);
    }

    public Range2(Range2 r){
        set(r);
    }


    public Range2 set(float x, float y, float w, float h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        return this;
    }

    public Range2 set(Range2 r){
        return set(r.x, r.y, r.w, r.h);
    }

    public Range2 nor(){
        return set(min(x, x + w), min(y, y + h), max(x, x + w), max(y, y + h));
    }

    public Range2 splt(int xParts, int yParts, int xi, int yi){
        return set(x + w * xi / xParts, y + h * yi / yParts, w / xParts, h / yParts);
    }

    public Range2 splt(int parts, int i){
        return splt(parts, parts, qx[i], qy[i]);
    }

    public Range2 expand(float amount){
        x -= amount;
        y -= amount;
        w += amount * 2;
        h += amount * 2;
        return this;
    }


    public Vec2 center(){
        return new Vec2(x + w / 2, y + h / 2);
    }

    public Vec2 constrain(Vec2 pos){
        return pos.set(clamp(pos.x, x, x + w), clamp(pos.y, y, y + h));
    }


    public Shape2 shape(Shape2 s){
        s.set(0, x, y).set(1, x + w, y).set(2, x + w, y + h).set(3, x, y + h);
        return s;
    }

    public Shape2 shape(){
        return shape(new Shape2(4));
    }


    public boolean contains(Pos2 value){
        return value.x() >= x && value.x() <= x + w && value.y() >= y && value.y() <= y + h;
    }

    public boolean overlaps(Range2 o){
        return o.x + o.w >= x && o.x <= x + w && o.y + o.h >= y && o.y <= y + h;
    }


    public Range2 cpy(){
        return new Range2(x, y, w, h);
    }


    @Override
    public String toString(){
        return valToString(x, y, w, h);
    }
}
