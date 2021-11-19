package jbsae.core;

import static org.lwjgl.glfw.GLFW.*;

/** @author Heiko Brumme */
public class Time{
    public long time = 0;
    public int frames, updates;
    public float delta;

    public Time(){
    }

    public void init(){
        time = time();
    }

    public void update(){
        time = time();
    }

    public long time(){
        return (long)(glfwGetTime() * 1000);
    }
}
