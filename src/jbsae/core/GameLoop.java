package jbsae.core;

import jbsae.*;

import java.util.concurrent.locks.*;

import static jbsae.JBSAE.*;
import static org.lwjgl.opengl.GL11C.*;


public class GameLoop{
    public Screen screen;

    public GameLoop(){
    }

    public void init(){
    }

    public final int MAX_UPDATES_PER_FRAME = 5;

    public void start(){
        long updateInterval = 1_000_000_000L / fps;
        long nextTick = System.nanoTime();

        // TODO: loopTimer is a bit scuffed
        long loopTimer = 0, lastLoop = System.nanoTime();
        int lastFrames = 0, lastUpdates = 0;

        screen.init();
        while(window.keep()){
            long now = System.nanoTime();
            for(int updates = 0;now >= nextTick && updates < MAX_UPDATES_PER_FRAME;updates++){
                update();
                // TODO: Delta
                nextTick += updateInterval;
                now = System.nanoTime();
            }
            render();
            sounds.update();

            loopTimer += now - lastLoop;
            lastLoop = System.nanoTime();
            if(loopTimer >= 1_000_000_000L){
                loopTimer %= 1_000_000_000L;
                time.cfps = time.frames - lastFrames;
                time.cups = time.updates - lastUpdates;
                lastFrames = time.frames;
                lastUpdates = time.updates;
                Log.info("FPS: " + time.cfps + " UPS: " + time.cups);
                Log.info("Sources: " + sounds.sources.size);
            }

            long sleepNs = nextTick - System.nanoTime();
            if(sleepNs > 2_000_000L) LockSupport.parkNanos(sleepNs - 1_000_000L);
            while(System.nanoTime() < nextTick) Thread.onSpinWait();

            window.poll();
        }
    }

    public void update(){
        time.updates++;
        window.update();
        screen.update();
    }

    public void render(){
        time.frames++;
        renderer.binded = null;
        glClear(GL_COLOR_BUFFER_BIT);
        screen.draw();
        draw.render();
        renderer.flush();
        window.swap();
    }

//    public void start(){
//        long lastLoop = time.millis();
//        int frameTimer = 0, updateTimer = 0, loopTimer = 0;
//        int lastFrames = 0, lastUpdates = 0;
//
//        screen.init();
//        while(window.keep()){
//            time.update();
//            frameTimer += time.time - lastLoop;
//            updateTimer += time.time - lastLoop;
//            loopTimer += time.time - lastLoop;
//            lastLoop = time.time;
//
//            int minTime = min((1000 / ups) - updateTimer, (1000 / fps) - frameTimer, 1000 - updateTimer);
//            if(minTime > 1){
//                try{
//                    Thread.sleep(minTime);
//                }catch(InterruptedException e){
//                    Log.error(getStackTrace(e));
//                    throw new RuntimeException(e.getMessage());
//                }
//                continue;
//            }
//
//            // TODO: Delta time
//            if(updateTimer >= (1000 / ups)){
//                updateTimer %= (1000 / ups);
//                time.updates++;
//                window.update();
//                screen.update();
//            }
//            if(frameTimer >= (1000 / fps)){
//                renderer.binded = null;
//                frameTimer %= (1000 / fps);
//                time.frames++;
//                glClear(GL_COLOR_BUFFER_BIT);
//                screen.draw();
//                draw.render();
//                renderer.flush();
//                window.swap();
//            }
//            if(loopTimer >= 1000){
//                loopTimer %= 1000;
//                time.cfps = time.frames - lastFrames;
//                time.cups = time.updates - lastUpdates;
//                lastFrames = time.frames;
//                lastUpdates = time.updates;
//                Log.info("FPS: " + time.cfps + " UPS: " + time.cups);
//            }
//            window.poll();
//        }
//}
}
