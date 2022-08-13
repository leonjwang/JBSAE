package jbsae.math;

import static jbsae.util.Mathf.*;

public class Ang2 implements Pos2{
    public float a, l;

    public Ang2(){
    }

    public Ang2(float a, float l){
        set(a, l);
    }

    public Ang2(Ang2 v){
        set(v);
    }

    public Ang2(Pos2 v){
        setc(v);
    }


    public Ang2 set(float a, float l){
        this.a = a;
        this.l = l;
        return this;
    }

    public Ang2 set(Ang2 v){
        return set(v.a, v.l);
    }

    public Ang2 setc(float x, float y){
        this.a = angle(x, y);
        this.l = dst(0, 0, x, y);
        return this;
    }

    public Ang2 setc(Pos2 v){
        return setc(v.x(), v.y());
    }


    @Override
    public float x(){
        return cos(a) * l;
    }

    @Override
    public float y(){
        return sin(a) * l;
    }
}
