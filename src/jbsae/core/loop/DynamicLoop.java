package jbsae.core.loop;

import jbsae.*;
import jbsae.time.*;

import static jbsae.JBSAE.*;

public class DynamicLoop extends GameLoop{
    public int upsCap = -1;

    public DynamicLoop(){
    }

    public DynamicLoop(int upsCap){
        this.upsCap = upsCap;
    }

    @Override
    public void start(){
        screen.init();

        if(upsCap < 0) upsCap = ups;
        long interval = 1_000_000_000L / ups;

        long now = System.nanoTime();
        Interval frameInterval = new Interval(1_000_000_000L / fps, now);
        Interval updateInterval = new Interval(1_000_000_000L / upsCap, now);
        Interval loopInterval = new Interval(1_000_000_000L, now);
        int lastFrames = 0, lastUpdates = 0;
        while(window.keep()){
            now = System.nanoTime();

            time.delta = (float)(now - updateInterval.lastTick) / (float)interval;
            updateInterval.tick(now);
            update();

            if(frameInterval.tick(now)) render();
            if(loopInterval.tick(now)){
                time.cfps = time.frames - lastFrames;
                time.cups = time.updates - lastUpdates;
                lastFrames = time.frames;
                lastUpdates = time.updates;
                Log.info("FPS: " + time.cfps + " UPS: " + time.cups);
            }

            waitTill(updateInterval.nextTick());
            window.poll();
        }
    }
}
