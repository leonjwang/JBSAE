package jbsae.time;

import jbsae.*;

import static org.lwjgl.glfw.GLFW.*;


public class Time{
    public long time = 0;
    public int frames, updates;
    public int cfps, cups;
    public float delta = 1f;

    public int lastFrames, lastUpdates;

    public Time(){
    }

    public void init(){
        time = millis();
    }

    public void update(){
        time = millis();
    }

    public long millis(){
        return (long)(glfwGetTime() * 1000);
    }

    public void tick(){
        cfps = frames - lastFrames;
        cups = updates - lastUpdates;
        lastFrames = frames;
        lastUpdates = updates;
        Log.info("FPS: " + cfps + " UPS: " + cups);
    }
}
