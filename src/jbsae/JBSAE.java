package jbsae;

import jbsae.audio.*;
import jbsae.core.*;
import jbsae.files.*;
import jbsae.files.assets.*;
import jbsae.graphics.*;
import jbsae.graphics.gl.*;
import jbsae.input.*;
import jbsae.math.*;
import jbsae.struct.*;
import jbsae.util.*;

import static jbsae.util.Drawf.*;
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
        for(Object assetName : AssetFi.all.keys()) AssetFi.all.get((String)assetName).load();
    }

    public static void start(){
        loop.start();
    }

    public static void dispose(){
        input.dispose();
        window.dispose();
        renderer.dispose();
        sounds.dispose();

        for(Shader shader : Shader.all) shader.dispose();
        for(Sound sound : Sound.all) sound.dispose();
        for(Source source : Source.all) source.dispose();
        for(Texture texture : Texture.all) texture.dispose();

        glfwTerminate();
    }

    public static void screen(Screen s){
        loop.screen = s;
    }

    public static void main(String[] args){
        Fi file = new Fi("sus");
        file.create();
    }
//        init();
//        AssetDir root = new AssetDir("assets");
//        root.list(new Seq<AssetFi>());
//
//        load();
//
//        Drawf.font(Font.all.get(0));
////        Vec2 center = new Vec2(width / 2, height / 2);
//
////        ((TextureFi)AssetFi.all.get("square.png")).texture.bind();
//
//        screen(new Screen(){
//            @Override
//            public void draw(){
////                Drawf.scalet(50);
//                Drawf.line(((TextureFi)AssetFi.all.get("square.png")).texture.full, 1f, 1f, 2f, 2f, 0.1f);
//
//                Drawf.text("safkjhsd", 12, 130, 100);
////                for(int i = 0;i < 8;i++){
////                    Tmp.v1.setr(100 + (i % 2 == 0 ? 1 : -1) * Mathf.cos((time.frames / 180f) * 360f / 2f) * 25f, (Interpf.spow3.get(Mathf.mod(time.frames / 180f, 1f)) + i / 8f) * 360f).add(center);
////                    Drawf.fill(Tmp.c1.hsv((time.frames / 180f + (i < 4 ? i : 8 - i) / 8f) * 360f / 3f, 1f, 1f));
////                    Drawf.rectc(Tmp.v1.x, Tmp.v1.y, 20, 20);
////                }
////
////                Drawf.line(width / 2, height / 2, input.mouse.x, input.mouse.y);
////
////                float a = Tmp.v1.set(0, -1).ang();
//            }
//        });
//        start();
//        dispose();
//    }
}
