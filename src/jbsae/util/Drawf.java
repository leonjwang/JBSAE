package jbsae.util;

import jbsae.graphics.*;
import jbsae.graphics.gl.*;
import jbsae.math.*;
import jbsae.struct.*;

import static jbsae.JBSAE.*;
import static jbsae.util.Colorf.*;

public class Drawf{
    public static Queue<DrawSetting> settingQueue = new Queue<>();
    public static Range2 tr = new Range2();
    public static Shape2 ts1 = new Shape2(4), ts2 = new Shape2(4);


    /** Set fill color. */
    public static void fill(Color c){
        current().fill.set(c);
    }

    public static void fill(float... c){
        if(c.length == 1) current().fill.set(c[0], c[0], c[0], 1f);
        else if(c.length == 3) current().fill.set(c[0], c[1], c[2], 1f);
        else if(c.length == 4) current().fill.set(c[0], c[1], c[2], c[3]);
        else current().fill.set(white);
    }


    /** Draw a rectangle. */
    public static void rectc(float x, float y, float w, float h){
        rectc(x, y, w, h, 0);
    }

    public static void rectc(float x, float y, float w, float h, float r){
        rect(x - w / 2, y - h / 2, w, h, r);
    }

    public static void rect(float x, float y, float w, float h){
        rect(x, y, w, h, 0);
    }

    public static void rect(float x, float y, float w, float h, float r){
        renderer.draw(apply(tr.set(x, y, w, h).toShape(ts1).rot(r)), tr.set(0, 0, 1, 1).toShape(ts2), current().fill);
    }


    /** Translation functions. */
    public static void rotatet(float r){
        current().rotation = r;
    }

    public static void rotate(float r){
        current().rotation += r;
    }


    public static void scalet(float s){
        scalet(s, s);
    }

    public static void scalet(float x, float y){
        current().scale.set(x, y);
    }

    public static void scale(float s){
        scale(s, s);
    }

    public static void scale(float x, float y){
        current().scale.scl(x, y);
    }


    public static void translatet(float x, float y){
        current().translate.set(x, y);
    }

    public static void translate(float x, float y){
        current().translate.add(x, y);
    }


    /** Current setting manipulation. */
    public static void push(){
        settingQueue.addLast(current() == null ? new DrawSetting() : current().cpy());
    }

    public static void pop(){
        settingQueue.removeLast();
    }


    public static DrawSetting current(){
        return settingQueue.last();
    }

    public static Shape2 apply(Shape2 s){
        return s.rot(current().rotation).scl(current().scale).add(current().translate);
    }


    public static class DrawSetting{
        public Color fill = white.cpy();
        public float rotation = 0;
        public Vec2 scale = new Vec2(1, 1);
        public Vec2 translate = new Vec2(0, 0);

        public DrawSetting cpy(){
            DrawSetting n = new DrawSetting();
            n.fill = fill.cpy();
            n.rotation = rotation;
            n.scale = scale.cpy();
            n.translate = translate.cpy();
            return n;
        }
    }
}
