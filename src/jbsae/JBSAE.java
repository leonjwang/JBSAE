package jbsae;

import jbsae.audio.*;
import jbsae.core.*;
import jbsae.files.*;
import jbsae.graphics.*;
import jbsae.graphics.gl.*;
import jbsae.input.*;
import jbsae.math.*;
import jbsae.util.*;
import org.lwjgl.openal.*;

import static jbsae.util.Mathf.*;
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
    public static Sounds sounds = new Sounds();

    public static void init(){
        glfwInit();
        time.init();
        window.init();
        renderer.init();
        input.init();
        loop.init();

        sounds.init();
        sounds.setAttenuationModel(AL11.AL_EXPONENT_DISTANCE);
    }

    public static void start(){
        loop.start();
    }

    public static void dispose(){
        input.dispose();
        window.dispose();
        renderer.dispose();
        sounds.dispose();
        glfwTerminate();
    }

    public static void screen(Screen s){
        loop.screen = s;
    }

    public static void main(String[] args){
        init();
        Font roboto = new Font(new Fi("assets/fonts/Brandbe"));
        Drawf.font(roboto);

        SoundSource sourceBack = null;
        try{
            SoundBuffer buffBack = new SoundBuffer("assets/sounds/game1.ogg");
            sounds.addSoundBuffer(buffBack);
            sourceBack = new SoundSource(false, true);
            sourceBack.setBuffer(buffBack.id);
            sounds.addSoundSource("Music", sourceBack);
            sourceBack.play();
//            sourceBack.setPitch(3f);
        }catch(Exception e){
            e.printStackTrace();
        }

//        soundMgr.playSoundSource(Sounds.BEEP.toString());
        SoundSource finalBuffBack = sourceBack;
        screen(new Screen(){
            @Override
            public void draw(){
//                if(chance(0.01f)){
//                    finalBuffBack.setPitch(2f);
//                    finalBuffBack.setPosition(new Vec3(random(0, 1000), random(0, 1000), random(0, 1000)));
//                    sounds.playSoundSource("Music");
//                }
//                Vec3 pos = new Vec3(Mathf.sint(5000) * 10000000f, Mathf.sint(2000) * 10000000f, Mathf.sint(15000) * 10000000f);
//                sounds.listener.setPosition(pos);
//                sounds.listener.setOrientation(pos, new Vec3(0, 1, 0));
                Drawf.fill(Colorf.vibrantc());
                Drawf.text("Testing chars BeCaUsE Font", 100, 100);
            }
        });
        start();
        dispose();
    }
}
