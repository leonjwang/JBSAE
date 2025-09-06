package jbsae.core;

import jbsae.*;

import static jbsae.JBSAE.*;
import static org.lwjgl.opengl.GL11.*;


public class GameLoop{
    public int rotation = 0;
    public Screen screen;

    public GameLoop(){
    }

    public void init(){
    }

    public void start(){
        long lastLoop = time.millis();
        int frameTimer = 0, updateTimer = 0, loopTimer = 0;
        int lastFrames = 0, lastUpdates = 0;

        screen.init();
        while(window.keep()){
            time.update();
            frameTimer += time.time - lastLoop;
            updateTimer += time.time - lastLoop;
            loopTimer += time.time - lastLoop;
            lastLoop = time.time;

            if(updateTimer >= (1000 / ups)){
                updateTimer %= (1000 / ups);
                time.updates++;
                window.update();
                screen.update();
                rotation++;
            }
            if(frameTimer >= (1000 / fps)){
                renderer.binded = null;
                frameTimer %= (1000 / fps);
                time.frames++;
                glClear(GL_COLOR_BUFFER_BIT);
                screen.draw();
                draw.render();
                renderer.flush();
                window.swap();
            }
            if(loopTimer >= 1000){
                loopTimer %= 1000;
                time.cfps = time.frames - lastFrames;
                time.cups = time.updates - lastUpdates;
                lastFrames = time.frames;
                lastUpdates = time.updates;
                Log.info("FPS: " + time.cfps + " UPS: " + time.cups);
            }
            window.poll();
        }
    }
}
