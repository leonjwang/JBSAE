package jbsae.graphics.gl;

import org.lwjgl.system.*;

import java.nio.*;

import static jbsae.JBSAE.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.stb.STBImage.*;

/** @author Heiko Brumme */
public class Texture{
    public int id;
    public int width, height;
    public ByteBuffer image;

    public Texture(String path){ //TODO: File system
        id = glGenTextures();
        load(path);
        init();
    }

    public Texture(int width, int height, ByteBuffer image){
        id = glGenTextures();
        this.width = width;
        this.height = height;
        this.image = image;
        init();
    }

    public void init(){
        bind();
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
    }

    public void load(String path){
        MemoryStack stack = MemoryStack.stackPush();
        IntBuffer w = stack.mallocInt(1), h = stack.mallocInt(1);
        image = stbi_load(path, w, h, stack.mallocInt(1), 4);
        if(image == null) System.out.println("Failed to load texture: " + path);

        width = w.get();
        height = h.get();
    }

    public void bind(){
        renderer.binded = this;
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void dispose(){
        glDeleteTextures(id);
    }
}
