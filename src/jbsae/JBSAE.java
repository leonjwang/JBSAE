package jbsae;

import jbsae.core.*;
import jbsae.graphics.*;
import jbsae.graphics.gl.*;
import jbsae.input.*;
import jbsae.util.*;

import static org.lwjgl.glfw.GLFW.*;

public class JBSAE{
    public static boolean debug = true;

    public static String programName = "JBSAE";
    public static int width = 600, height = 500;
    public static int curWidth, curHeight;
    public static int fps = 60, ups = 60;

    public static Time time = new Time();
    public static Window window = new Window();
    public static Renderer renderer = new Renderer();
    public static Input input = new Input();
    public static GameLoop loop = new GameLoop();

    public static void init(){
        glfwInit();
        time.init();
        window.init();
        renderer.init();
        input.init();
        loop.init();
    }

    public static void start(){
        loop.start();
    }

    public static void dispose(){
        input.dispose();
        window.dispose();
        renderer.dispose();
        glfwTerminate();
    }

    public static void screen(Screen s){
        loop.screen = s;
    }

    public static void main(String[] args){
        init();
        Texture t = new Texture("assets/logo.png");
        Region r = new Region(t, 10, 10, 500, 100);
        screen(new Screen(){
            @Override
            public void draw(){
                for(int i = 0;i < 4000;i++) Drawf.draw(r, 0, 0, 100, 100);
            }
        });
        start();
        dispose();
    }
}
