package jbsae.graphics.gl;

import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.opengl.GL30.*;

public class Renderer{
    public Renderer(){
    }

    public void init(){
        int vao = glGenVertexArrays();
        glBindVertexArray(vao);

        MemoryStack stack = MemoryStack.stackPush();
        FloatBuffer vertices = stack.mallocFloat(3 * 6);
        vertices.put(-0.6f).put(-0.4f).put(0f).put(1f).put(0f).put(0f);
        vertices.put(0.6f).put(-0.4f).put(0f).put(0f).put(1f).put(0f);
        vertices.put(0f).put(0.6f).put(0f).put(0f).put(0f).put(1f);
        vertices.flip();

        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        MemoryStack.stackPop();

        //TODO: Can't just plop in path like this, need a file reader class :(
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, "assets/shaders/shader.vert");
        glCompileShader(vertexShader);

//        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
//        glShaderSource(fragmentShader, "assets/shaders/shader.frag");
//        glCompileShader(fragmentShader);

        int status = glGetShaderi(vertexShader, GL_COMPILE_STATUS);
        if (status != GL_TRUE) {
            throw new RuntimeException(glGetShaderInfoLog(vertexShader));
        }
    }
}
