package examples;

import jbsae.*;
import jbsae.audio.*;
import jbsae.core.*;
import jbsae.graphics.*;
import jbsae.math.*;
import jbsae.util.*;

import static jbsae.JBSAE.*;

public class AudioTest{
    public static void main(String[] args){
        jbsae(() -> {
            Drawf.font(assets.fonts.get("brandbe.fnt"));

            Source song = sounds.play("mix.wav");
            song.loop(true);
            song.pitch(1f);
            song.relative(false);
            song.position(new Vec3(10, 0, 0));

            screen(new Screen(){
                public float la = 360f;
                public float a = 360f;

                @Override
                public void draw(){
                    Vec2 v = new Vec2().set(input.mouse).sub(width / 2f, height / 2f);

                    a += Mathf.diffa(v.ang(), la);
                    la = v.ang();
                    a = Math.max(a, 0);

                    Region square = assets.textures.get("square.png").full;

//                    for(int i = 0;i < 3600;i++){
//                        float ang = i / 10f;
//                        Drawf.fill(Tmp.c1.hsv(ang, 1f, 1f).a(0.01f));
//                        Tmp.v1.set(width + height, 0).rot(ang).add(width / 2f, height / 2f);
//                        Tmp.v2.set(Tmp.v1).nor().scl(-2);
//                        Drawf.line(square, width / 2f + Tmp.v2.x, height / 2f + Tmp.v2.y, Tmp.v1.x, Tmp.v1.y, 5f);
//                    }

                    Drawf.fill(Tmp.c1.hsv(a, 1f, 1f).a(v.len() / height * 2));
                    Drawf.line(square, width / 2f, height / 2f, input.mouse.x, input.mouse.y, 5f);

                    Drawf.fill(Tmp.c1.hsv(a + 180, 1f, 1f));
                    Drawf.text("" + a, 10, 10, 30);

                    Drawf.fill(Tmp.c1.hsv(0f, 0f, v.len()));
                    Drawf.text("" + v.len(), 10, 40, 20);

                    float pitch = Mathf.max(1, a) / 360;
                    song.pitch(pitch);

                    float gain = (v.len() / height) * 4f;
                    song.gain(gain);

                    if(!song.playing()) song.play();
                }
            });
        });
    }
}
