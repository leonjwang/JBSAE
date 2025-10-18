package jbsae.core.loop;

import jbsae.core.*;

import java.util.concurrent.locks.*;

import static jbsae.JBSAE.*;
import static org.lwjgl.opengl.GL11C.*;


public class GameLoop{ // TODO: The loops are scuffed, but I don't know how to make clean
    public Screen screen;

    public GameLoop(){
    }

    public void init(){
    }

    public void start(){
    }

    public void update(){
        time.updates++;
        window.update();
        screen.update();
        sounds.update();
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

    public static void waitTill(long targetTime){
        long sleepNs = targetTime - System.nanoTime();
        if(sleepNs > 2_000_000L) LockSupport.parkNanos(sleepNs - 1_000_000L);
        while(System.nanoTime() < targetTime) Thread.onSpinWait();
    }
}
