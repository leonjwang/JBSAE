package jbsae.util;

import jbsae.*;
import jbsae.graphics.*;
import jbsae.math.*;
import jbsae.struct.*;

import static jbsae.JBSAE.*;
import static jbsae.util.Colorf.*;

public class Drawf{
    public static Queue<DrawSetting> settings = new Queue<>();


    /** Set fill color. */
    public static void fill(){
        fill(white);
    }

    public static void fill(float v){
        current().fill.set(v, v, v, 1f);
    }

    public static void fill(float r, float g, float b){
        fill(r, g, b, 1f);
    }

    public static void fill(float r, float g, float b, float a){
        current().fill.set(r, g, b, a);
    }

    public static void fill(Color c){
        current().fill.set(c);
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
        renderer.draw(apply(Tmp.r1.set(x, y, w, h).shape(Tmp.s14).rot(r)), Tmp.r1.set(0, 0, 1, 1).shape(Tmp.s24), current().fill);
    }


    /** Tmp.r1anslation functions. */
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
        settings.addLast(current() == null ? new DrawSetting() : current().cpy());
    }

    public static void pop(){
        settings.removeLast();
    }


    public static DrawSetting current(){
        return settings.last();
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
