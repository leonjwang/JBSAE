package jbsae.graphics;

import jbsae.graphics.gl.*;
import jbsae.math.*;

public class Region{
    public Texture texture;
    public Range2 region;

    public Region(Texture texture){
        this.texture = texture;
        region = new Range2(0, 0, 1, 1);
    }

    public Region(Texture texture, float x, float y, float w, float h){
        this.texture = texture;
        this.region = new Range2(x / texture.width, y / texture.height, w / texture.width, h / texture.height);
    }
}
