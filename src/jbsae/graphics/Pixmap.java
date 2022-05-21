package jbsae.graphics;

import jbsae.graphics.gl.*;
import org.lwjgl.system.*;

import static jbsae.util.Mathf.*;

public class Pixmap{
    public Color[][] map;

    public Pixmap(Texture t){
        map = new Color[t.width][t.height];

        byte[] a = new byte[t.image.capacity()];
        t.image.position(0);
        t.image.get(a);
        //TODO: image
        int i = 0;
        for(int y = 0;y < t.height;y++){
            for(int x = 0;x < t.width;x++) map[x][y] = new Color(mod(t.image.get(i++), 256) / 255f, mod(t.image.get(i++), 256) / 255f, mod(t.image.get(i++), 256) / 255f, mod(t.image.get(i++), 256) / 255f);
        }
    }

    public Texture create(){
        byte[] b = new byte[map.length * map[0].length * 4];
        int i = 0;
        for(int y = 0;y < map[0].length;y++){
            for(int x = 0;x < map.length;x++){
                b[i++] = (int)(map[x][y].r * 255) >= 255 ? -1 : (byte)(map[x][y].r * 255);
                b[i++] = (int)(map[x][y].g * 255) >= 255 ? -1 : (byte)(map[x][y].g * 255);
                b[i++] = (int)(map[x][y].b * 255) >= 255 ? -1 : (byte)(map[x][y].b * 255);
                b[i++] = (int)(map[x][y].a * 255) >= 255 ? -1 : (byte)(map[x][y].a * 255);
            }
        }
        MemoryStack stack = MemoryStack.stackPush();
        return new Texture(map.length, map[0].length, stack.bytes(b));
    }
}
