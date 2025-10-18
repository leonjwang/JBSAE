package jbsae.time;

import jbsae.*;

public class Stopwatch{ // TODO: Does this even deserve to be here? This is just a glorified timer
    public long start, end;

    public Stopwatch(){
    }

    public void start(){
        start = System.currentTimeMillis();
    }

    public void end(){
        end = System.currentTimeMillis();
    }

    public void print(){
        Log.info("Runtime: " + (end - start) + "ms (" + ((end - start) / 1000f) + "s)");
    }
}
