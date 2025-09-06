package jbsae;

import jbsae.audio.*;
import jbsae.core.*;
import jbsae.files.assets.*;
import jbsae.graphics.draw.*;
import jbsae.graphics.gl.*;
import jbsae.input.*;

import static org.lwjgl.glfw.GLFW.*;

public class JBSAE{
    public static String programName = "JBSAE";
    public static int width = 600, height = 500;
    public static int fps = 60, ups = 60;

    public static Assets assets = new Assets();
    public static Time time = new Time();
    public static Window window = new Window();
    public static Renderer renderer = new Renderer();
    public static Input input = new Input();
    public static GameLoop loop = new GameLoop();
    public static Sounds sounds = new Sounds();

    public static Draw draw = new Draw();

    public static void init(){
        glfwInit();
        assets.init();
        time.init();
        window.init();
        renderer.init();
        input.init();
        loop.init();
        sounds.init();
    }

    public static void load(){
        assets.load();

        Texture icon = assets.textures.get(programName + ".png");
        window.icon(icon);
    }

    public static void start(){
        loop.start();
    }

    public static void exit(){
        glfwSetWindowShouldClose(window.id, true);
    }

    public static void dispose(){
        input.dispose();
        window.dispose();
        renderer.dispose();
        sounds.dispose();
        assets.dispose();

        glfwTerminate();
    }

    public static void screen(Screen s){
        loop.screen = s;
    }
}
