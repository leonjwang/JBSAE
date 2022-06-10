package testers;


import jbsae.*;
import jbsae.core.*;
import jbsae.files.*;
import jbsae.graphics.*;
import jbsae.graphics.gl.*;
import jbsae.math.*;
import jbsae.math.interp.*;
import jbsae.struct.*;
import jbsae.util.*;

import java.lang.reflect.*;

import static jbsae.JBSAE.*;
import static jbsae.util.Colorf.*;
import static jbsae.util.Drawf.*;
import static jbsae.util.Mathf.*;

public class InterpDisplay extends Screen{
    public static float displaySize = 150;
    public static float exampleSize = 5;
    public static float exampleTime = 2000;
    public static float boxSize = 80;
    public static float boxOutline = 2;
    public static float boxSpacing = 100;
    public static float lineSize = 2;
    public static float linePercision = 0.02f;
    public static float hoverSize = 1.1f;

    public Seq<Interp> interpSeq = new Seq<>();
    public Seq<String> nameSeq = new Seq<>();
    public Color boxColor = new Color();
    public Texture box;
    public Vec2 tmp = new Vec2();
    public Range2 tmp2 = new Range2();
    public String lastOver = "";
    public float lastExamplePos = 0;

    @Override
    public void init(){
        box = new TextureFile("assets/sprites/effects/square.png").load().texture;

        Interpf tmp = new Interpf();
        try{
            for(Field f : Interpf.class.getDeclaredFields()){
                interpSeq.add((Interp)f.get(tmp));
                nameSeq.add(f.getName());
            }
        }catch(Exception e){
        }
    }

    @Override
    public void draw(){
        box.bind();
        fill(black);
        rect(0, 0, width, height);
        int max = (int)(width / boxSpacing);
        for(int i = 0;i < interpSeq.size;i++){
            float tx = (i % max + 0.5f) * boxSpacing, ty = (i / max + 0.5f) * boxSpacing + input.scroll * -30;
            boxColor.hsv(time.millis() / 10 + tx / 20 + ty / 10, 1, 1);

            push();
            translate(tx, ty);

            tmp2.set(tx - boxSize / 2, ty - boxSize / 2, boxSize, boxSize);
            if(tmp2.contains(input.mouse)){
                if(!nameSeq.get(i).equals(lastOver)){
                    lastOver = nameSeq.get(i);
                    lastExamplePos = interpSeq.get(i).get(time.millis() % exampleTime / exampleTime) - 0.5f;
                    System.out.println(nameSeq.get(i));
                }
                scale(hoverSize);
            }

            fill(boxColor);
            rectc(0, 0, boxSize, boxSize);
            fill(black);
            rectc(0, 0, boxSize - boxOutline * 2, boxSize - boxOutline * 2);
            fill(boxColor);

            scale(0.9f * boxSize);
            float lasty = interpSeq.get(i).get(0);
            for(float x = linePercision;x < 1;x += linePercision){
                line(x - 0.5f, interpSeq.get(i).get(x) - 0.5f, x - linePercision - 0.5f, lasty - 0.5f, lineSize / boxSize);
                lasty = interpSeq.get(i).get(x);
            }
            pop();
        }

        for(int i = 0;i < interpSeq.size;i++){
            float tx = (i % max + 0.5f) * boxSpacing, ty = (i / max + 0.5f) * boxSpacing + input.scroll * -30;
            boxColor.hsv(time.millis() / 10 + tx / 20 + ty / 10, 1, 1);

            tmp2.set(tx - boxSize / 2, ty - boxSize / 2, boxSize, boxSize);
            if(tmp2.contains(input.mouse)){
                push();
                float rx = clamp(input.mouse.x, displaySize / 2f, width - displaySize / 2f);
                translatet(rx, input.mouse.y + displaySize / 10f);
                fill(boxColor);
                rectc(0, 0, displaySize, displaySize / 5f);
                fill(black);
                rectc(0, 0, displaySize - boxOutline * 2, displaySize / 5f - boxOutline * 2);
                scale(0.9f * displaySize);

                fill(boxColor);
                float curPos = interpSeq.get(i).get(time.millis() % exampleTime / exampleTime) - 0.5f;
                if(lastExamplePos - curPos > 0.3f) lastExamplePos = -0.5f;
                line(curPos, 0f, lastExamplePos, 0f, exampleSize / displaySize);
                rectc(curPos, 0f, exampleSize / displaySize, exampleSize / displaySize);
                lastExamplePos = interpSeq.get(i).get(time.millis() % exampleTime / exampleTime) - 0.5f;

                pop();
            }
        }
    }

    public void line(float x1, float y1, float x2, float y2, float w){
        tmp.set(x2 - x1, y2 - y1);
        rectc((x1 + x2) / 2f, (y1 + y2) / 2f, w, tmp.len(), tmp.ang() + 90);
    }

    public static void main(String[] args){
        JBSAE.width = 400;
        JBSAE.debug = false;

        JBSAE.init();
        JBSAE.screen(new InterpDisplay());
        JBSAE.start();
        JBSAE.dispose();
    }
}
