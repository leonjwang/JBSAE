package jbsae.graphics.gl;

import jbsae.graphics.*;
import jbsae.math.*;
import org.lwjgl.system.*;

import java.nio.*;

import static jbsae.JBSAE.*;
import static org.lwjgl.opengl.GL30.*;

/** @author Heiko Brumme */
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
        program.setUniform("model", new Mat4());
        program.setUniform("view", new Mat4());
        program.setUniform("projection", Mat4.orthographic(0f, width * 2, 0f, height * 2, -1f, 1f));

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
        drawTextureRegion(x, y, x + texture.width, y + texture.height, 0f, 0f, 1f, 1f, c);
    }

    public void drawTexture(float x, float y, float w, float h, Color c){
        drawTextureRegion(x, y, x + w, y + h, 0f, 0f, 1f, 1f, c);
    }

    public void drawTextureRegion(float x1, float y1, float x2, float y2, float tx1, float ty1, float tx2, float ty2, Color c){
        if(vertices.remaining() < 8 * 6) flush();

        vertex(x1, y1, c.r, c.g, c.b, c.a, tx1, ty1);
        vertex(x1, y2, c.r, c.g, c.b, c.a, tx1, ty2);
        vertex(x2, y2, c.r, c.g, c.b, c.a, tx2, ty2);
        vertex(x1, y1, c.r, c.g, c.b, c.a, tx1, ty1);
        vertex(x2, y2, c.r, c.g, c.b, c.a, tx2, ty2);
        vertex(x2, y1, c.r, c.g, c.b, c.a, tx2, ty1);

        verticesNum += 6;
    }

    public void vertex(float... values){
        for(int i = 0;i < values.length;i ++) vertices.put(values[i]);
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
