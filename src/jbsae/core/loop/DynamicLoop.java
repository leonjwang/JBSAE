package jbsae.core.loop;

import jbsae.*;
import jbsae.time.*;

import static jbsae.JBSAE.*;

public class DynamicLoop extends GameLoop{
    public int cap;

    public DynamicLoop(){
        this(ups);
    }

    public DynamicLoop(int cap){
        this.cap = cap;
    }

    @Override
    public void start(){
        screen.init();

        float expectedInterval = (float)(1_000_000_000L / (double)ups);

        long now = System.nanoTime();
        Interval frameInterval = new Interval(1_000_000_000L / fps, now);
        Interval updateInterval = new Interval(cap <= 0 ? 1L : 1_000_000_000L / cap, now);
        Interval loopInterval = new Interval(1_000_000_000L, now);
        while(window.keep()){
            now = System.nanoTime();

            time.delta = (float)(now - updateInterval.lastTick) / expectedInterval;
            updateInterval.tick(now);
            update();

            if(frameInterval.tick(now)) render();
            if(loopInterval.tick(now)) time.tick();

            waitTill(updateInterval.nextTick());
            window.poll();
        }
    }
}
