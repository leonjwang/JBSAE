package jbsae.core.loop;

import jbsae.*;
import jbsae.core.*;

import java.util.concurrent.locks.*;

import static jbsae.JBSAE.*;
import static org.lwjgl.opengl.GL11C.*;


public class GameLoop{
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
}
