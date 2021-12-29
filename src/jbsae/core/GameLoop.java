package jbsae.core;

import jbsae.graphics.*;
import jbsae.graphics.gl.*;
import jbsae.math.*;
import jbsae.util.*;

import java.lang.reflect.*;

import static jbsae.JBSAE.*;
import static jbsae.util.Drawf.*;
import static jbsae.util.Stringf.*;
import static jbsae.util.Colorf.*;
import static org.lwjgl.opengl.GL11.*;

/** @author Heiko Brumme */
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
        push();
        screen.init();
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
                screen.update();
                rotation++;
            }
            if(frameTimer >= (1000 / fps)){
                frameTimer %= (1000 / fps);
                time.frames++;
                glClear(GL_COLOR_BUFFER_BIT);
                screen.draw();
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
