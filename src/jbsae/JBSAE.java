package jbsae;

import jbsae.core.*;
import jbsae.graphics.gl.*;
import jbsae.input.*;
import org.lwjgl.glfw.*;

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
        glfwTerminate();
    }

    public static void main(String[] args){
        init();
        start();
        dispose();
    }
}
