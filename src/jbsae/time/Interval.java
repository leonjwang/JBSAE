package jbsae.time;

import static jbsae.JBSAE.*;

public class Interval{
    public long lastTick, interval;

    /** Creates a timer with the given interval and a start time of 0. (It will tick immediately) */
    public Interval(long interval){
        this(interval, 0);
    }

    public Interval(long interval, long start){
        this.lastTick = start;
        this.interval = interval;
    }

    public long nextTick(){
        return lastTick + interval;
    }

    public boolean tick(long now){
        if(now - lastTick >= interval){
            lastTick += ((now - lastTick) / interval) * interval;
            return true;
        }
        return false;
    }

    public boolean tick(){
        return tick(time.millis());
    }
}
