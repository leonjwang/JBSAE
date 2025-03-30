package jbsae.graphics.gl;

import jbsae.struct.*;

import static org.lwjgl.opengl.GL20.*;


public class Shader{
    public static Seq<Shader> all = new Seq<>();

    public int id, type;
    public String data;

    public Shader(String data, int type){
        all.add(this);

        id = glCreateShader(type);
        this.type = type;
        this.data = data;
        init();
    }

    public Shader init(){
        glShaderSource(id, data);
        glCompileShader(id);
        glUseProgram(id);
        return this;
    }

    public void dispose(){
        glDeleteShader(id);
    }
}
