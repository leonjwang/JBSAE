package jbsae.graphics;

import jbsae.func.*;
import jbsae.graphics.gl.*;
import jbsae.math.*;
import org.lwjgl.*;

import java.nio.*;

import static jbsae.util.Mathf.*;

public class Pixmap{
    public Color[][] map;

    public Pixmap(Texture t){
        this(t.width, t.height);

        int i = 0;
        t.image.position(0);
        clear();
        each(pos -> {
            set(pos, new Color(
            mod(t.image.get(), 256) / 255f,
            mod(t.image.get(), 256) / 255f,
            mod(t.image.get(), 256) / 255f,
            mod(t.image.get(), 256) / 255f));
        });
    }

    public Pixmap(int width, int height){
        map = new Color[width][height];
        clear();
    }

    public void clear(){
        each(pos -> map[pos.x][pos.y] = new Color());
    }

    public int width(){
        return map.length;
    }

    public int height(){
        return map[0].length;
    }

    public Color get(Point2 pos){
        return get(pos.x, pos.y);
    }

    public Color get(int x, int y){
        return map[clamp(x, 0, width() - 1)][clamp(y, 0, height() - 1)];
    }

    public Pixmap set(int x, int y, float r, float g, float b, float a){
        map[clamp(x, 0, width() - 1)][clamp(y, 0, height() - 1)].set(r, g, b, a);
        return this;
    }

    public Pixmap set(Point2 pos, Color c){
        return set(pos.x, pos.y, c);
    }

    public Pixmap set(int x, int y, Color c){
        set(x, y, c.r, c.g, c.b, c.a);
        return this;
    }

    public Pixmap each(Cons<Point2> cons){
        Point2 pos = new Point2();
        for(int y = 0;y < height();y++){
            for(int x = 0;x < width();x++) cons.get(pos.set(x, y));
        }
        return this;
    }

    public Pixmap draw(Pixmap p, int tx, int ty){
        for(int y = 0;y < p.height();y++){
            for(int x = 0;x < p.width();x++) get(x + tx, y + ty).set(p.get(x, y));
        }
        return this;
    }

    public Texture create(){
        ByteBuffer buffer = BufferUtils.createByteBuffer(map.length * map[0].length * 4);
        for(int y = 0;y < height();y++){
            for(int x = 0;x < width();x++){
                buffer.put((byte)((int)(map[x][y].r * 255) & 0xFF));
                buffer.put((byte)((int)(map[x][y].g * 255) & 0xFF));
                buffer.put((byte)((int)(map[x][y].b * 255) & 0xFF));
                buffer.put((byte)((int)(map[x][y].a * 255) & 0xFF));
            }
        }
        buffer.flip();
        return new Texture(map.length, map[0].length, buffer);
    }
}
