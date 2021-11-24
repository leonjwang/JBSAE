package jbsae.math;

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

    public Shape2 toShape(Shape2 s){
        s.set(x, y, x + w, y, x + w, y + h, x, y + h);
        return s;
    }

    public Shape2 shape(){
        return toShape(new Shape2(4));
    }
}
