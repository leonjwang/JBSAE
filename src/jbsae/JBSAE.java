package jbsae;

import jbsae.audio.*;
import jbsae.core.*;
import jbsae.core.loop.*;
import jbsae.func.*;
import jbsae.graphics.draw.*;
import jbsae.graphics.gl.*;
import jbsae.input.*;
import jbsae.time.*;

import java.lang.management.*;
import java.util.*;

import static jbsae.util.Stringf.*;
import static org.lwjgl.glfw.GLFW.*;

public class JBSAE{
    public static String programName = "JBSAE";
    public static int width = 600, height = 500;
    public static int ups = 60, fps = 60;

    public static Assets assets = new Assets();
    public static Time time = new Time();
    public static Window window = new Window();
    public static Renderer renderer = new Renderer();
    public static Input input = new Input();
    public static GameLoop loop = new FixedLoop();
    public static Sounds sounds = new Sounds();

    public static Draw draw = new SortedDraw();

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

    public static void jbsae(Screen s){
        jbsae(() -> screen(s));
    }

    public static void jbsae(Prog prog){
        checkRelaunch();
        Log.init();
        try{
            init();
            load();
            prog.run();
            start();
            dispose();
        }catch(Throwable e){
            Log.error(getStackTrace(e));
            Log.writeToFile("log" + System.currentTimeMillis() + ".log"); // TODO: Dynamic log dump location
        }
    }


    // TODO: We ignore main String[] args atm
    private static void checkRelaunch(){
//        System.out.println("Meow");
//        System.out.println(System.getProperty("os.name").toLowerCase());
        if(!System.getProperty("os.name").toLowerCase().contains("mac")) return;
        if(ManagementFactory.getRuntimeMXBean().getInputArguments().contains("-XstartOnFirstThread")) return;
        try{
            ArrayList<String> commands = new ArrayList<>();
            commands.add(System.getProperty("java.home") + "/bin/java");
            commands.add("-XstartOnFirstThread");
            commands.add("-cp");
            commands.add(System.getProperty("java.class.path"));
            Class<?> mainClass = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE)
            .walk(frames -> frames
            .filter(frame -> frame.getMethodName().equals("main"))
            .findFirst()
            .map(StackWalker.StackFrame::getDeclaringClass)
            .orElseThrow(() -> new IllegalStateException("Could not find main method in stack trace!"))
            );
            commands.add(mainClass.getName());

            ProcessBuilder pb = new ProcessBuilder(commands).inheritIO();
            Process process = pb.start();
            process.waitFor();
            System.exit(process.exitValue());
        }catch(Exception e){
            // Buns
            System.err.println("Failed to launch JVM");
            e.printStackTrace();
        }
    }
}
