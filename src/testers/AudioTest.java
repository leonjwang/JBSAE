package testers;

import jbsae.*;
import jbsae.audio.*;
import jbsae.core.*;
import jbsae.math.*;
import jbsae.util.*;

import static jbsae.JBSAE.*;

public class AudioTest{
    public static void main(String[] args){
        jbsae(() -> {
            Drawf.font(assets.fonts.get("brandbe.fnt"));

            Source song = new Source(assets.sounds.get("mix.wav"));
            song.pitch(0.85f);
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


                    Drawf.fill(Tmp.c1.hsv(a, 1f, v.len() / height * 2));
                    Drawf.line(assets.textures.get("square.png").full, width / 2f, height / 2f, input.mouse.x, input.mouse.y, 5f);

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
