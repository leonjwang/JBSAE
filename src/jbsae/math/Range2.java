package jbsae.math;

import static jbsae.util.Mathf.*;

public class Range2{
    public float x, y, w, h;

    public Range2(){
    }

    public Range2(float x, float y, float w, float h){
        set(x, y, w, h);
    }

    public Range2 set(float x, float y, float w, float h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        return this;
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

    public Vec2 center(){
        return new Vec2(x + w, y + h).scl(0.5f);
    }

    public Shape2 toShape(Shape2 s){
        s.set(x, y, x + w, y, x + w, y + h, x, y + h);
        return s;
    }

    public Shape2 shape(){
        return toShape(new Shape2(4));
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
}
