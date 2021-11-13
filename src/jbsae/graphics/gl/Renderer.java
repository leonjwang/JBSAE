package jbsae.graphics.gl;

import jbsae.math.*;
import org.lwjgl.system.*;

import java.io.*;
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

        Shader vertexShader = new Shader("assets/shaders/shader.vert", GL_VERTEX_SHADER);
        Shader fragShader = new Shader("assets/shaders/shader.frag", GL_FRAGMENT_SHADER);

        int status = glGetShaderi(vertexShader.id, GL_COMPILE_STATUS);
        if (status != GL_TRUE) {
            throw new RuntimeException(glGetShaderInfoLog(vertexShader.id));
        }

        status = glGetShaderi(fragShader.id, GL_COMPILE_STATUS);
        if (status != GL_TRUE) {
            throw new RuntimeException(glGetShaderInfoLog(fragShader.id));
        }

        int floatSize = 4;

        int posAttrib = glGetAttribLocation(vertexShader.id, "position");
        glEnableVertexAttribArray(posAttrib);
        glVertexAttribPointer(posAttrib, 3, GL_FLOAT, false, 6 * floatSize, 0);

        int colAttrib = glGetAttribLocation(vertexShader.id, "color");
        glEnableVertexAttribArray(colAttrib);
        glVertexAttribPointer(colAttrib, 3, GL_FLOAT, false, 6 * floatSize, 3 * floatSize);

        int uniModel = glGetUniformLocation(vertexShader.id, "model");
        Matrix4f model = new Matrix4f();
        glUniformMatrix4fv(uniModel, false, model.getBuffer());

        int uniView = glGetUniformLocation(vertexShader.id, "view");
        Matrix4f view = new Matrix4f();
        glUniformMatrix4fv(uniView, false, view.getBuffer());

        int uniProjection = glGetUniformLocation(vertexShader.id, "projection");
        float ratio = 640f / 480f;
        Matrix4f projection = Matrix4f.orthographic(-ratio, ratio, -1f, 1f, -1f, 1f);
        glUniformMatrix4fv(uniProjection, false, projection.getBuffer());
    }
}
