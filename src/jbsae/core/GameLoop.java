package jbsae.core;

import jbsae.graphics.*;
import jbsae.graphics.gl.*;
import jbsae.math.*;
import jbsae.util.*;

import java.lang.reflect.*;

import static jbsae.JBSAE.*;
import static jbsae.util.Stringf.*;
import static jbsae.util.Colorf.*;
import static org.lwjgl.opengl.GL11.*;

/** @author Heiko Brumme */
public class GameLoop{
    public int rotation = 0;
    public Screen screen;
    public Texture texture1;

    public GameLoop(){
    }

    public void init(){
        texture1 = new Texture("assets/logo.png");
    }

    public void start(){
        long lastLoop = time.time();
        int frameTimer = 0, updateTimer = 0, loopTimer = 0;
        int lastFrames = 0, lastUpdates = 0;
        while(!window.shouldClose()){
            time.update();
            frameTimer += time.time - lastLoop;
            updateTimer += time.time - lastLoop;
            loopTimer += time.time - lastLoop;
            lastLoop = time.time;

            if(updateTimer >= (1000 / ups)){
                updateTimer %= (1000 / ups);
                time.updates++;
                window.update();
                rotation++;
            }
            if(frameTimer >= (1000 / fps)){
                frameTimer %= (1000 / fps);
                time.frames++;
                glClear(GL_COLOR_BUFFER_BIT);
                tempDraw();
                renderer.flush();
                window.swapBuffers();
            }
            if(loopTimer >= 1000){
                loopTimer %= 1000;
                printDebug("fps: " + (time.frames - lastFrames) + ", ups: " + (time.updates - lastUpdates));
                lastFrames = time.frames;
                lastUpdates = time.updates;
            }
            window.pollEvents();
        }
    }

    public void tempDraw(){
        texture1.bind();
        Colorf c = new Colorf();
        Field[] fields = c.getClass().getFields();
        int cnt0 = 0, cnt1 = 0, cnt2 = 0, cnt3 = 0;
        int w = 250, h = 75;

        for(int i = 0;i < 4;i++){
            try{
                Color r = (Color)fields[i].get(c);
                renderer.drawTexture(10, 10 + (cnt0++) * h, w, h, r);
            }catch(Exception ignored){
            }
        }
        for(int i = 4;i < fields.length;i++){
            try{
                Color r = (Color)fields[i].get(c);
                String name = fields[i].getName();
                if(name.length() > 5 && name.substring(0, 5).equals("light")) renderer.drawTexture(10 + w, 10 + (cnt1++) * h, w, h, r);
                else if(name.length() > 4 && fields[i].getName().substring(0, 4).equals("dark")) renderer.drawTexture(10 + w * 3, 10 + (cnt2++) * h, w, h, r);
                else renderer.drawTexture(10 + w * 2, 10 + (cnt3++) * h, w, h, r);
            }catch(Exception ignored){
            }
        }
        for(int i = 0;i < 360;i ++){
            renderer.drawTexture(10 + w * 4, 10 + i, w, h, (new Color().hsv(i, 1f, 1f).a(1)));
        }
    }
}
