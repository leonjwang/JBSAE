package jbsae.graphics.gl;

import static org.lwjgl.opengl.ARBVertexArrayObject.*;

/** @author Heiko Brumme */
public class VertexArray{
    public int id;

    public VertexArray(){
        id = glGenVertexArrays();
        bind();
    }

    public void bind(){
        glBindVertexArray(id);
    }

    public void dispose(){
        glDeleteVertexArrays(id);
    }
}
