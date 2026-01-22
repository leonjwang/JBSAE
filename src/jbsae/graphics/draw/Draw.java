package jbsae.graphics.draw;


import jbsae.*;
import jbsae.graphics.*;
import jbsae.math.*;
import jbsae.struct.*;

import static jbsae.JBSAE.*;
import static jbsae.util.Colorf.*;
import static jbsae.util.Mathf.*;

public class Draw{
    public Font font;
    public Color fill = WHITE.cpy();


    public void draw(Region region, float x, float y, float w, float h){
        drawi(region, x, y, x + w, y, x + w, y + h, x, y + h);
    }

    public void draw(Region region, float x, float y, float w, float h, float r){
        if(zero(r)) drawi(region, x, y, x + w, y, x + w, y + h, x, y + h);
        else{
            float cx = x + w / 2f, cy = y + h / 2f;

            float x1 = x, y1 = y;
            float x2 = x + w, y2 = y;
            float x3 = x + w, y3 = y + h;
            float x4 = x, y4 = y + h;

            float cos = cos(r), sin = sin(r);

            float nx1 = cos * x1 - sin * y1;
            y1 = cos * y1 + sin * x1;
            x1 = nx1;

            float nx2 = cos * x2 - sin * y2;
            y2 = cos * y2 + sin * x2;
            x2 = nx2;

            float nx3 = cos * x3 - sin * y3;
            y3 = cos * y3 + sin * x3;
            x3 = nx3;

            float nx4 = cos * x4 - sin * y4;
            y4 = cos * y4 + sin * x4;
            x4 = nx4;

            drawi(region, x1, y1, x2, y2, x3, y3, x4, y4);
        }
    }

    public void draw(Region region, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4){
        drawi(region, x1, y1, x2, y2, x3, y3, x4, y4);
    }

    public void drawi(Region region, float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4){
        if(region != null && renderer.binded != region.texture) region.texture.bind();

        Range2 r = region != null ? region.region : Tmp.r1.set(0, 0, 1, 1);
        renderer.draw(x1, y1, x2, y2, x3, y3, x4, y4,
        r.x, r.y, r.x + r.w, r.y, r.x + r.w, r.y + r.h, r.x, r.y + r.h,
        fill.r, fill.g, fill.b, fill.a);
    }


    public void render(){
    }
}
