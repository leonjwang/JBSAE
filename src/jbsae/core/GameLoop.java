package jbsae.core;

import org.lwjgl.system.*;

import java.nio.*;

import static jbsae.JBSAE.*;
import static jbsae.util.Stringf.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class GameLoop{
    public int rotation = 0;
    public Screen screen;

    public GameLoop(){
    }

    public void init(){

    }

    public void start(){
        long lastLoop = time.time();
        int frameTimer = 0, updateTimer = 0, loopTimer = 0;
        int lastFrames = 0, lastUpdates = 0;
        while(!window.shouldClose()){
            time.update();
            frameTimer += time.time - lastLoop;
            updateTimer += time.time - lastLoop;
            loopTimer += time.time - lastLoop;
            lastLoop = time.time;

            if(updateTimer >= (1000 / ups)){
                updateTimer %= (1000 / ups);
                time.updates++;
                window.update();
                rotation++;
            }
            if(frameTimer >= (1000 / fps)){
                frameTimer %= (1000 / fps);
                time.frames++;
                //tempDraw();
                window.swapBuffers();
            }
            if(loopTimer >= 1000){
                loopTimer %= 1000;
                printDebug("fps: " + (time.frames - lastFrames) + ", ups: " + (time.updates - lastUpdates));
                lastFrames = time.frames;
                lastUpdates = time.updates;
            }
            window.pollEvents();
        }
    }

    public void tempDraw(){
        float ratio;

        ratio = curWidth / (float)curHeight;

        /* Set ortographic projection */
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(-ratio, ratio, -1f, 1f, 1f, -1f);
        glMatrixMode(GL_MODELVIEW);

        /* Rotate matrix */
        glLoadIdentity();
        glRotatef(rotation, 0f, 0f, 1f);

        /* Render triangle */
        glBegin(GL_TRIANGLES);
        glColor3f(1f, 0f, 0f);
        glVertex3f(-0.6f, -0.4f, 0f);
        glColor3f(0f, 1f, 0f);
        glVertex3f(0.6f, -0.4f, 0f);
        glColor3f(0f, 0f, 1f);
        glVertex3f(0f, 0.6f, 0f);
        glEnd();
    }
}
