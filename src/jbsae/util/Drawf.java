package jbsae.util;

import jbsae.*;
import jbsae.graphics.*;
import jbsae.graphics.draw.*;
import jbsae.graphics.gl.*;
import jbsae.math.*;

import static jbsae.JBSAE.*;
import static jbsae.util.Colorf.*;

public class Drawf{
    /** Set fill color. */
    public static void fill(){
        fill(white);
    }

    public static void fill(float v){
        draw.fill.set(v, v, v, 1f);
    }

    public static void fill(float r, float g, float b){
        draw.fill.set(r, g, b, 1f);
    }

    public static void fill(float r, float g, float b, float a){
        draw.fill.set(r, g, b, a);
    }

    public static void fill(Color c){
        draw.fill.set(c);
    }

    public static void fill(Color c, float a){
        draw.fill.set(c).a(a);
    }


    public static void alpha(float a){
        draw.fill.a(a);
    }


    public static void layer(float z){
        ((SortedDraw)draw).z = z;
    }


    /** Draw a rectangle. */
    public static void rectc(float x, float y, float w, float h){
        rect(x - w / 2, y - h / 2, w, h, 0);
    }

    public static void rectc(float x, float y, float w, float h, float r){
        rect(x - w / 2, y - h / 2, w, h, r);
    }

    public static void rect(float x, float y, float w, float h){
        draw.draw(null, x, y, w, h);
    }

    public static void rect(float x, float y, float w, float h, float r){
        draw.draw(null, x, y, w, h, r);
    }


    /** Draw a line. */
    public static void line(float x1, float y1, float x2, float y2){
        line(x1, y1, x2, y2, 1);
    }

    public static void line(float x1, float y1, float x2, float y2, float w){
        line(null, x1, y1, x2, y2, w);
    }

    public static void line(Region region, float x1, float y1, float x2, float y2){
        line(region, x1, y1, x2, y2, 1);
    }

    public static void line(Region region, float x1, float y1, float x2, float y2, float w){
        float len = Tmp.v1.set(x2, y2).sub(x1, y1).len(), ang = Tmp.v1.ang();
        drawc(region, (x1 + x2) / 2, (y1 + y2) / 2, len, w, ang);
    }


    /** Draw a texture region. */
    public static void drawc(Region region, float x, float y, float w, float h){
        draw(region, x - w / 2, y - h / 2, w, h);
    }

    public static void drawc(Region region, float x, float y, float w, float h, float r){
        draw(region, x - w / 2, y - h / 2, w, h, r);
    }

    public static void draw(Region region, float x, float y, float w, float h){
        draw.draw(region, x, y, w, h);
    }

    public static void draw(Region region, float x, float y, float w, float h, float r){
        draw.draw(region, x, y, w, h, r);
    }


    /** Set the font. */
    public static void font(Font font){
        draw.font = font;
    }


    /** Draw text. */
    public static void text(String text, float x, float y){
        text(text, x, y, 10);
    }

    public static void text(String text, float x, float y, float size){
        float tx = 0, ty = 0, scl = size / draw.font.size;
        for(int i = 0;i < text.length();i++){
            if(text.charAt(i) == '\n'){
                ty -= size;
                tx = 0;
                continue;
            }
            Glyph glyph = draw.font.glyphs.get(text.charAt(i));
            if(glyph == null) glyph = draw.font.none;
            draw(glyph.region, x + tx + glyph.xOffset * scl, y + ty + size - (glyph.yOffset + glyph.height) * scl, glyph.width * scl, glyph.height * scl);
            tx += glyph.xAdvance * scl;
        }
    }


    /** Translation functions. */
    public static void rotate(float r){
        draw.matrixes.first().rotate(r);
    }

    public static void scale(float s){
        scale(s, s);
    }

    public static void scale(float x, float y){
        draw.matrixes.first().scale(x, y);
    }

    public static void translate(float x, float y){
        draw.matrixes.first().translate(x, y);
    }



    /** Current setting manipulation. */
    public static void push(){
        draw.matrixes.addFirst(new DrawMatrix());
    }

    public static void pop(){
        draw.matrixes.removeFirst();
    }

//
//
//    /** Push everything to the screen. */
//    public static void flush(){
//        requests.sort(r -> r.z);
//        for(DrawRequest r : requests){
//            if(renderer.binded != r.texture) r.texture.bind();
//            Tmp.s14.set(0, r.x1, r.y1).set(1, r.x2, r.y2).set(2, r.x3, r.y3).set(3, r.x4, r.y4);
//            Tmp.s24.set(0, r.rx1, r.ry1).set(1, r.rx2, r.ry2).set(2, r.rx3, r.ry3).set(3, r.rx4, r.ry4);
//            renderer.draw(Tmp.s14, Tmp.s24, Tmp.c1.set(r.r, r.g, r.b, r.a));
//        }
//        requests.clear();
//    }
//
//
}
