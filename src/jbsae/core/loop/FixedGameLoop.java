package jbsae.core.loop;

import jbsae.*;

import java.util.concurrent.locks.*;

import static jbsae.JBSAE.*;
import static org.lwjgl.opengl.GL11C.*;

public class FixedGameLoop extends GameLoop{ // TODO: The loops are scuffed
    @Override
    public void start(){
        screen.init(); // TODO: is screen.init here scuffed?

        long frameInterval = 1_000_000_000L / fps, updateInterval = 1_000_000_000L / ups;

        long nextTick = System.nanoTime();
        long lastFrame = nextTick, lastUpdate = nextTick, lastLoop = nextTick;
        int lastFrames = 0, lastUpdates = 0;
        while(window.keep()){
            long now = System.nanoTime();

            if(now - lastUpdate >= updateInterval){
                lastUpdate += ((now - lastUpdate) / updateInterval) * updateInterval;
                update();
            }

            if(now - lastFrame >= frameInterval){
                lastFrame += ((now - lastFrame) / frameInterval) * frameInterval;
                render();
            }

            if(now - lastLoop >= 1_000_000_000L){
                lastLoop += ((now - lastLoop) / 1_000_000_000L) * 1_000_000_000L;
                time.cfps = time.frames - lastFrames;
                time.cups = time.updates - lastUpdates;
                lastFrames = time.frames;
                lastUpdates = time.updates;
                Log.info("FPS: " + time.cfps + " UPS: " + time.cups);

                sounds.update();
                Log.info("Sources: " + sounds.sources.size);
            }

            nextTick = Math.min(lastFrame + frameInterval, lastUpdate + updateInterval);

            long sleepNs = nextTick - System.nanoTime();
            if(sleepNs > 2_000_000L) LockSupport.parkNanos(sleepNs - 1_000_000L);
            while(System.nanoTime() < nextTick) Thread.onSpinWait();

            window.poll();
        }
    }
}
