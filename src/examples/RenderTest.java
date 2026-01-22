package examples;

import jbsae.*;
import jbsae.Log.*;
import jbsae.core.*;
import jbsae.core.loop.*;
import jbsae.math.*;
import jbsae.struct.*;
import jbsae.struct.tree.*;
import jbsae.util.*;

import static jbsae.JBSAE.*;
import static jbsae.util.Mathf.*;

// Baseline: 55 fps
public class RenderTest{
    public static final float pixSize = 2f;

    public static void main(String[] args){
        jbsae(() -> {
            screen(new Screen(){
                public void init(){
                    Log.info("Drawing " + (int)(width / pixSize + 1) * (int)(height / pixSize + 1) + " pixels");
                }

                public void update(){
                }

                public void draw(){
                    assets.textures.get("square.png").bind();

                    for(float x = 0;x < width;x += pixSize){
                        for(float y = 0;y < height;y += pixSize){
                            Drawf.fill(Tmp.c1.hsv(x + y, 1f, 1f));
                            Drawf.rect(x, y, pixSize, pixSize);
                        }
                    }
                }
            });
        });
    }
}
