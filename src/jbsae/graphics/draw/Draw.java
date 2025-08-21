package jbsae.graphics.draw;


import jbsae.*;
import jbsae.graphics.*;
import jbsae.graphics.draw.DrawMatrix.*;
import jbsae.math.*;
import jbsae.struct.*;

import static jbsae.JBSAE.*;
import static jbsae.util.Colorf.*;
import static jbsae.util.Mathf.zero;

public class Draw{
    public Font font;
    public Color fill = WHITE.cpy();
    public Queue<DrawMatrix> matrixes = new Queue<>();


    public void draw(Region region, float x, float y, float w, float h){
        drawi(region, x, y, x + w, y, x + w, y + h, x, y + h);
    }

    public void draw(Region region, float x, float y, float w, float h, float r){
        Tmp.r1.set(x, y, w, h).shape(Tmp.s14);
        if(!zero(r)) Tmp.s14.rot(r);
        drawi(region, Tmp.s14.v[0].x, Tmp.s14.v[0].y, Tmp.s14.v[1].x, Tmp.s14.v[1].y, Tmp.s14.v[2].x, Tmp.s14.v[2].y, Tmp.s14.v[3].x, Tmp.s14.v[3].y);
    }

    public void draw(Region region, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4){
        drawi(region, x1, y1, x2, y2, x3, y3, x4, y4);
    }

    public void drawi(Region region, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4){
        if(region != null && renderer.binded != region.texture) region.texture.bind();

        if(matrixes.size > 0){
            for(DrawMatrix matrix : matrixes){
                for(Transformation transformation : matrix.transformations){
                    x1 = transformation.x(x1);
                    y1 = transformation.y(y1);
                    x2 = transformation.x(x2);
                    y2 = transformation.y(y2);
                    x3 = transformation.x(x3);
                    y3 = transformation.y(y3);
                    x4 = transformation.x(x4);
                    y4 = transformation.y(y4);
                }
            }
        }

        Range2 r = region != null ? region.region : Tmp.r1.set(0, 0, 1, 1);
        renderer.draw(x1, y1, x2, y2, x3, y3, x4, y4,
        r.x, r.y, r.x + r.w, r.y, r.x + r.w, r.y + r.h, r.x, r.y + r.h,
        fill.r, fill.g, fill.b, fill.a);
    }


    public void render(){
    }
}
