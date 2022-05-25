package jbsae.core;

import static jbsae.JBSAE.*;
import static jbsae.util.Drawf.*;
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
                frameTimer %= (1000 / fps);
//                System.out.println(fps);
                time.frames++;
                glClear(GL_COLOR_BUFFER_BIT);
                screen.draw();
                renderer.flush();
                window.swap();
            }
            if(loopTimer >= 1000){
                loopTimer %= 1000;
                time.cfps = time.frames - lastFrames;
                time.cups = time.updates - lastUpdates;
                lastFrames = time.frames;
                lastUpdates = time.updates;
                System.out.println("FPS: " + time.cfps + "UPS: " + time.cups);
            }
            window.poll();
        }
    }
}
