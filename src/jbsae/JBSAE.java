package jbsae;

import jbsae.audio.*;
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
    public static Sounds sounds = new Sounds();

    public static void init(){
        glfwInit();
        time.init();
        window.init();
        renderer.init();
        input.init();
        loop.init();
        sounds.init();
    }

    public static void load(){
        for(Shader shader : Shader.all) shader.load();
        for(Sound sound : Sound.all) sound.load();
        for(Font font : Font.all) font.load();
        for(Texture texture : Texture.all) texture.load();
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
        Texture icon = new Texture("assets/" + programName + ".png");
        Font roboto = new Font("assets/fonts/Roboto/font.fnt");
        Sound music = new Sound("assets/sounds/Spiritualcore Mix [Physical Emotion].ogg");


        load();
        Drawf.font(roboto);

        Source sourceBack = new Source(music).loop(true).pitch(1.65f);
        sourceBack.play();
        screen(new Screen(){
            @Override
            public void draw(){
//                if(chance(0.01f)){
//                    finalBuffBack.setPitch(2f);
//                    finalBuffBack.setPosition(new Vec3(random(0, 1000), random(0, 1000), random(0, 1000)));
//                    sounds.playSoundSource("Music");
//                }
//                Vec3 pos = new Vec3(Mathf.sint(5000) * 10000000f, Mathf.sint(2000) * 10000000f, Mathf.sint(15000) * 10000000f);
//                sounds.listener.position(new Vec3(-20, 0, 0));
//                sounds.listener.setOrientation(pos, new Vec3(0, 1, 0));

//                Drawf.fill(Colorf.lighten(Colorf.vibrantc(Tmp.c1)));
                Drawf.fill(Colorf.vibrantc(Tmp.c1));
//                Drawf.draw(roboto.pages.get(0).full, 100, 100, 200, 200);
                Drawf.text("Testing chars BeCaUsE Font", 100, 100);
            }
        });
        start();
        dispose();
    }
}
