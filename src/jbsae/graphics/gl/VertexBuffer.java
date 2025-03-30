package jbsae.graphics.gl;

import java.nio.*;

import static org.lwjgl.opengl.GL15.*;


public class VertexBuffer{
    public int id;

    public VertexBuffer(){
        id = glGenBuffers();
        bind(GL_ARRAY_BUFFER);
    }

    public void bind(int target){
        glBindBuffer(target, id);
    }

    public void data(int target, long size, int usage){
        glBufferData(target, size, usage);
    }

    public void subData(int target, long offset, FloatBuffer data){
        glBufferSubData(target, offset, data);
    }

    public void dispose(){
        glDeleteBuffers(id);
    }
}
