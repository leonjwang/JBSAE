package jbsae.graphics.gl;

import org.lwjgl.system.*;

import java.nio.*;

import static jbsae.util.Stringf.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.stb.STBImage.*;

/** @author Heiko Brumme */
public class Texture{
    public int id;
    public int width, height;
    public ByteBuffer image;

    public Texture(String path){
        id = glGenTextures();
        load(path);
    }

    public void load(String path){
        MemoryStack stack = MemoryStack.stackPush();
        IntBuffer w = stack.mallocInt(1);
        IntBuffer h = stack.mallocInt(1);
        image = stbi_load(path, w, h, stack.mallocInt(1), 4);
        if(image == null) printDebug("Failed to load texture: " + path);

        width = w.get();
        height = h.get();

        bind();

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
    }

    public void bind(){
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void dispose(){
        glDeleteTextures(id);
    }
}
