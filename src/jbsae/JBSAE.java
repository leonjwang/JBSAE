package jbsae;

import jbsae.audio.*;
import jbsae.core.*;
import jbsae.files.assets.*;
import jbsae.graphics.*;
import jbsae.graphics.draw.*;
import jbsae.graphics.gl.*;
import jbsae.input.*;
import jbsae.math.*;
import jbsae.struct.*;
import jbsae.util.*;

import static org.lwjgl.glfw.GLFW.*;

public class JBSAE{
    public static boolean debug = true;

    public static String programName = "JBSAE";
    public static String assetsFolder = "assets";
    public static int width = 600, height = 500;
    public static int curWidth = 1, curHeight = 1;
    public static int fps = 60, ups = 60;

    public static Time time = new Time();
    public static Window window = new Window();
    public static Renderer renderer = new Renderer();
    public static Input input = new Input();
    public static GameLoop loop = new GameLoop();
    public static Sounds sounds = new Sounds();

    public static Draw draw = new Draw();

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
        for(String assetName : AssetFi.all) AssetFi.all.get(assetName).load();
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
//        Fi file = new Fi("sus");
//        Write write = new Write(file);
//        double f = 1 / Math.random();
//        System.out.println(f);
//        write.d(f);
//        write.close();
//
//        Read read = new Read(file);
//        System.out.println(read.d());
//
//        read.close();
//        file.create();
//    }
        init();
//        AssetDir root = new AssetDir("assets");
//        root.list(new Seq<AssetFi>());

        AssetFi assets_sounds_Spiritualcore_Mix_wav = AssetFi.create("assets/sounds/Spiritualcore Mix.wav");
        AssetFi assets_sounds_Spiritualcore_Mix_ogg = AssetFi.create("assets/sounds/Spiritualcore Mix.ogg");
        AssetFi assets_sounds_Spiritualcore_Mix_mp3 = AssetFi.create("assets/sounds/Spiritualcore Mix.mp3");
        AssetFi assets_sounds_Felys_wav = AssetFi.create("assets/sounds/Felys.wav");
        AssetFi assets_shaders_shader_vert = AssetFi.create("assets/shaders/shader.vert");
        AssetFi assets_shaders_shader_frag = AssetFi.create("assets/shaders/shader.frag");
        AssetFi assets_sprites_effects_circle_png = AssetFi.create("assets/sprites/effects/circle.png");
        AssetFi assets_sprites_effects_square_png = AssetFi.create("assets/sprites/effects/square.png");
        AssetFi assets_JBSAE_png = AssetFi.create("assets/JBSAE.png");
        AssetFi assets_fonts_Brandbe_font_fnt = AssetFi.create("assets/fonts/Brandbe/font.fnt");
        AssetFi assets_fonts_Brandbe_qfUMs2lDXZLHOCcP4QSEfsZJ_ttf_0_png = AssetFi.create("assets/fonts/Brandbe/qfUMs2lDXZLHOCcP4QSEfsZJ.ttf_0.png");
        AssetFi assets_fonts_Roboto_ylJJ_tsk1KIAihFAhRCwiojb_ttf_0_png = AssetFi.create("assets/fonts/Roboto/ylJJ_tsk1KIAihFAhRCwiojb.ttf_0.png");
        AssetFi assets_fonts_Roboto_font_fnt = AssetFi.create("assets/fonts/Roboto/font.fnt");

        load();

//        Drawf.font(Font.all.get(0));
//        Vec2 center = new Vec2(width / 2, height / 2);

//        ((TextureFi)AssetFi.all.get("square.png")).texture.bind();

        Source song = new Source(Sound.all.get(0));
        song.pitch(0.85f);
        song.relative(true);
        song.position(new Vec3(100, 100, 0));

        screen(new Screen(){
            @Override
            public void draw(){
//                Drawf.scalet(50);
                Vec2 v = new Vec2().set(input.mouse).sub(width / 2f, height / 2f);
//                Drawf.line(((TextureFi)AssetFi.all.get("assets/sprites/effects/square.png")).texture.full, width / 2f, height / 2f, input.mouse.x, input.mouse.y, 1f);
//                Drawf.text("" + v.ang(), 10, 10);

                if(!song.playing()) song.play();

//                Drawf.line(((TextureFi)AssetFi.all.get("square.png")).texture.full, 1f, 1f, 2f, 2f, 0.1f);
//
//                for(int i = 0;i < 2000;i++){
//                    Drawf.fill(Tmp.c1.hsv(i, 1f, 1f));
//                    Drawf.text("safk\njhsd", 12 + i, 130, 100);
//                }
//                for(int i = 0;i < 8;i++){
//                    Tmp.v1.setr(100 + (i % 2 == 0 ? 1 : -1) * Mathf.cos((time.frames / 180f) * 360f / 2f) * 25f, (Interpf.spow3.get(Mathf.mod(time.frames / 180f, 1f)) + i / 8f) * 360f).add(center);
//                    Drawf.fill(Tmp.c1.hsv((time.frames / 180f + (i < 4 ? i : 8 - i) / 8f) * 360f / 3f, 1f, 1f));
//                    Drawf.rectc(Tmp.v1.x, Tmp.v1.y, 20, 20);
//                }
//
//                Drawf.line(width / 2, height / 2, input.mouse.x, input.mouse.y);
//
//                float a = Tmp.v1.set(0, -1).ang();
            }
        });
        start();

        dispose();
    }
}
