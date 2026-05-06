package examples;

import jbsae.*;
import jbsae.core.*;
import jbsae.graphics.*;
import jbsae.graphics.gl.*;
import jbsae.util.*;

import static jbsae.JBSAE.*;

// Baseline: 46 fps
public class RenderTest{
    public static int layers = 5;
    public static float pixSize = 1f;

    public static void main(String[] args){
        jbsae(() -> {
            screen(new Screen(){
                public Texture trace;

                public float[] z;

                public void init(){
                    Log.info("Drawing " + (int)(width / pixSize + 1) * (int)(height / pixSize + 1) + " pixels");

                    Pixmap trace = new Pixmap(assets.textures.get("JBSAE.png"));
                    Pixmap buffer = new Pixmap(assets.textures.get("JBSAE.png"));
                    for(int i = 0;i < 2;i++){
                        trace.each(pos -> {
                            if(trace.get(pos).a > 0.2f){
                                for(int r = 0;r < 4;r++){
                                    int nx = pos.x + Mathf.d4x(r), ny = pos.y + Mathf.d4y(r);
                                    if(buffer.get(nx, ny).a <= 0.2f){
                                        buffer.set(nx, ny, Colorf.RED);
                                    }
                                }
                            }
                        });
                        buffer.each(pos -> trace.set(pos, buffer.get(pos)));
                    }
                    this.trace = trace.create();

                    z = new float[layers];
                    for(int i = 0;i < layers;i++) z[i] = i;
                }

                public void update(){
                }

                public void draw(){
                    trace.bind();
                    for(float x = 0;x < width;x += pixSize){
                        for(float y = 0;y < height;y += pixSize){
//                            Drawf.layer(Structf.choose(z));
                            Drawf.fill(Tmp.c1.hsv(x + y, 1f, 1f));
                            Drawf.rect(x, y, pixSize, pixSize);
                        }
                    }

                    Drawf.layer(1000);
                    Drawf.fill(Colorf.WHITE);
                    Drawf.rect(0, 0, 200, 200);
                }
            });
        });
    }
}
