package examples;

import jbsae.*;
import jbsae.core.*;
import jbsae.graphics.gl.*;
import jbsae.math.interp.*;
import jbsae.struct.*;
import jbsae.util.*;

import java.lang.reflect.*;

import static jbsae.JBSAE.*;

// TODO: Currently sorted draw has behavior differing with normal draw due to how null and binding textures work. Theoretically, we shouldn't even support this form of drawing, however
public class InterpVisualizer extends Screen{
    public Texture box;

    public static final int BOX_SIZE = 100;
    public static final int DISPLAY_SIZE = 90;
    public static final int STROKE_WIDTH = 1;
    public static final int TEXT_SIZE = 15;
    public static final float MOUSE_OVER_ZOOM = 1.1f;
    public static final int EXAMPLE_TIME_FRAMES = 120;
    public static final float EXAMPLE_SCALE = 0.9f;
    public static final int EXAMPLE_WIDTH = 100;
    public static final int EXAMPLE_HEIGHT = 30;

    public static final int INTERP_STATES = 100;

    public static final float SCROLL_SCALE = -30f;

    public Seq<Interp> interps = new Seq<>();
    public Seq<String> names = new Seq<>();

    @Override
    public void init(){
        Interpf tmp = new Interpf();
        try{
            for(Field f : Interpf.class.getDeclaredFields()){
                interps.add((Interp)f.get(tmp));
                names.add(f.getName());
            }
        }catch(Exception e){
        }

        box = assets.textures.get("square.png");

        Drawf.font(assets.fonts.get("brandbe.fnt"));
    }

    @Override
    public void draw(){
        int x = 0;
        int y = 0;

        int boxes = width / BOX_SIZE;
        float tx = (width - boxes * BOX_SIZE) / 2f;
        float ty = input.scroll * SCROLL_SCALE;

        Drawf.fill(Colorf.WHITE);
        for(int i = 0;i < interps.size;i++){
            Interp interp = interps.get(i);

            if((x + BOX_SIZE) > width){
                x = 0;
                y += BOX_SIZE;
            }

            float rx = x + tx + BOX_SIZE / 2f - DISPLAY_SIZE / 2f;
            float ry = y + ty + BOX_SIZE / 2f - DISPLAY_SIZE / 2f;

            float realSize = DISPLAY_SIZE;

            boolean mouseOver = Tmp.r1.set(rx, ry, DISPLAY_SIZE, DISPLAY_SIZE).contains(input.mouse);
            if(mouseOver){
                realSize *= MOUSE_OVER_ZOOM;

                float v1 = (interp.get((float)(time.frames % EXAMPLE_TIME_FRAMES) / EXAMPLE_TIME_FRAMES) - 0.5f) * EXAMPLE_SCALE;
                float v2 = (interp.get((float)((time.frames + 1) % EXAMPLE_TIME_FRAMES) / EXAMPLE_TIME_FRAMES) - 0.5f) * EXAMPLE_SCALE;
                if(time.frames % EXAMPLE_TIME_FRAMES == EXAMPLE_TIME_FRAMES - 1) v2 = v1;


                Drawf.layer(1f);
                Drawf.fill(0f, 0f, 0f, 0.5f);
                Drawf.draw(box.full, input.mouse.x - EXAMPLE_WIDTH / 2f, input.mouse.y, EXAMPLE_WIDTH, EXAMPLE_HEIGHT);
                float x1 = v1 * DISPLAY_SIZE + input.mouse.x, x2 = v2 * DISPLAY_SIZE + input.mouse.x;
                Drawf.fill(1f, 1f, 1f);
                Drawf.box(box.full, input.mouse.x - EXAMPLE_WIDTH / 2f, input.mouse.y, EXAMPLE_WIDTH, EXAMPLE_HEIGHT);
                Drawf.line(box.full, x1, input.mouse.y + EXAMPLE_HEIGHT / 2f, x2, input.mouse.y + EXAMPLE_HEIGHT / 2f, STROKE_WIDTH * 2);
                Drawf.drawc(box.full, x1, input.mouse.y + EXAMPLE_HEIGHT / 2f, STROKE_WIDTH * 2, STROKE_WIDTH * 2);
                Drawf.layer(0);

            }

            rx = x + tx + BOX_SIZE / 2f - realSize / 2f;
            ry = y + ty + BOX_SIZE / 2f - realSize / 2f;
            Drawf.box(box.full, rx, ry, realSize, realSize);

            for(int v = 0;v < INTERP_STATES;v++){
                float v1 = (float)v / INTERP_STATES;
                float v2 = (float)(v + 1) / INTERP_STATES;

                float f1 = interp.get(v1), f2 = interp.get(v2);

                float x1 = v1 * realSize + rx, x2 = v2 * realSize + rx;
                float y1 = f1 * realSize + ry, y2 = f2 * realSize + ry;

                Drawf.line(box.full, x1, y1, x2, y2, STROKE_WIDTH);
            }

            Drawf.text(names.get(i), rx + STROKE_WIDTH * 2, ry + STROKE_WIDTH * 2, TEXT_SIZE);

            x += BOX_SIZE;
        }
    }

    public static void main(String[] args){
        jbsae(new InterpVisualizer());
    }
}
