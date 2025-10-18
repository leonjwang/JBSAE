package jbsae.graphics.gl;

import jbsae.struct.*;

import static jbsae.JBSAE.*;
import static org.lwjgl.opengl.GL20.*;


public class Shader{
    public int id, type;
    public String data;

    public Shader(String data, int type){
        assets.shaderList.add(this);

        id = glCreateShader(type);
        this.type = type;
        this.data = data;
        init();
    }

    public boolean frag(){
        return type == GL_FRAGMENT_SHADER;
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
