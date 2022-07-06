package jbsae.util;

import jbsae.*;
import jbsae.graphics.*;
import jbsae.math.*;

import static jbsae.JBSAE.*;
import static jbsae.util.Colorf.*;
import static jbsae.util.Mathf.*;

public class Drawf{
    public static DrawSetting current = new DrawSetting(null);


    /** Set fill color. */
    public static void fill(){
        fill(white);
    }

    public static void fill(float v){
        current.fill.set(v, v, v, 1f);
    }

    public static void fill(float r, float g, float b){
        fill(r, g, b, 1f);
    }

    public static void fill(float r, float g, float b, float a){
        current.fill.set(r, g, b, a);
    }

    public static void fill(Color c){
        current.fill.set(c);
    }


    /** Draw a rectangle. */
    public static void rectc(float x, float y, float w, float h){
        rect(x - w / 2, y - h / 2, w, h, 0);
    }

    public static void rectc(float x, float y, float w, float h, float r){
        rect(x - w / 2, y - h / 2, w, h, r);
    }

    public static void rect(float x, float y, float w, float h){
        draw(Tmp.r1.set(x, y, w, h).shape(Tmp.s14));
    }

    public static void rect(float x, float y, float w, float h, float r){
        draw(Tmp.r1.set(x, y, w, h).shape(Tmp.s14).rot(r));
    }


    /** Draw a line. */
    public static void line(float x1, float y1, float x2, float y2){
        line(x1, y1, x2, y2, 1);
    }

    public static void line(float x1, float y1, float x2, float y2, float w){
        float a = Tmp.v1.set(x2, y2).sub(x1, y1).ang();
        draw(Tmp.s14.set(
        0, x1 + Tmp.v1.setr(w / 2, a + 90).x, y1 + Tmp.v1.y).set(
        1, x2 + Tmp.v1.setr(w / 2, a + 90).x, y2 + Tmp.v1.y).set(
        2, x2 + Tmp.v1.setr(w / 2, a - 90).x, y2 + Tmp.v1.y).set(
        3, x1 + Tmp.v1.setr(w / 2, a - 90).x, y1 + Tmp.v1.y));
    }

    public static void line(Region region, float x1, float y1, float x2, float y2){
        line(region, x1, y1, x2, y2, 1);
    }

    public static void line(Region region, float x1, float y1, float x2, float y2, float w){
        float a = Tmp.v1.set(x2, y2).sub(x1, y1).ang();
        draw(Tmp.s14.set(
        0, x1 + Tmp.v1.setr(w / 2, a + 90).x, y1 + Tmp.v1.y).set(
        1, x2 + Tmp.v1.setr(w / 2, a + 90).x, y2 + Tmp.v1.y).set(
        2, x2 + Tmp.v1.setr(w / 2, a - 90).x, y2 + Tmp.v1.y).set(
        3, x1 + Tmp.v1.setr(w / 2, a - 90).x, y1 + Tmp.v1.y), region);
    }


    /** Draw a texture region. */
    public static void drawc(Region region, float x, float y, float w, float h){
        draw(region, x - w / 2, y - h / 2, w, h);
    }

    public static void drawc(Region region, float x, float y, float w, float h, float r){
        draw(region, x - w / 2, y - h / 2, w, h, r);
    }

    public static void draw(Region region, float x, float y, float w, float h){
        draw(Tmp.r1.set(x, y, w, h).shape(Tmp.s14), region);
    }

    public static void draw(Region region, float x, float y, float w, float h, float r){
        draw(Tmp.r1.set(x, y, w, h).shape(Tmp.s14).rot(r), region);
    }


    public static void draw(Shape2 pos){
        draw(pos, Tmp.r1.set(0, 0, 1, 1).shape(Tmp.s24));
    }

    public static void draw(Shape2 pos, Region region){
        if(renderer.binded != region.texture) region.texture.bind();
        draw(pos, Tmp.r1.set(region.region).shape(Tmp.s24));
    }

    public static void draw(Shape2 pos, Shape2 region){
        renderer.draw(apply(pos), region, current.fill);
    }


    /** Set the font. */
    public static void font(Font font){
        current.font = font;
    }


    /** Draw text. */
    public static void text(String text, float x, float y){
        text(text, x, y, 10);
    }

    public static void text(String text, float x, float y, float size){
        float tx = 0, scl = size / current.font.size;
        for(int i = 0;i < text.length();i++){
            Glyph glyph = current.font.glyphs.get(text.charAt(i));
            if(glyph == null) glyph = current.font.none;
            draw(glyph.region, x + tx + glyph.xOffset * scl, y + size - (glyph.yOffset + glyph.height) * scl, glyph.width * scl, glyph.height * scl);
            tx += glyph.xAdvance * scl;
        }
    }


    /** Tmp.r1anslation functions. */
    public static void rotatet(float r){
        current.rotation = mod(r, 360);
    }

    public static void rotate(float r){
        current.rotation = mod(current.rotation + r, 360);
    }


    public static void scalet(float s){
        scalet(s, s);
    }

    public static void scalet(float x, float y){
        current.scale.set(x, y);
    }

    public static void scale(float s){
        scale(s, s);
    }

    public static void scale(float x, float y){
        current.scale.scl(x, y);
    }


    public static void translatet(float x, float y){
        current.translate.set(x, y);
    }

    public static void translate(float x, float y){
        current.translate.add(x, y);
    }


    /** Current setting manipulation. */
    public static void push(){
        current = current.cpy();
    }

    public static void pop(){
        current = current.parent;
    }


    public static Shape2 apply(Shape2 s){
        if(!zero(current.rotation)) s.rot(current.rotation);
        if(!current.scale.eql(Tmp.v1.set(1f, 1f))) s.scl(current.scale);
        if(!zero(current.translate.len())) s.add(current.translate);
        return s;
    }


    public static class DrawSetting{
        public DrawSetting parent;

        public Color fill = white.cpy();
        public float rotation = 0;
        public Vec2 scale = new Vec2(1, 1);
        public Vec2 translate = new Vec2(0, 0);
        public Font font;

        public DrawSetting(DrawSetting parent){
            this.parent = parent;
        }

        public DrawSetting cpy(){
            DrawSetting n = new DrawSetting(this);
            n.fill = fill.cpy();
            n.rotation = rotation;
            n.scale = scale.cpy();
            n.translate = translate.cpy();
            n.font = font;
            return n;
        }
    }
}
