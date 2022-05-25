package jbsae.core;

import static org.lwjgl.glfw.GLFW.*;

/** @author Heiko Brumme */
public class Time{
    public long time = 0;
    public int frames, updates;
    public int cfps, cups;
    public float delta = 1f;

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
}
