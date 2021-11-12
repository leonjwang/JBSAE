package jbsae.math;

public class Vec2{
    public float x, y;

    public Vec2(){
    }

    public Vec2(float x, float y){
        this.x = x;
        this.y = y;
    }



    @Override
    public String toString(){
        return "(" + x + ", " + y + ")";
    }
}
