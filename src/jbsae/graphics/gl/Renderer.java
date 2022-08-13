package jbsae.graphics.gl;

import jbsae.files.assets.*;
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

    public Texture binded;

    public Renderer(){
    }

    public void init(){
        vertices = MemoryUtil.memAllocFloat(4096);
        verticesNum = 0;

        vertexArray = new VertexArray();
        vertexBuffer = new VertexBuffer();
        vertexBuffer.data(GL_ARRAY_BUFFER, vertices.capacity() * Float.BYTES, GL_DYNAMIC_DRAW);

        vertexShader = ((ShaderFi)AssetFi.create(assetsFolder + "/shaders/shader.vert").load()).shader;
        fragmentShader = ((ShaderFi)AssetFi.create(assetsFolder + "/shaders/shader.frag").load()).shader;

        program = new ShaderProgram(vertexShader, fragmentShader);
        program.bind("fragColor", 0);
        program.link();
        program.use();

        program.setVertex("position", 2, 8 * Float.BYTES, 0);
        program.setVertex("color", 4, 8 * Float.BYTES, 2 * Float.BYTES);
        program.setVertex("texCoord", 2, 8 * Float.BYTES, 6 * Float.BYTES);
        program.setUniform("texImage", 0);
        program.setUniform("model", new Mat4());
        program.setUniform("view", new Mat4());
        program.setUniform("projection", new Mat4().ortho(0f, width * 2, 0f, height * 2, -1f, 1f));

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        glEnable(GL_MULTISAMPLE);
    }

    public void bind(Texture t){
        flush();
        t.bind();
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

    public void draw(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4, float rx1, float ry1, float rx2, float ry2, float rx3, float ry3, float rx4, float ry4, float r, float g, float b, float a){
        if(vertices.remaining() < 8 * 6) flush();

        x1 *= 2;
        y1 *= 2;
        x2 *= 2;
        y2 *= 2;
        x3 *= 2;
        y3 *= 2;
        x4 *= 2;
        y4 *= 2;

        float c = (ry1 + ry2 + ry3 + ry4) / 4;
        ry1 = 2 * c - ry1;
        ry2 = 2 * c - ry2;
        ry3 = 2 * c - ry3;
        ry4 = 2 * c - ry4;


        vertex(x1, y1, rx1, ry1, r, g, b, a);
        vertex(x2, y2, rx2, ry2, r, g, b, a);
        vertex(x3, y3, rx3, ry3, r, g, b, a);
        vertex(x1, y1, rx1, ry1, r, g, b, a);
        vertex(x4, y4, rx4, ry4, r, g, b, a);
        vertex(x3, y3, rx3, ry3, r, g, b, a);

        verticesNum += 6;
    }

    public void vertex(float x, float y, float rx, float ry, float r, float g, float b, float a){
        vertices.put(x).put(y).put(r).put(g).put(b).put(a).put(rx).put(ry);
    }

    public void dispose(){
        MemoryUtil.memFree(vertices);
        vertexArray.dispose();
        vertexBuffer.dispose();
        program.dispose();
    }
}
