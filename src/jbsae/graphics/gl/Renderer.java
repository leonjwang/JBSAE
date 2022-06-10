package jbsae.graphics.gl;

import jbsae.files.*;
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

    public Texture binded;

    public Renderer(){
    }

    public void init(){
        vertices = MemoryUtil.memAllocFloat(4096);
        verticesNum = 0;

        vertexArray = new VertexArray();
        vertexBuffer = new VertexBuffer();
        vertexBuffer.data(GL_ARRAY_BUFFER, vertices.capacity() * Float.BYTES, GL_DYNAMIC_DRAW);

        vertexShader = new ShaderFile("assets/shaders/shader.vert", GL_VERTEX_SHADER).load().shader;
        fragmentShader = new ShaderFile("assets/shaders/shader.frag", GL_FRAGMENT_SHADER).load().shader;

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

    public void draw(Shape2 d, Shape2 t, Color c){
        if(vertices.remaining() < 8 * 6) flush();

        d.scl(2);
        t.sclc(1, -1);
        vertex(d.v[0], t.v[0], c);
        vertex(d.v[1], t.v[1], c);
        vertex(d.v[2], t.v[2], c);
        vertex(d.v[0], t.v[0], c);
        vertex(d.v[3], t.v[3], c);
        vertex(d.v[2], t.v[2], c);

        verticesNum += 6;
    }

    public void vertex(Pos2 p, Pos2 t, Color c){
        vertices.put(p.x()).put(p.y()).put(c.r).put(c.g).put(c.b).put(c.a).put(t.x()).put(t.y());
    }

    public void dispose(){
        MemoryUtil.memFree(vertices);
        vertexArray.dispose();
        vertexBuffer.dispose();
        program.dispose();
    }
}
