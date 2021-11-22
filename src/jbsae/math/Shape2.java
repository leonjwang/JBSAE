package jbsae.math;

public class Shape2{
    public Vec2[] v;

    public Shape2(){
    }

    public Shape2(float... coords){
        v = new Vec2[coords.length / 2];
        for(int i = 0;i < coords.length;i += 2) v[i / 2] = new Vec2(coords[i], coords[i + 1]);
    }

    public Shape2(Vec2... pos){
        Vec2[] v = new Vec2[pos.length];
        for(int i = 0;i < v.length;i ++) v[i] = new Vec2(pos[i]);
        this.v = v;
    }

    public Shape2 rot(float r){
        Vec2 c = center();
        for(int i = 0;i < v.length;i ++) v[i].add(-c.x, -c.y).rot(r).add(c);
        return this;
    }

    public Vec2 center(){
        Vec2 c = new Vec2();
        for(int i = 0;i < v.length;i ++) c.add(v[i]);
        c.scl(1f / v.length);
        return c;
    }
}
