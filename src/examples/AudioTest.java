package examples;

import jbsae.*;
import jbsae.audio.*;
import jbsae.core.*;
import jbsae.graphics.*;
import jbsae.graphics.gl.*;
import jbsae.math.*;
import jbsae.util.*;

import static jbsae.JBSAE.*;

public class AudioTest{

    public static void main(String[] args){
        jbsae(() -> {
            Drawf.font(assets.fonts.get("brandbe.fnt"));

            Source song = sounds.play("Nadir.wav");
            song.loop(true);
            song.pitch(1f);
            song.relative(false);
            song.position(new Vec3(10, 0, 0));

            Pixmap wheelMap = new Pixmap(width, height);
            Vec2 center = new Vec2(width / 2f, height / 2f);
            for(int y = 0;y < height;y++){
                for(int x = 0;x < width;x++){
                    Tmp.v1.set(x, y).sub(center);
                    wheelMap.set(x, y, Tmp.c1.hsv(Tmp.v1.ang(), 1f, Tmp.v1.len() / 300f));
                }
            }
            Texture wheel = wheelMap.create();

            screen(new Screen(){
                public float la = 360f;
                public float a = 360f;

                @Override
                public void draw(){
                    if(!song.playing()) song.play();

                    Vec2 v = new Vec2().set(input.mouse).sub(width / 2f, height / 2f);

                    a += Mathf.diffa(v.ang(), la);
                    la = v.ang();
                    a = Math.max(a, 0);

                    wheel.bind();
                    Drawf.fill(Colorf.WHITE);

                    Drawf.rectc(width / 2f, height / 2f, width, height);

                    Region square = assets.textures.get("square.png").full;


                    Drawf.fill(Tmp.c1.set(1f, 1f, 1f).a(0.2f));
                    Drawf.line(square, width / 2f, height / 2f, input.mouse.x, input.mouse.y, 5f);


                    Drawf.fill(1f, 1f, 1f, 0.9f);
                    Drawf.text("" + a, 10, 10, 30);

                    Drawf.fill(1f, 1f, 1f, 0.9f);
                    Drawf.text("" + v.len(), 10, 40, 20);

                    float pitch = Math.max(1, a) / 360f;
                    song.pitch(pitch);

                    float gain = (v.len() / height) * 4f;
                    song.gain(gain);
                }
            });
        });
    }
}