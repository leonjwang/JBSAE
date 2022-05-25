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
        Pixmap o = new Pixmap(t);
        Pixmap p = new Pixmap(t);
        p.each(pos -> {
            for(int x = pos.x - 5;x < pos.x + 5;x++){
                for(int y = pos.y - 5;y < pos.y + 5;y++){
                    if(o.get(x, y).a > 0.1f && p.get(pos.x, pos.y).a < 0.1f) p.get(pos.x, pos.y).set(1f, 0f, 0f);
                }
            }
        });
        Texture i = p.create();
        screen(new Screen(){
            @Override
            public void draw(){
                i.bind();
                for(int i = 0;i < 2000;i++){
                    Drawf.fill(Tmp.c1.hsv(i / 10f, 1, 1));
                    Drawf.rectc(100 + Mathf.sint() * 100, 100, 100, 100);
                }
            }
        });
        start();
        dispose();
    }
}
