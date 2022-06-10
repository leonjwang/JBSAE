package jbsae.graphics.gl;

import jbsae.graphics.*;
import jbsae.struct.*;

import java.nio.*;

import static jbsae.JBSAE.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

/** @author Heiko Brumme */
public class Texture{
    public static Seq<Texture> all = new Seq<>();

    public int id;
    public int width, height;
    public ByteBuffer image;
    public Region full;

    public Texture(int width, int height, ByteBuffer image){
        all.add(this);

        id = glGenTextures();
        this.width = width;
        this.height = height;
        this.image = image;
        init();
    }

    public Texture init(){
        full = new Region(this);
        bind();
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
        return this;
    }

    public void bind(){
        if(renderer.binded == this) return;
        renderer.flush();
        renderer.binded = this;
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void dispose(){
        glDeleteTextures(id);
    }
}
