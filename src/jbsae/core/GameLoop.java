package jbsae.core;

import jbsae.graphics.*;
import jbsae.graphics.gl.*;
import jbsae.util.*;
import org.lwjgl.system.*;

import java.lang.reflect.*;
import java.nio.*;

import static jbsae.JBSAE.*;
import static jbsae.util.Stringf.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class GameLoop{
    public int rotation = 0;
    public Screen screen;
    public Texture texture1;

    public GameLoop(){
    }

    public void init(){
        texture1 = Texture.loadTexture("assets/sprites/effects/square.png");
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
                texture1.bind();
                Colorf c = new Colorf();
                Field[] fields = c.getClass().getFields();
                int cnt1 = 0, cnt2 = 0, cnt3 = 0;
                for(int i = 4;i < fields.length;i++){
                    try{
                        Color r = (Color)fields[i].get(c);
                        String name = fields[i].getName();
                        if(name.length() > 5 && name.substring(0, 5).equals("light")) renderer.drawTexture(texture1, 10, (cnt1++) * 40, r);
                        else if(name.length() > 5 && fields[i].getName().substring(0, 5).equals("dark")) renderer.drawTexture(texture1, 90, (cnt2++) * 40, r);
                        else renderer.drawTexture(texture1, 50, (cnt3++) * 40, r);
                    }catch(Exception e){
                    }
                }
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
}
