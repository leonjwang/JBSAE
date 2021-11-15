package jbsae.graphics.gl;

import jbsae.graphics.*;
import jbsae.math.*;
import org.lwjgl.system.*;

import java.nio.*;

import static jbsae.JBSAE.*;
import static org.lwjgl.opengl.GL30.*;

public class Renderer{
    public Shader vertexShader, fragmentShader;
    public VertexArray vertexArray;
    public VertexBuffer vertexBuffer;
    public ShaderProgram program;

    public FloatBuffer vertices;
    public int verticesNum;

    public Renderer(){
    }

    public void init(){
        vertices = MemoryUtil.memAllocFloat(4096);
        verticesNum = 0;

        vertexArray = new VertexArray();
        vertexBuffer = new VertexBuffer();
        vertexBuffer.data(GL_ARRAY_BUFFER, vertices.capacity() * Float.BYTES, GL_DYNAMIC_DRAW);

        vertexShader = new Shader("assets/shaders/shader.vert", GL_VERTEX_SHADER);
        fragmentShader = new Shader("assets/shaders/shader.frag", GL_FRAGMENT_SHADER);

        program = new ShaderProgram(vertexShader, fragmentShader);
        program.bindFragment("fragColor", 0);
        program.link();
        program.use();

        program.setVertex("position", 2, 8 * Float.BYTES, 0);
        program.setVertex("color", 4, 8 * Float.BYTES, 2 * Float.BYTES);
        program.setVertex("texCoord", 2, 8 * Float.BYTES, 6 * Float.BYTES);
        program.setUniform("texImage", 0);
        program.setUniform("model", new Matrix4f());
        program.setUniform("view", new Matrix4f());
        program.setUniform("projection", Matrix4f.orthographic(0f, width * 2, 0f, height * 2, -1f, 1f));

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public void flush(){
        if(verticesNum <= 0) return;

        vertices.flip();
        vertexArray.bind();
        program.use();

        vertexBuffer.bind(GL_ARRAY_BUFFER);
        vertexBuffer.subData(GL_ARRAY_BUFFER, 0, vertices);
        glDrawArrays(GL_TRIANGLES, 0, verticesNum);

        vertices.clear();
        verticesNum = 0;
    }

    public void drawTexture(Texture texture, float x, float y, Color c){
        drawTextureRegion(x, y, x + texture.getWidth(), y + texture.getHeight(), 0f, 0f, 1f, 1f, c);
    }

    public void drawTextureRegion(float x1, float y1, float x2, float y2, float tx1, float ty1, float tx2, float ty2, Color c){
        if(vertices.remaining() < 8 * 6) flush();

        vertices.put(x1).put(y1).put(c.r).put(c.g).put(c.b).put(c.a).put(tx1).put(ty1);
        vertices.put(x1).put(y2).put(c.r).put(c.g).put(c.b).put(c.a).put(tx1).put(ty2);
        vertices.put(x2).put(y2).put(c.r).put(c.g).put(c.b).put(c.a).put(tx2).put(ty2);
        vertices.put(x1).put(y1).put(c.r).put(c.g).put(c.b).put(c.a).put(tx1).put(ty1);
        vertices.put(x2).put(y2).put(c.r).put(c.g).put(c.b).put(c.a).put(tx2).put(ty2);
        vertices.put(x2).put(y1).put(c.r).put(c.g).put(c.b).put(c.a).put(tx2).put(ty1);

        verticesNum += 6;
    }

    public void dispose(){
        MemoryUtil.memFree(vertices);
        vertexArray.dispose();
        vertexBuffer.dispose();
        vertexShader.dispose();
        fragmentShader.dispose();
        program.dispose();
    }
}
