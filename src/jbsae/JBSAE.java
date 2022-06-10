package jbsae;

import jbsae.audio.*;
import jbsae.core.*;
import jbsae.files.*;
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
        for(ShaderFile shader : ShaderFile.all) shader.load();
        for(SoundFile sound : SoundFile.all) sound.load();
        for(TextureFile texture : TextureFile.all) texture.load();
        for(FontFile font : FontFile.all) font.load();
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
        init();
        TextureFile icon = new TextureFile("assets/" + programName + ".png");
        Font roboto = new FontFile("assets/fonts/Roboto/font.fnt").load().font;
        SoundFile music = new SoundFile("assets/sounds/Spiritualcore Mix [Physical Emotion].ogg");


        load();
        Drawf.font(roboto);

        Source sourceBack = new Source(music.sound).loop(true).pitch(1.65f);
        sourceBack.play();

        Pixmap pix = new Pixmap(icon.texture);
        pix.each(pos -> {
            pix.get(pos).inv();
            if(Mathf.chance(0.1f)) pix.get(pos).set(Colorf.vibrantc());
        });
        Texture inv = pix.create();

        Pixmap large = new Pixmap(pix.width() * 10, pix.height() * 10);
        for(int x = 0;x < 10;x++){
            for(int y = 0;y < 10;y++) large.draw(pix, x * pix.width(), y * pix.height());
        }
        Texture meow = large.create();

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
                Drawf.text("Testing chars BeCaUsE Font askjhafsdhjdsfhjdjasjdfshbdashdfgaAGerschkdnvskzcxzcsc", 20, 20, 100);

                Drawf.fill();
                for(int i = 0;i < 10;i++){
                    for(int j = 0;j < 10;j++){
                        Drawf.draw(meow.full, i * width / 10f, j * height / 10f, width / 10f, height / 10f);
                    }
                }
            }
        });
        start();
        dispose();
    }
}
